package br.com.petfriends.pedido.core.query;

import java.util.UUID;

public final class BuscarPedidoEventosPeloIdQuery {
    private final UUID id;

    public BuscarPedidoEventosPeloIdQuery(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }
}
