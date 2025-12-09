package br.com.petfriends.almoxarifado.core.command;

import java.util.UUID;

public class AlmoxarifadoReservarItemCommand extends AggregateCommand<UUID> {
    private final String pedidoId;
    private final String itemId;
    private final Integer quantidade;

    public AlmoxarifadoReservarItemCommand(UUID id, String pedidoId, String itemId, Integer quantidade) {
        super(id);
        this.pedidoId = pedidoId;
        this.itemId = itemId;
        this.quantidade = quantidade;
    }

    public String getPedidoId() {
        return pedidoId;
    }

    public String getItemId() {
        return itemId;
    }

    public Integer getQuantidade() {
        return quantidade;
    }
}
