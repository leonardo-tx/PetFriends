package br.com.petfriends.pedido.core.command;

import java.util.List;

public final class CriarPedidoCommand extends BaseCommand {
    private final String clienteId;
    private final String rua;
    private final String numero;
    private final String complemento;
    private final String bairro;
    private final String cidade;
    private final String estado;
    private final String cep;
    private final List<AdicionarItemPedidoCommand> itemPedidoCommands;

    public CriarPedidoCommand(
            String clienteId,
            String rua,
            String numero,
            String complemento,
            String bairro,
            String cidade,
            String estado,
            String cep,
            List<AdicionarItemPedidoCommand> itemPedidoCommands
    ) {
        this.clienteId = clienteId;
        this.rua = rua;
        this.numero = numero;
        this.complemento = complemento;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
        this.cep = cep;
        this.itemPedidoCommands = itemPedidoCommands;
    }

    public String getClienteId() {
        return clienteId;
    }

    public String getRua() {
        return rua;
    }

    public String getNumero() {
        return numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public String getEstado() {
        return estado;
    }

    public String getCep() {
        return cep;
    }

    public List<AdicionarItemPedidoCommand> getItemPedidoCommands() {
        return itemPedidoCommands;
    }
}
