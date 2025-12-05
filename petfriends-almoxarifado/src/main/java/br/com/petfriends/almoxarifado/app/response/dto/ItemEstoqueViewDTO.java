package br.com.petfriends.almoxarifado.app.response.dto;

import java.util.List;

public record ItemEstoqueViewDTO(
        String itemId,
        int quantidadeDisponivel,
        int quantidadeReservada,
        List<ReservaViewDTO> reservas
) {
}
