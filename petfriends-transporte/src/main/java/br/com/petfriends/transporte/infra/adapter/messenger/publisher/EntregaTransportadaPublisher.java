package br.com.petfriends.transporte.infra.adapter.messenger.publisher;

import br.com.petfriends.transporte.core.event.EntregaTransportadaEvent;
import br.com.petfriends.transporte.infra.adapter.messenger.dto.EntregaTransportadaEventDTO;
import org.axonframework.eventhandling.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

@Component
public class EntregaTransportadaPublisher {
    private static final Logger log = LoggerFactory.getLogger(EntregaTransportadaPublisher.class);
    private final StreamBridge streamBridge;

    public EntregaTransportadaPublisher(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    @EventHandler
    public void on(EntregaTransportadaEvent event) {
        EntregaTransportadaEventDTO dto = new EntregaTransportadaEventDTO(event.getId(), event.getTimestamp());
        log.info("Evento publicado: {}", dto);
        streamBridge.send("entrega-transportada-out-0", dto);
    }
}
