package br.com.petfriends.pedido.app.request.dto;

import java.time.Instant;

public record EntregaTransportadaEventDTO(
    String id,
    Instant timestamp
) {
}
