package br.com.petfriends.transporte.core.query;

import java.util.UUID;

public final class BuscarEntregaEventosPeloIdQuery {
    private final UUID id;

    public BuscarEntregaEventosPeloIdQuery(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }
}
