package br.com.petfriends.almoxarifado.infra.adapter.messenger.dto;

import java.time.Instant;

public record PedidoReservaFalhouEventDTO(
    String id,
    Instant timestamp
) {
}
