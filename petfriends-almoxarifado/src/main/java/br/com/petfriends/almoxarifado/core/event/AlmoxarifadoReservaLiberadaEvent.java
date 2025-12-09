package br.com.petfriends.almoxarifado.core.event;

import java.time.Instant;
import java.util.UUID;

public final class AlmoxarifadoReservaLiberadaEvent extends BaseEvent<UUID> {
    private final String pedidoId;

    public AlmoxarifadoReservaLiberadaEvent(UUID id, Instant timestamp, String pedidoId) {
        super(id, timestamp);
        this.pedidoId = pedidoId;
    }

    public String getPedidoId() {
        return pedidoId;
    }
}
