package br.com.petfriends.pedido.core.command;

import java.util.List;

public final class CriarPedidoCommand extends BaseCommand {
    private final String clienteId;
    private final List<AdicionarItemPedidoCommand> itemPedidoCommands;

    public CriarPedidoCommand(String clienteId, List<AdicionarItemPedidoCommand> itemPedidoCommands) {
        this.clienteId = clienteId;
        this.itemPedidoCommands = itemPedidoCommands;
    }

    public String getClienteId() {
        return clienteId;
    }

    public List<AdicionarItemPedidoCommand> getItemPedidoCommands() {
        return itemPedidoCommands;
    }
}
