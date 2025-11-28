package br.com.petfriends.pedido.core.event;

import java.time.Instant;
import java.util.UUID;

public final class PedidoPagoEvent extends BaseEvent<UUID> {
    public PedidoPagoEvent(UUID id, Instant timestamp) {
        super(id, timestamp);
    }
}
