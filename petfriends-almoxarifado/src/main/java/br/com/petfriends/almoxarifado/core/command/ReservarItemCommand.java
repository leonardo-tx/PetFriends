package br.com.petfriends.almoxarifado.core.command;

public final class ReservarItemCommand extends BaseCommand {
    private final String itemId;
    private final Integer quantidade;

    public ReservarItemCommand(String itemId, Integer quantidade) {
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
