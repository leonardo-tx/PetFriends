package br.com.petfriends.pedido.app.response.dto;

public record EnderecoViewDTO(
    String rua,
    String numero,
    String complemento,
    String bairro,
    String cidade,
    String estado,
    String cep
) {
}
