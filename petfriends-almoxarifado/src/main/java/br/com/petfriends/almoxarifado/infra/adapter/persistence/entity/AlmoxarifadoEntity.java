package br.com.petfriends.almoxarifado.infra.adapter.persistence.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;
import java.util.UUID;

@Document
public record AlmoxarifadoEntity(
    @Id
    UUID id,

    @Field(name = "nome")
    String nome,

    @Field(name = "estoques")
    List<ItemEstoqueEntity> estoques
) {
}
