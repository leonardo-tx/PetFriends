package br.com.petfriends.almoxarifado.core.query;

import java.util.UUID;

public final class BuscarAlmoxarifadoEventosPeloIdQuery {
    private final UUID id;

    public BuscarAlmoxarifadoEventosPeloIdQuery(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }
}
