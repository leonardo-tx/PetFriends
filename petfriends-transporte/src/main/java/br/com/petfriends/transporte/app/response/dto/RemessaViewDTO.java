package br.com.petfriends.transporte.app.response.dto;

import br.com.petfriends.transporte.core.model.RemessaStatus;

import java.util.List;

public record RemessaViewDTO(
    String almoxarifadoId,
    RemessaStatus status,
    List<ItemRemessaViewDTO> items
) {
}
