package br.com.petfriends.pedido.app.response.mapper;

import br.com.petfriends.pedido.app.response.dto.ItemPedidoViewDTO;
import br.com.petfriends.pedido.core.model.ItemPedido;
import org.springframework.stereotype.Component;

@Component
public class ItemPedidoViewMapper {
    public ItemPedidoViewDTO toDTO(ItemPedido model) {
        return new ItemPedidoViewDTO(
                model.getProdutoId(),
                model.getValorUnitario().getValor(),
                model.getQuantidade()
        );
    }
}
