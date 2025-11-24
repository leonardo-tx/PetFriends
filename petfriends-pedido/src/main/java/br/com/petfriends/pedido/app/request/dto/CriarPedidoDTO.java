package br.com.petfriends.pedido.app.request.dto;

import java.util.List;

public record CriarPedidoDTO(String clienteId, List<CriarItemPedidoDTO> itens) {
}
