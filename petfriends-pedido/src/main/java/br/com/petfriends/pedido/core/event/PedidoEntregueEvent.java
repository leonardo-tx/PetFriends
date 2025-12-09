package br.com.petfriends.pedido.core.event;

import java.time.Instant;
import java.util.UUID;

public final class PedidoEntregueEvent extends BaseEvent<UUID> {
    public PedidoEntregueEvent(UUID id, Instant timestamp) {
        super(id, timestamp);
    }
}
