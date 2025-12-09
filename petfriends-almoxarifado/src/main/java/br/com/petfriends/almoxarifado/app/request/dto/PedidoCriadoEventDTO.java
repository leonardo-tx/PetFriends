package br.com.petfriends.almoxarifado.app.request.dto;

import java.time.Instant;
import java.util.List;

public record PedidoCriadoEventDTO(
    String id,
    Instant timestamp,
    String clienteId,
    EnderecoEventDTO endereco,
    List<ItemPedidoEventDTO> items
) {
}
