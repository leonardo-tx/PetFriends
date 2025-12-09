package br.com.petfriends.pedido.app.response.mapper;

import br.com.petfriends.pedido.app.response.dto.EnderecoViewDTO;
import br.com.petfriends.pedido.app.response.dto.ItemPedidoViewDTO;
import br.com.petfriends.pedido.app.response.dto.PedidoViewDTO;
import br.com.petfriends.pedido.core.model.Pedido;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PedidoViewMapper {
    private final ItemPedidoViewMapper itemPedidoViewMapper;
    private final EnderecoViewMapper enderecoViewMapper;

    public PedidoViewMapper(ItemPedidoViewMapper itemPedidoViewMapper, EnderecoViewMapper enderecoViewMapper) {
        this.itemPedidoViewMapper = itemPedidoViewMapper;
        this.enderecoViewMapper = enderecoViewMapper;
    }

    public PedidoViewDTO toDTO(Pedido model) {
        List<ItemPedidoViewDTO> itens = model.getItens()
                .stream()
                .map(itemPedidoViewMapper::toDTO)
                .toList();
        EnderecoViewDTO endereco = enderecoViewMapper.toDTO(model.getEndereco());
        return new PedidoViewDTO(model.getId(), model.getClienteId(), model.getStatus(), endereco, model.getEntregaId(), itens);
    }
}
