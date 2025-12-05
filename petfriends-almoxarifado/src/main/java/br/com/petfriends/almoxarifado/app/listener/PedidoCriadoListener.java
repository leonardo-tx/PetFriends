package br.com.petfriends.almoxarifado.app.listener;

import br.com.petfriends.almoxarifado.app.request.dto.PedidoCriadoEventDTO;
import br.com.petfriends.almoxarifado.core.command.ReservarItemCommand;
import br.com.petfriends.almoxarifado.core.command.ReservarPedidoCommand;
import br.com.petfriends.almoxarifado.core.port.in.ReservarPedidoUseCase;
import br.com.petfriends.almoxarifado.core.service.ReservarPedidoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.function.Consumer;

@Configuration
public class PedidoCriadoListener {
    private static final Logger log = LoggerFactory.getLogger(PedidoCriadoListener.class);
    private final ReservarPedidoUseCase reservarPedidoUseCase;

    public PedidoCriadoListener(ReservarPedidoService reservarPedidoUseCase) {
        this.reservarPedidoUseCase = reservarPedidoUseCase;
    }

    @Bean
    public Consumer<PedidoCriadoEventDTO> pedidoCriado() {
        return event -> {
            log.info("Evento recebido: {}", event);
            List<ReservarItemCommand> reservarItemCommands = event.items()
                    .stream()
                    .map(i -> new ReservarItemCommand(
                            i.produtoId(),
                            i.quantidade()
                    )).toList();
            ReservarPedidoCommand command = new ReservarPedidoCommand(event.id(), reservarItemCommands);
            reservarPedidoUseCase.reservar(command);
        };
    }
}
