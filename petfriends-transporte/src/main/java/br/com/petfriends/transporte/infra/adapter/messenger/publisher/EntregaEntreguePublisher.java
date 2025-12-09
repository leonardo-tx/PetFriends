package br.com.petfriends.transporte.infra.adapter.messenger.publisher;

import br.com.petfriends.transporte.core.event.EntregaEntregueEvent;
import br.com.petfriends.transporte.infra.adapter.messenger.dto.EntregaEntregueEventDTO;
import org.axonframework.eventhandling.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

@Component
public class EntregaEntreguePublisher {
    private static final Logger log = LoggerFactory.getLogger(EntregaEntreguePublisher.class);
    private final StreamBridge streamBridge;

    public EntregaEntreguePublisher(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    @EventHandler
    public void on(EntregaEntregueEvent event) {
        EntregaEntregueEventDTO dto = new EntregaEntregueEventDTO(event.getId(), event.getTimestamp());
        log.info("Evento publicado: {}", dto);
        streamBridge.send("entrega-entregue-out-0", dto);
    }
}
