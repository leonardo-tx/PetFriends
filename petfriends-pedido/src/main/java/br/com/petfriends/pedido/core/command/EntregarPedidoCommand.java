package br.com.petfriends.pedido.core.command;

import java.util.UUID;

public final class EntregarPedidoCommand extends AggregateCommand<UUID> {
    public EntregarPedidoCommand(UUID id) {
        super(id);
    }
}
