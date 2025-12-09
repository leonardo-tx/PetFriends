package br.com.petfriends.almoxarifado.core.query;

import java.util.UUID;

public final class BuscarAlmoxarifadoPeloIdQuery {
    private final UUID id;

    public BuscarAlmoxarifadoPeloIdQuery(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }
}
