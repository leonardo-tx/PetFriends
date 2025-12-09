package br.com.petfriends.almoxarifado.infra.adapter.messenger.publisher;

import br.com.petfriends.almoxarifado.core.event.PedidoReservaConcluidoEvent;
import br.com.petfriends.almoxarifado.infra.adapter.messenger.dto.PedidoReservaConcluidoEventDTO;
import br.com.petfriends.almoxarifado.infra.adapter.messenger.dto.ReservaEventDTO;
import org.axonframework.eventhandling.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

@Component
public class PedidoReservaConcluidoPublisher {
    private static final Logger log = LoggerFactory.getLogger(PedidoReservaConcluidoPublisher.class);
    private final StreamBridge streamBridge;

    public PedidoReservaConcluidoPublisher(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    @EventHandler
    public void on(PedidoReservaConcluidoEvent event) {
        PedidoReservaConcluidoEventDTO dto = new PedidoReservaConcluidoEventDTO(
                event.getId(),
                event.getTimestamp(),
                event.getAlmoxarifadoReservas().stream().map(r -> new ReservaEventDTO(
                        r.almoxarifadoId().toString(),
                        r.itemId(),
                        r.quantidade()
                )).toList()
        );
        log.info("Evento publicado: {}", dto);
        streamBridge.send("pedido-reserva-concluido-out-0", dto);
    }
}
