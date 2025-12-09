package br.com.petfriends.pedido.core.command;

import java.util.UUID;

public final class AdicionarEntregaAoPedidoCommand extends AggregateCommand<UUID> {
    private final String entregaId;

    public AdicionarEntregaAoPedidoCommand(UUID id, String entregaId) {
        super(id);
        this.entregaId = entregaId;
    }

    public String getEntregaId() {
        return entregaId;
    }
}
