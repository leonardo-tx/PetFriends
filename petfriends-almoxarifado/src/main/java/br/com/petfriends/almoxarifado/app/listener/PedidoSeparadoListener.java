package br.com.petfriends.almoxarifado.app.listener;

import br.com.petfriends.almoxarifado.app.request.dto.PedidoSeparadoEventDTO;
import br.com.petfriends.almoxarifado.core.command.ConsumirReservaCommand;
import br.com.petfriends.almoxarifado.core.port.in.ConsumirReservaUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class PedidoSeparadoListener {
    private static final Logger log = LoggerFactory.getLogger(PedidoSeparadoListener.class);
    private final ConsumirReservaUseCase consumirReservaUseCase;

    public PedidoSeparadoListener(ConsumirReservaUseCase consumirReservaUseCase) {
        this.consumirReservaUseCase = consumirReservaUseCase;
    }

    @Bean
    public Consumer<PedidoSeparadoEventDTO> pedidoSeparado() {
        return event -> {
            log.info("Evento recebido: {}", event);

            ConsumirReservaCommand command = new ConsumirReservaCommand(event.id());
            consumirReservaUseCase.consumirReservas(command);
        };
    }
}
