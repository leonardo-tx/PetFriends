package br.com.petfriends.transporte.app.request.dto;

import java.time.Instant;

public record PedidoPagoEventDTO(String id, Instant timestamp) {
}
