package br.com.petfriends.pedido.app.request.dto;

import java.time.Instant;
import java.util.UUID;

public record PedidoReservaFalhouEventDTO(
    UUID id,
    Instant timestamp
) {
}
