package br.com.petfriends.transporte.infra.adapter.messenger.dto;

import java.time.Instant;
import java.util.UUID;

public record EntregaSeparadaEventDTO(UUID id, Instant timestamp) {
}
