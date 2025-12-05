package br.com.petfriends.transporte.core.query;

import java.util.UUID;

public final class BuscarEntregaPeloIdQuery {
    private final UUID id;

    public BuscarEntregaPeloIdQuery(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }
}
