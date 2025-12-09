package br.com.petfriends.pedido.infra.adapter.persistence.entity;

import org.springframework.data.mongodb.core.mapping.Field;

public record EnderecoEntity(
    @Field(name = "rua")
    String rua,

    @Field(name = "numero")
    String numero,

    @Field(name = "complemento")
    String complemento,

    @Field(name = "bairro")
    String bairro,

    @Field(name = "cidade")
    String cidade,

    @Field(name = "estado")
    String estado,

    @Field(name = "cep")
    String cep
) {
}
