package br.com.petfriends.pedido.infra.adapter.messenger.dto;

import java.time.Instant;
import java.util.UUID;

public record PedidoSeparadoEventDTO(UUID id, Instant timestamp) {
}
