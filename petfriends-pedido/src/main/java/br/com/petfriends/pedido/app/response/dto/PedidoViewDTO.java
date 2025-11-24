package br.com.petfriends.pedido.app.response.dto;

import br.com.petfriends.pedido.core.model.PedidoStatus;

import java.util.List;
import java.util.UUID;

public record PedidoViewDTO(UUID id, String clienteId, PedidoStatus status, List<ItemPedidoViewDTO> itens) {
}
