package br.com.petfriends.pedido.core.event;

import br.com.petfriends.pedido.core.model.Endereco;
import br.com.petfriends.pedido.core.model.ItemPedido;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public final class PedidoCriadoEvent extends BaseEvent<UUID> {
    private final String clienteId;
    private final Endereco endereco;
    private final List<ItemPedido> items;

    public PedidoCriadoEvent(UUID id, Instant timestamp, String clienteId, Endereco endereco, List<ItemPedido> items) {
        super(id, timestamp);
        this.clienteId = clienteId;
        this.endereco = endereco;
        this.items = items;
    }

    public String getClienteId() {
        return clienteId;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public List<ItemPedido> getItems() {
        return items;
    }
}
