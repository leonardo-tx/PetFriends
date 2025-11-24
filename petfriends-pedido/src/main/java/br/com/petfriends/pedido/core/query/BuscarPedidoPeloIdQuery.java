package br.com.petfriends.pedido.core.query;

import java.util.UUID;

public final class BuscarPedidoPeloIdQuery {
    private final UUID id;

    public BuscarPedidoPeloIdQuery(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }
}
