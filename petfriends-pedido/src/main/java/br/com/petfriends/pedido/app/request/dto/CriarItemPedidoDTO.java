package br.com.petfriends.pedido.app.request.dto;

import java.math.BigDecimal;

public record CriarItemPedidoDTO(String produtoId, BigDecimal valorUnitario, Integer quantidade) {
}
