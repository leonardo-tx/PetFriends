package br.com.petfriends.transporte.core.event;

import java.time.Instant;
import java.util.UUID;

public final class RemessaSeparadaEvent extends BaseEvent<UUID> {
    private final String almoxarifadoId;

    public RemessaSeparadaEvent(UUID id, Instant timestamp, String almoxarifadoId) {
        super(id, timestamp);
        this.almoxarifadoId = almoxarifadoId;
    }

    public String getAlmoxarifadoId() {
        return almoxarifadoId;
    }
}
