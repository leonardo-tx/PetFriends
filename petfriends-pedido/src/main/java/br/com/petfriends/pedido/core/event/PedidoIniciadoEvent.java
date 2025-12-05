package br.com.petfriends.pedido.core.event;

import java.time.Instant;
import java.util.UUID;

public final class PedidoIniciadoEvent extends BaseEvent<UUID> {
    public PedidoIniciadoEvent(UUID id, Instant timestamp) {
        super(id, timestamp);
    }
}
