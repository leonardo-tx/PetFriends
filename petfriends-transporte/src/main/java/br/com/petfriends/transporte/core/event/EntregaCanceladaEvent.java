package br.com.petfriends.transporte.core.event;

import java.time.Instant;
import java.util.UUID;

public final class EntregaCanceladaEvent extends BaseEvent<UUID> {
    public EntregaCanceladaEvent(UUID id, Instant timestamp) {
        super(id, timestamp);
    }
}
