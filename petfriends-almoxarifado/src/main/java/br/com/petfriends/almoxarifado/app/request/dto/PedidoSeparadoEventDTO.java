package br.com.petfriends.almoxarifado.app.request.dto;

import java.time.Instant;

public record PedidoSeparadoEventDTO(
    String id,
    Instant timestamp
) {
}
