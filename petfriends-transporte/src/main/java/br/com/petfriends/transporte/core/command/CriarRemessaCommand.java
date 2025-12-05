package br.com.petfriends.transporte.core.command;

public final class CriarRemessaCommand extends BaseCommand {
    private final String almoxarifadoId;
    private final String itemId;
    private final int quantidade;

    public CriarRemessaCommand(String almoxarifadoId, String itemId, int quantidade) {
        this.almoxarifadoId = almoxarifadoId;
        this.itemId = itemId;
        this.quantidade = quantidade;
    }

    public String getAlmoxarifadoId() {
        return almoxarifadoId;
    }

    public String getItemId() {
        return itemId;
    }

    public int getQuantidade() {
        return quantidade;
    }
}
