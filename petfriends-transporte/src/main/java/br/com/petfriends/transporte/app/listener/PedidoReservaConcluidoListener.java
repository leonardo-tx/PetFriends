package br.com.petfriends.transporte.app.listener;

import br.com.petfriends.transporte.app.request.dto.PedidoReservaConcluidoEventDTO;
import br.com.petfriends.transporte.core.command.CriarEntregaCommand;
import br.com.petfriends.transporte.core.command.CriarRemessaCommand;
import br.com.petfriends.transporte.core.port.in.CriarEntregaUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.function.Consumer;

@Configuration
public class PedidoReservaConcluidoListener {
    private static final Logger log = LoggerFactory.getLogger(PedidoReservaConcluidoListener.class);
    private final CriarEntregaUseCase criarEntregaUseCase;

    public PedidoReservaConcluidoListener(CriarEntregaUseCase criarEntregaUseCase) {
        this.criarEntregaUseCase = criarEntregaUseCase;
    }

    @Bean
    public Consumer<PedidoReservaConcluidoEventDTO> pedidoReservaConcluido() {
        return event -> {
            log.info("Evento recebido: {}", event);
            List<CriarRemessaCommand> criarRemessaCommands = event.reservas()
                    .stream()
                    .map(r -> new CriarRemessaCommand(r.almoxarifadoId(), r.itemId(), r.quantidade()))
                    .toList();
            CriarEntregaCommand command = new CriarEntregaCommand(event.id(), criarRemessaCommands);
            criarEntregaUseCase.criar(command);
        };
    }
}
