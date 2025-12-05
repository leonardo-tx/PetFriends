package br.com.petfriends.transporte.infra.adapter.persistence.entity;

import br.com.petfriends.transporte.core.model.RemessaStatus;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

public record RemessaEntity(
    @Field(name = "almoxarifadoId")
    String almoxarifadoId,

    @Field(name = "status")
    RemessaStatus status,

    @Field(name = "items")
    List<ItemRemessaEntity> items
) {
}
