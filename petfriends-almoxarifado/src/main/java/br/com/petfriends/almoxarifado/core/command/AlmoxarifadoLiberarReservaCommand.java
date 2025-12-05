package br.com.petfriends.almoxarifado.core.command;

import java.util.UUID;

public final class AlmoxarifadoLiberarReservaCommand extends BaseIdentifiableCommand<UUID> {
    private final String itemId;
    private final Integer quantidade;

    public AlmoxarifadoLiberarReservaCommand(UUID id, String itemId, Integer quantidade) {
        super(id);
        this.itemId = itemId;
        this.quantidade = quantidade;
    }

    public String getItemId() {
        return itemId;
    }

    public Integer getQuantidade() {
        return quantidade;
    }
}
