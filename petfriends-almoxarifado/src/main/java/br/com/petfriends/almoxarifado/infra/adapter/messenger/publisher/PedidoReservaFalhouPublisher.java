package br.com.petfriends.almoxarifado.infra.adapter.messenger.publisher;

import br.com.petfriends.almoxarifado.core.event.PedidoReservaFalhouEvent;
import br.com.petfriends.almoxarifado.infra.adapter.messenger.dto.PedidoReservaFalhouEventDTO;
import org.axonframework.eventhandling.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

@Component
public class PedidoReservaFalhouPublisher {
    private static final Logger log = LoggerFactory.getLogger(PedidoReservaFalhouPublisher.class);
    private final StreamBridge streamBridge;

    public PedidoReservaFalhouPublisher(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    @EventHandler
    public void on(PedidoReservaFalhouEvent event) {
        PedidoReservaFalhouEventDTO dto = new PedidoReservaFalhouEventDTO(
                event.getId(),
                event.getTimestamp()
        );
        log.info("Evento publicado: {}", dto);
        streamBridge.send("pedido-reserva-falhou-out-0", dto);
    }
}
