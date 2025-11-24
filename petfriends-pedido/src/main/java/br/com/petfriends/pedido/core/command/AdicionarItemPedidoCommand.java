package br.com.petfriends.pedido.core.command;

import java.math.BigDecimal;

public final class AdicionarItemPedidoCommand extends BaseCommand {
    private final String produtoId;
    private final BigDecimal valorUnitario;
    private final Integer quantidade;

    public AdicionarItemPedidoCommand(String produtoId, BigDecimal valorUnitario, Integer quantidade) {
        this.produtoId = produtoId;
        this.valorUnitario = valorUnitario;
        this.quantidade = quantidade;
    }

    public String getProdutoId() {
        return produtoId;
    }

    public BigDecimal getValorUnitario() {
        return valorUnitario;
    }

    public Integer getQuantidade() {
        return quantidade;
    }
}
