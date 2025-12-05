package br.com.petfriends.pedido.app.listener;

import br.com.petfriends.pedido.app.request.dto.EntregaSeparadaEventDTO;
import br.com.petfriends.pedido.core.command.SepararPedidoCommand;
import br.com.petfriends.pedido.core.port.in.GetPedidoUseCase;
import br.com.petfriends.pedido.core.port.in.SepararPedidoUseCase;
import br.com.petfriends.pedido.core.query.BuscarPedidoPeloEntregaIdQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;
import java.util.function.Consumer;

@Configuration
public class EntregaSeparadaListener {
    private static final Logger log = LoggerFactory.getLogger(EntregaSeparadaListener.class);
    private final SepararPedidoUseCase separarPedidoUseCase;
    private final GetPedidoUseCase getPedidoUseCase;

    public EntregaSeparadaListener(SepararPedidoUseCase separarPedidoUseCase, GetPedidoUseCase getPedidoUseCase) {
        this.separarPedidoUseCase = separarPedidoUseCase;
        this.getPedidoUseCase = getPedidoUseCase;
    }

    @Bean
    public Consumer<EntregaSeparadaEventDTO> entregaSeparada() {
        return event -> {
            log.info("Evento recebido: {}", event);

            BuscarPedidoPeloEntregaIdQuery query = new BuscarPedidoPeloEntregaIdQuery(event.id());
            UUID pedidoId = getPedidoUseCase.getByEntregaId(query).getId();

            SepararPedidoCommand command = new SepararPedidoCommand(pedidoId);
            separarPedidoUseCase.separar(command);
        };
    }
}
