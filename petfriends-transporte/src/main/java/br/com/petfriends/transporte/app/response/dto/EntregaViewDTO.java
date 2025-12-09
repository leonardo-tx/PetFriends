package br.com.petfriends.transporte.app.response.dto;

import br.com.petfriends.transporte.core.model.EntregaStatus;

import java.util.List;
import java.util.UUID;

public record EntregaViewDTO(
    UUID id,
    String pedidoId,
    EntregaStatus status,
    List<RemessaViewDTO> remessas
) {
}
