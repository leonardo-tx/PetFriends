package br.com.petfriends.transporte.app.request.dto;

import java.time.Instant;
import java.util.List;

public record PedidoReservaConcluidoEventDTO(
    String id,
    Instant timestamp,
    List<ReservaEventDTO> reservas
) {
}
