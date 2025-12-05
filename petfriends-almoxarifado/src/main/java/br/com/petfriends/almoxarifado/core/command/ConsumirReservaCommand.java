package br.com.petfriends.almoxarifado.core.command;

public final class ConsumirReservaCommand extends BaseCommand {
    private final String pedidoId;

    public ConsumirReservaCommand(String pedidoId) {
        this.pedidoId = pedidoId;
    }

    public String getPedidoId() {
        return pedidoId;
    }
}
