package br.com.petfriends.almoxarifado.infra.adapter.persistence.entity;

import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

public record ItemEstoqueEntity(
    @Field(name = "itemId")
    String itemId,

    @Field(name = "quantidadeDisponivel")
    int quantidadeDisponivel,

    @Field(name = "quantidadeReservada")
    int quantidadeReservada,

    @Field(name = "reservas")
    List<ReservaEntity> reservas
) {
}
