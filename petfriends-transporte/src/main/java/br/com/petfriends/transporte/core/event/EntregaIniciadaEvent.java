package br.com.petfriends.transporte.core.event;

import java.time.Instant;
import java.util.UUID;

public final class EntregaIniciadaEvent extends BaseEvent<UUID> {
    public EntregaIniciadaEvent(UUID id, Instant timestamp) {
        super(id, timestamp);
    }
}
