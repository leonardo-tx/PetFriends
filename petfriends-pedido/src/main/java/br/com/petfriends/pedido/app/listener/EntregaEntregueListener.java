package br.com.petfriends.pedido.app.listener;

import br.com.petfriends.pedido.app.request.dto.EntregaEntregueEventDTO;
import br.com.petfriends.pedido.core.command.EntregarPedidoCommand;
import br.com.petfriends.pedido.core.port.in.EntregarPedidoUseCase;
import br.com.petfriends.pedido.core.port.in.GetPedidoUseCase;
import br.com.petfriends.pedido.core.query.BuscarPedidoPeloEntregaIdQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;
import java.util.function.Consumer;

@Configuration
public class EntregaEntregueListener {
    private static final Logger log = LoggerFactory.getLogger(EntregaEntregueListener.class);
    private final EntregarPedidoUseCase entregarPedidoUseCase;
    private final GetPedidoUseCase getPedidoUseCase;

    public EntregaEntregueListener(EntregarPedidoUseCase entregarPedidoUseCase, GetPedidoUseCase getPedidoUseCase) {
        this.entregarPedidoUseCase = entregarPedidoUseCase;
        this.getPedidoUseCase = getPedidoUseCase;
    }

    @Bean
    public Consumer<EntregaEntregueEventDTO> entregaEntregue() {
        return event -> {
            log.info("Evento recebido: {}", event);

            BuscarPedidoPeloEntregaIdQuery query = new BuscarPedidoPeloEntregaIdQuery(event.id());
            UUID pedidoId = getPedidoUseCase.getByEntregaId(query).getId();

            EntregarPedidoCommand command = new EntregarPedidoCommand(pedidoId);
            entregarPedidoUseCase.entregar(command);
        };
    }
}
