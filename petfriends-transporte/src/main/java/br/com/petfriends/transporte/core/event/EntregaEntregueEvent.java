package br.com.petfriends.transporte.core.event;

import java.time.Instant;
import java.util.UUID;

public final class EntregaEntregueEvent extends BaseEvent<UUID> {
    public EntregaEntregueEvent(UUID id, Instant timestamp) {
        super(id, timestamp);
    }
}
