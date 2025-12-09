package br.com.petfriends.pedido.app.request.dto;

import java.time.Instant;

public record EntregaEntregueEventDTO(
    String id,
    Instant timestamp
) {
}
