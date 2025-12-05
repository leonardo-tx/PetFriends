package br.com.petfriends.pedido.infra.adapter.messenger.publisher;

import br.com.petfriends.pedido.core.event.PedidoCanceladoEvent;
import br.com.petfriends.pedido.infra.adapter.messenger.dto.PedidoCanceladoEventDTO;
import org.axonframework.eventhandling.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

@Component
public class PedidoCanceladoPublisher {
    private static final Logger log = LoggerFactory.getLogger(PedidoCanceladoPublisher.class);
    private final StreamBridge streamBridge;

    public PedidoCanceladoPublisher(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    @EventHandler
    public void on(PedidoCanceladoEvent event) {
        PedidoCanceladoEventDTO dto = new PedidoCanceladoEventDTO(event.getId(), event.getTimestamp());
        log.info("Evento publicado: {}", dto);
        streamBridge.send("pedido-cancelado-out-0", dto);
    }
}
