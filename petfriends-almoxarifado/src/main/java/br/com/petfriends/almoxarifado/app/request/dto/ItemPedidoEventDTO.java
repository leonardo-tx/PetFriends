package br.com.petfriends.almoxarifado.app.request.dto;

import java.math.BigDecimal;

public record ItemPedidoEventDTO(String produtoId, BigDecimal valorUnitario, int quantidade) {
}
