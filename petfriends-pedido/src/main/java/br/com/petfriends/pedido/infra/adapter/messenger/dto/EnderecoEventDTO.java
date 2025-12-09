package br.com.petfriends.pedido.infra.adapter.messenger.dto;

public record EnderecoEventDTO(
    String rua,
    String numero,
    String complemento,
    String bairro,
    String cidade,
    String estado,
    String cep
) {
}
