package br.com.petfriends.transporte.infra.adapter.persistence.entity;

import org.springframework.data.mongodb.core.mapping.Field;

public record ItemRemessaEntity(
    @Field(name = "itemId")
    String itemId,

    @Field(name = "quantidade")
    int quantidade
) {
}
