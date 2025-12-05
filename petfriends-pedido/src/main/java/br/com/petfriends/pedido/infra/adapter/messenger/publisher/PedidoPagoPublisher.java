package br.com.petfriends.pedido.infra.adapter.messenger.publisher;

import br.com.petfriends.pedido.core.event.PedidoPagoEvent;
import br.com.petfriends.pedido.infra.adapter.messenger.dto.PedidoPagoEventDTO;
import org.axonframework.eventhandling.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

@Component
public class PedidoPagoPublisher {
    private static final Logger log = LoggerFactory.getLogger(PedidoPagoPublisher.class);
    private final StreamBridge streamBridge;

    public PedidoPagoPublisher(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    @EventHandler
    public void on(PedidoPagoEvent event) {
        PedidoPagoEventDTO dto = new PedidoPagoEventDTO(event.getId(), event.getTimestamp());
        log.info("Evento publicado: {}", dto);
        streamBridge.send("pedido-pago-out-0", dto);
    }
}
