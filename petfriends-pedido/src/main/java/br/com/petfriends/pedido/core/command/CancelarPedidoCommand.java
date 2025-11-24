package br.com.petfriends.pedido.core.command;

import java.util.UUID;

public final class CancelarPedidoCommand extends AggregateCommand<UUID> {
    public CancelarPedidoCommand(UUID id) {
        super(id);
    }
}
