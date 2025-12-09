package br.com.petfriends.pedido.core.event;

import java.time.Instant;
import java.util.UUID;

public final class PedidoSeparadoEvent extends BaseEvent<UUID> {
    public PedidoSeparadoEvent(UUID id, Instant timestamp) {
        super(id, timestamp);
    }
}
