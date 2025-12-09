package br.com.petfriends.almoxarifado.core.command;

public final class LiberarReservaCommand extends BaseCommand {
    private final String pedidoId;

    public LiberarReservaCommand(String pedidoId) {
        this.pedidoId = pedidoId;
    }

    public String getPedidoId() {
        return pedidoId;
    }
}
