package br.com.petfriends.pedido.app.request.dto;

import java.time.Instant;

public record EntregaSeparadaEventDTO(
    String id,
    Instant timestamp
) {
}
