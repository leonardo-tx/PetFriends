package br.com.petfriends.pedido.infra.adapter.messenger.dto;

import java.time.Instant;
import java.util.UUID;

public record PedidoCanceladoEventDTO(UUID id, Instant timestamp) {
}
