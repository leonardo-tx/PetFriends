package br.com.petfriends.pedido.app.response.dto;

import java.math.BigDecimal;

public record ItemPedidoViewDTO(String produtoId, BigDecimal valorUnitario, int quantidade) {
}
