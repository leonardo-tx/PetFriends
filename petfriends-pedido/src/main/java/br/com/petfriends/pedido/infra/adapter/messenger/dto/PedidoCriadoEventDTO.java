package br.com.petfriends.pedido.infra.adapter.messenger.dto;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record PedidoCriadoEventDTO(
    UUID id,
    Instant timestamp,
    String clienteId,
    EnderecoEventDTO endereco,
    List<ItemPedidoEventDTO> items
) {
}
