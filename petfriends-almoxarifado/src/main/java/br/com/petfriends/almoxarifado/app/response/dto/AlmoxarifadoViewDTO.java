package br.com.petfriends.almoxarifado.app.response.dto;

import java.util.List;
import java.util.UUID;

public record AlmoxarifadoViewDTO(UUID id, String nome, List<ItemEstoqueViewDTO> estoques) {
}
