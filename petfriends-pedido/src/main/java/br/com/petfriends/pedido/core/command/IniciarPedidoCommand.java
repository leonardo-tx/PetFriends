package br.com.petfriends.pedido.core.command;

import java.util.UUID;

public final class IniciarPedidoCommand extends AggregateCommand<UUID> {
    public IniciarPedidoCommand(UUID id) {
        super(id);
    }
}
