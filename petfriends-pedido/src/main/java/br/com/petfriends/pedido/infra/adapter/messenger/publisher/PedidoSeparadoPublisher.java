package br.com.petfriends.pedido.infra.adapter.messenger.publisher;

import br.com.petfriends.pedido.core.event.PedidoSeparadoEvent;
import br.com.petfriends.pedido.infra.adapter.messenger.dto.PedidoSeparadoEventDTO;
import org.axonframework.eventhandling.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

@Component
public class PedidoSeparadoPublisher {
    private static final Logger log = LoggerFactory.getLogger(PedidoSeparadoPublisher.class);
    private final StreamBridge streamBridge;

    public PedidoSeparadoPublisher(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    @EventHandler
    public void on(PedidoSeparadoEvent event) {
        PedidoSeparadoEventDTO dto = new PedidoSeparadoEventDTO(event.getId(), event.getTimestamp());
        log.info("Evento publicado: {}", dto);
        streamBridge.send("pedido-separado-out-0", dto);
    }
}
