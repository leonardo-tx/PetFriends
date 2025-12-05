package br.com.petfriends.transporte.infra.adapter.messenger.publisher;

import br.com.petfriends.transporte.core.event.EntregaSeparadaEvent;
import br.com.petfriends.transporte.infra.adapter.messenger.dto.EntregaSeparadaEventDTO;
import org.axonframework.eventhandling.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

@Component
public class EntregaSeparadaPublisher {
    private static final Logger log = LoggerFactory.getLogger(EntregaSeparadaPublisher.class);
    private final StreamBridge streamBridge;

    public EntregaSeparadaPublisher(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    @EventHandler
    public void on(EntregaSeparadaEvent event) {
        EntregaSeparadaEventDTO dto = new EntregaSeparadaEventDTO(event.getId(), event.getTimestamp());
        log.info("Evento publicado: {}", dto);
        streamBridge.send("entrega-separada-out-0", dto);
    }
}
