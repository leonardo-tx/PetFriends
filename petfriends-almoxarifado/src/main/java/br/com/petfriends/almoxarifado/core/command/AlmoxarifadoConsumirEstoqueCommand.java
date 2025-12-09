package br.com.petfriends.almoxarifado.core.command;

import java.util.UUID;

public final class AlmoxarifadoConsumirEstoqueCommand extends AggregateCommand<UUID> {
    private final String pedidoId;

    public AlmoxarifadoConsumirEstoqueCommand(UUID id, String pedidoId) {
        super(id);
        this.pedidoId = pedidoId;
    }

    public String getPedidoId() {
        return pedidoId;
    }
}
