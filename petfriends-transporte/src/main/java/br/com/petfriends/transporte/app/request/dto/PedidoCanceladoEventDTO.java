package br.com.petfriends.transporte.app.request.dto;

import java.time.Instant;

public record PedidoCanceladoEventDTO(String id, Instant timestamp) {
}
