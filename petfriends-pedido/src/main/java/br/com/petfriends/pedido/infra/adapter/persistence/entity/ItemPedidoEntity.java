package br.com.petfriends.pedido.infra.adapter.persistence.entity;

import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;

public record ItemPedidoEntity(
    @Field(name = "produtoId")
    String produtoId,

    @Field(name = "valorUnitario")
    BigDecimal valorUnitario,

    @Field(name = "quantidade")
    int quantidade
) {
}
