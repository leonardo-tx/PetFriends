package br.com.petfriends.transporte.infra.adapter.messenger.publisher;

import br.com.petfriends.transporte.core.event.EntregaCriadaEvent;
import br.com.petfriends.transporte.infra.adapter.messenger.dto.EntregaCriadaEventDTO;
import org.axonframework.eventhandling.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

@Component
public class EntregaCriadaPublisher {
    private static final Logger log = LoggerFactory.getLogger(EntregaCriadaPublisher.class);
    private final StreamBridge streamBridge;

    public EntregaCriadaPublisher(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    @EventHandler
    public void on(EntregaCriadaEvent event) {
        EntregaCriadaEventDTO dto = new EntregaCriadaEventDTO(event.getId(), event.getTimestamp(), event.getPedidoId());
        log.info("Evento publicado: {}", dto);
        streamBridge.send("entrega-criada-out-0", dto);
    }
}
