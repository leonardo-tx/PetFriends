package br.com.petfriends.transporte.infra.adapter.messenger.dto;

import java.time.Instant;
import java.util.UUID;

public record EntregaEntregueEventDTO(UUID id, Instant timestamp) {
}
