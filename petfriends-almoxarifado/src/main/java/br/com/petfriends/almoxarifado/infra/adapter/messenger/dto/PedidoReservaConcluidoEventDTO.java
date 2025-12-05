package br.com.petfriends.almoxarifado.infra.adapter.messenger.dto;

import java.time.Instant;
import java.util.List;

public record PedidoReservaConcluidoEventDTO(
    String id,
    Instant timestamp,
    List<ReservaEventDTO> reservas
) {
}
