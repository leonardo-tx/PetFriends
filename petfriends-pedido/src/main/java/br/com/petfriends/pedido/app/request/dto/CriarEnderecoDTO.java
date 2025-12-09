package br.com.petfriends.pedido.app.request.dto;

public record CriarEnderecoDTO(
    String rua,
    String numero,
    String complemento,
    String bairro,
    String cidade,
    String estado,
    String cep
) {
}
