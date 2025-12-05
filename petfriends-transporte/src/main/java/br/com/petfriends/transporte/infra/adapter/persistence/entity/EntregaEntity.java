package br.com.petfriends.transporte.infra.adapter.persistence.entity;

import br.com.petfriends.transporte.core.model.EntregaStatus;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;
import java.util.UUID;

@Document(collection = "entregas")
public record EntregaEntity(
    @Id
    UUID id,

    @Field(name = "pedidoId")
    String pedidoId,

    @Field(name = "status")
    EntregaStatus status,

    @Field(name = "remessas")
    List<RemessaEntity> remessas
) {
}
