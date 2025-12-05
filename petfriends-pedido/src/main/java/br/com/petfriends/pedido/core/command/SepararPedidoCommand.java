package br.com.petfriends.pedido.core.command;

import java.util.UUID;

public final class SepararPedidoCommand extends AggregateCommand<UUID> {
    public SepararPedidoCommand(UUID id) {
        super(id);
    }
}
