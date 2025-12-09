package br.com.petfriends.almoxarifado.app.listener;

import br.com.petfriends.almoxarifado.app.request.dto.PedidoCanceladoEventDTO;
import br.com.petfriends.almoxarifado.core.command.LiberarReservaCommand;
import br.com.petfriends.almoxarifado.core.port.in.LiberarReservaUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class PedidoCanceladoListener {
    private static final Logger log = LoggerFactory.getLogger(PedidoCanceladoListener.class);
    private final LiberarReservaUseCase liberarReservaUseCase;

    public PedidoCanceladoListener(LiberarReservaUseCase liberarReservaUseCase) {
        this.liberarReservaUseCase = liberarReservaUseCase;
    }

    @Bean
    public Consumer<PedidoCanceladoEventDTO> pedidoCancelado() {
        return event -> {
            log.info("Evento recebido: {}", event);

            LiberarReservaCommand command = new LiberarReservaCommand(event.id());
            liberarReservaUseCase.liberarReserva(command);
        };
    }
}
