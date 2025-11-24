package br.com.petfriends.pedido.core.event;

import br.com.petfriends.pedido.core.model.ItemPedido;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public final class PedidoCriadoEvent extends BaseEvent<UUID> {
    private final String clienteId;
    private final List<ItemPedido> items;

    public PedidoCriadoEvent(UUID id, Instant timestamp, String clienteId, List<ItemPedido> items) {
        super(id, timestamp);
        this.clienteId = clienteId;
        this.items = items;
    }

    public String getClienteId() {
        return clienteId;
    }

    public List<ItemPedido> getItems() {
        return items;
    }
}
