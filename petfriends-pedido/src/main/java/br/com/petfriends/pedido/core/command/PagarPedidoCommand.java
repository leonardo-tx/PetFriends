package br.com.petfriends.pedido.core.command;

import java.util.UUID;

public final class PagarPedidoCommand extends AggregateCommand<UUID> {
    public PagarPedidoCommand(UUID id) {
        super(id);
    }
}
