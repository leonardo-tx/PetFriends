package br.com.petfriends.pedido.app.response.mapper;

import br.com.petfriends.pedido.app.response.dto.ItemPedidoViewDTO;
import br.com.petfriends.pedido.app.response.dto.PedidoViewDTO;
import br.com.petfriends.pedido.core.model.Pedido;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PedidoViewMapper {
    private final ItemPedidoViewMapper itemPedidoViewMapper;

    public PedidoViewMapper(ItemPedidoViewMapper itemPedidoViewMapper) {
        this.itemPedidoViewMapper = itemPedidoViewMapper;
    }

    public PedidoViewDTO toDTO(Pedido model) {
        List<ItemPedidoViewDTO> itens = model.getItens()
                .stream()
                .map(itemPedidoViewMapper::toDTO)
                .toList();
        return new PedidoViewDTO(model.getId(), model.getClienteId(), model.getStatus(), itens);
    }
}
