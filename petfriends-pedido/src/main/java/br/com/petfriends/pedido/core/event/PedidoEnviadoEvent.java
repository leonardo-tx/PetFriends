package br.com.petfriends.pedido.core.event;

import java.time.Instant;
import java.util.UUID;

public final class PedidoEnviadoEvent extends BaseEvent<UUID> {
    public PedidoEnviadoEvent(UUID id, Instant timestamp) {
        super(id, timestamp);
    }
}
