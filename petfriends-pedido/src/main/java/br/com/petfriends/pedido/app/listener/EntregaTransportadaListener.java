package br.com.petfriends.pedido.app.listener;

import br.com.petfriends.pedido.app.request.dto.EntregaTransportadaEventDTO;
import br.com.petfriends.pedido.core.command.EnviarPedidoCommand;
import br.com.petfriends.pedido.core.port.in.EnviarPedidoUseCase;
import br.com.petfriends.pedido.core.port.in.GetPedidoUseCase;
import br.com.petfriends.pedido.core.query.BuscarPedidoPeloEntregaIdQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;
import java.util.function.Consumer;

@Configuration
public class EntregaTransportadaListener {
    private static final Logger log = LoggerFactory.getLogger(EntregaTransportadaListener.class);
    private final EnviarPedidoUseCase enviarPedidoUseCase;
    private final GetPedidoUseCase getPedidoUseCase;

    public EntregaTransportadaListener(EnviarPedidoUseCase enviarPedidoUseCase, GetPedidoUseCase getPedidoUseCase) {
        this.enviarPedidoUseCase = enviarPedidoUseCase;
        this.getPedidoUseCase = getPedidoUseCase;
    }

    @Bean
    public Consumer<EntregaTransportadaEventDTO> entregaTransportada() {
        return event -> {
            log.info("Evento recebido: {}", event);

            BuscarPedidoPeloEntregaIdQuery query = new BuscarPedidoPeloEntregaIdQuery(event.id());
            UUID pedidoId = getPedidoUseCase.getByEntregaId(query).getId();

            EnviarPedidoCommand command = new EnviarPedidoCommand(pedidoId);
            enviarPedidoUseCase.enviar(command);
        };
    }
}
