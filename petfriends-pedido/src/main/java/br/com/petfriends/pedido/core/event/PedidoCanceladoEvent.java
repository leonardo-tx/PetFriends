package br.com.petfriends.pedido.core.event;

import java.time.Instant;
import java.util.UUID;

public final class PedidoCanceladoEvent extends BaseEvent<UUID> {
    public PedidoCanceladoEvent(UUID id, Instant timestamp) {
        super(id, timestamp);
    }
}
