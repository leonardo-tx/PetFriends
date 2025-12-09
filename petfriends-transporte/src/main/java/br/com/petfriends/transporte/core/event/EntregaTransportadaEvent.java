package br.com.petfriends.transporte.core.event;

import java.time.Instant;
import java.util.UUID;

public final class EntregaTransportadaEvent extends BaseEvent<UUID> {
    public EntregaTransportadaEvent(UUID id, Instant timestamp) {
        super(id, timestamp);
    }
}
