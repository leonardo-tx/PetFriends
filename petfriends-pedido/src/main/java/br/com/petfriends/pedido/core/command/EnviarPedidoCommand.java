package br.com.petfriends.pedido.core.command;

import java.util.UUID;

public final class EnviarPedidoCommand extends AggregateCommand<UUID> {
    public EnviarPedidoCommand(UUID id) {
        super(id);
    }
}
