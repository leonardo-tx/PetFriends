package br.com.petfriends.pedido.app.listener;

import br.com.petfriends.pedido.app.request.dto.EntregaCriadaEventDTO;
import br.com.petfriends.pedido.core.command.AdicionarEntregaAoPedidoCommand;
import br.com.petfriends.pedido.core.port.in.AdicionarEntregaAoPedidoUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class EntregaCriadaListener {
    private static final Logger log = LoggerFactory.getLogger(EntregaCriadaListener.class);
    private final AdicionarEntregaAoPedidoUseCase adicionarEntregaAoPedidoUseCase;

    public EntregaCriadaListener(AdicionarEntregaAoPedidoUseCase adicionarEntregaAoPedidoUseCase) {
        this.adicionarEntregaAoPedidoUseCase = adicionarEntregaAoPedidoUseCase;
    }

    @Bean
    public Consumer<EntregaCriadaEventDTO> entregaCriada() {
        return event -> {
            log.info("Evento recebido: {}", event);
            AdicionarEntregaAoPedidoCommand command = new AdicionarEntregaAoPedidoCommand(
                    event.pedidoId(),
                    event.id()
            );
            adicionarEntregaAoPedidoUseCase.adicionarEntrega(command);
        };
    }
}
