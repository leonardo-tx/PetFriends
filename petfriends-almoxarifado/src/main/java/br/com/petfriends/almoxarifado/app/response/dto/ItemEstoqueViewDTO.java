package br.com.petfriends.almoxarifado.app.response.dto;

public record ItemEstoqueViewDTO(String itemId, int quantidadeDisponivel, int quantidadeReservada) {
}
