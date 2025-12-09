package br.com.petfriends.transporte.core.query;

public final class BuscarEntregaPeloPedidoIdQuery {
    private final String id;

    public BuscarEntregaPeloPedidoIdQuery(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
