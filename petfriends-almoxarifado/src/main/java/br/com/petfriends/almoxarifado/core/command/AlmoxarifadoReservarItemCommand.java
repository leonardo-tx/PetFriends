package br.com.petfriends.almoxarifado.core.command;

import java.util.UUID;

public class AlmoxarifadoReservarItemCommand extends BaseIdentifiableCommand<UUID> {
    private final String itemId;
    private final Integer quantidade;

    public AlmoxarifadoReservarItemCommand(UUID id, String itemId, Integer quantidade) {
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
