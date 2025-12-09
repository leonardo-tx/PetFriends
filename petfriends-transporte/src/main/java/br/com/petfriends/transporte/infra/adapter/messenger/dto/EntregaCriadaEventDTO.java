package br.com.petfriends.transporte.infra.adapter.messenger.dto;

import java.time.Instant;
import java.util.UUID;

public record EntregaCriadaEventDTO(UUID id, Instant timestamp, String pedidoId) {
}
