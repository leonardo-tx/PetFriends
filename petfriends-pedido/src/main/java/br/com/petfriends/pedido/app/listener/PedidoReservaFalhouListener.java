package br.com.petfriends.pedido.app.listener;

import br.com.petfriends.pedido.app.request.dto.PedidoReservaFalhouEventDTO;
import br.com.petfriends.pedido.core.command.CancelarPedidoCommand;
import br.com.petfriends.pedido.core.port.in.CancelarPedidoUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class PedidoReservaFalhouListener {
    private static final Logger log = LoggerFactory.getLogger(PedidoReservaFalhouListener.class);
    private final CancelarPedidoUseCase cancelarPedidoUseCase;

    public PedidoReservaFalhouListener(CancelarPedidoUseCase cancelarPedidoUseCase) {
        this.cancelarPedidoUseCase = cancelarPedidoUseCase;
    }

    @Bean
    public Consumer<PedidoReservaFalhouEventDTO> pedidoReservaFalhou() {
        return event -> {
            log.info("Evento recebido: {}", event);
            CancelarPedidoCommand command = new CancelarPedidoCommand(event.id());
            cancelarPedidoUseCase.cancelar(command);
        };
    }
}
