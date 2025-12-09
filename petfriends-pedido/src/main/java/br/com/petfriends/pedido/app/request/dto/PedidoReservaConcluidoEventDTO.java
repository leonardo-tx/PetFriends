package br.com.petfriends.pedido.app.request.dto;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record PedidoReservaConcluidoEventDTO(
    UUID id,
    Instant timestamp,
    List<ReservaEventDTO> reservas
) {
}
