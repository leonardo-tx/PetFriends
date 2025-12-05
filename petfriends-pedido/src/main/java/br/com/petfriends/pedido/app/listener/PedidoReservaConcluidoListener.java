package br.com.petfriends.pedido.app.listener;

import br.com.petfriends.pedido.app.request.dto.PedidoReservaConcluidoEventDTO;
import br.com.petfriends.pedido.core.command.IniciarPedidoCommand;
import br.com.petfriends.pedido.core.port.in.IniciarPedidoUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class PedidoReservaConcluidoListener {
    private static final Logger log = LoggerFactory.getLogger(PedidoReservaConcluidoListener.class);
    private final IniciarPedidoUseCase iniciarPedidoUseCase;

    public PedidoReservaConcluidoListener(IniciarPedidoUseCase iniciarPedidoUseCase) {
        this.iniciarPedidoUseCase = iniciarPedidoUseCase;
    }

    @Bean
    public Consumer<PedidoReservaConcluidoEventDTO> pedidoReservaConcluido() {
        return event -> {
            log.info("Evento recebido: {}", event);
            IniciarPedidoCommand command = new IniciarPedidoCommand(event.id());
            iniciarPedidoUseCase.iniciar(command);
        };
    }
}
