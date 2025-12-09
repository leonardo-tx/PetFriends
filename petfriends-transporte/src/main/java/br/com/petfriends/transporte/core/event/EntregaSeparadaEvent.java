package br.com.petfriends.transporte.core.event;

import java.time.Instant;
import java.util.UUID;

public final class EntregaSeparadaEvent extends BaseEvent<UUID> {
    public EntregaSeparadaEvent(UUID id, Instant timestamp) {
        super(id, timestamp);
    }
}
