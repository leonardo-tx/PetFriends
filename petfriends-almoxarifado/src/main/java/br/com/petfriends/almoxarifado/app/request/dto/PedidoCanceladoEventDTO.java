package br.com.petfriends.almoxarifado.app.request.dto;

import java.time.Instant;

public record PedidoCanceladoEventDTO(
    String id,
    Instant timestamp
) {
}
