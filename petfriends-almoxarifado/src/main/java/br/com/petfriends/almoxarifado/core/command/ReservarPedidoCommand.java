package br.com.petfriends.almoxarifado.core.command;

import java.util.List;

public final class ReservarPedidoCommand extends BaseCommand {
    private final String pedidoId;
    private final List<ReservarItemCommand> reservarItemCommands;

    public ReservarPedidoCommand(String pedidoId, List<ReservarItemCommand> reservarItemCommands) {
        this.pedidoId = pedidoId;
        this.reservarItemCommands = reservarItemCommands;
    }

    public String getPedidoId() {
        return pedidoId;
    }

    public List<ReservarItemCommand> getReservarItemCommands() {
        return reservarItemCommands;
    }
}
