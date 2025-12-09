package br.com.petfriends.pedido.core.query;

public final class BuscarPedidoPeloEntregaIdQuery {
    private final String id;

    public BuscarPedidoPeloEntregaIdQuery(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
