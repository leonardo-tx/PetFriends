package br.com.petfriends.almoxarifado.infra.adapter.persistence.entity;

import org.springframework.data.mongodb.core.mapping.Field;

public record ReservaEntity(
    @Field(name = "pedidoId")
    String pedidoId,

    @Field(name = "quantidade")
    int quantidade
) {
}
