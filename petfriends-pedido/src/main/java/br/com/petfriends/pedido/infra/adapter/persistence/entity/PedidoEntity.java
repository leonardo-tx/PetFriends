package br.com.petfriends.pedido.infra.adapter.persistence.entity;

import br.com.petfriends.pedido.core.model.PedidoStatus;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;
import java.util.UUID;

@Document(collection = "pedidos")
public record PedidoEntity(
    @Id
    UUID id,

    @Field(name = "clienteId")
    String clienteId,

    @Field(name = "status")
    PedidoStatus status,

    @Field(name = "itens")
    List<ItemPedidoEntity> itens
) {
}
