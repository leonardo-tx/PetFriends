package br.com.petfriends.almoxarifado.app.request.dto;

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
