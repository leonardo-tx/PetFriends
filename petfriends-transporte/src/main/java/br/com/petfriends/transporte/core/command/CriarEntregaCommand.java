package br.com.petfriends.transporte.core.command;

import java.util.List;

public final class CriarEntregaCommand extends BaseCommand {
    private final String pedidoId;
    private final List<CriarRemessaCommand> remessaCommands;

    public CriarEntregaCommand(
            String pedidoId,
            List<CriarRemessaCommand> remessaCommands
    ) {
        this.pedidoId = pedidoId;
        this.remessaCommands = remessaCommands;
    }

    public String getPedidoId() {
        return pedidoId;
    }

    public List<CriarRemessaCommand> getRemessaCommands() {
        return remessaCommands;
    }
}
