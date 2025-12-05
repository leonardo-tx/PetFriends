package br.com.petfriends.pedido.infra.adapter.messenger.dto;

import java.math.BigDecimal;

public record ItemPedidoEventDTO(String produtoId, BigDecimal valorUnitario, int quantidade) {
}
