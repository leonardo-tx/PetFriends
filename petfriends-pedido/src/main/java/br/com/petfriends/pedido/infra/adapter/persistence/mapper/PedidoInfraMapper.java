package br.com.petfriends.pedido.infra.adapter.persistence.mapper;

import br.com.petfriends.pedido.core.model.ItemPedido;
import br.com.petfriends.pedido.core.model.Pedido;
import br.com.petfriends.pedido.infra.adapter.persistence.entity.ItemPedidoEntity;
import br.com.petfriends.pedido.infra.adapter.persistence.entity.PedidoEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PedidoInfraMapper {
    private final ItemPedidoInfraMapper itemPedidoInfraMapper;

    public PedidoInfraMapper(ItemPedidoInfraMapper itemPedidoInfraMapper) {
        this.itemPedidoInfraMapper = itemPedidoInfraMapper;
    }

    public Pedido toModel(PedidoEntity entity) {
        List<ItemPedido> itens = entity.itens()
                .stream()
                .map(itemPedidoInfraMapper::toModel)
                .toList();
        return new Pedido(entity.id(), entity.clienteId(), entity.status(), itens);
    }

    public PedidoEntity toEntity(Pedido model) {
        List<ItemPedidoEntity> itens = model.getItens()
                .stream()
                .map(itemPedidoInfraMapper::toEntity)
                .toList();
        return new PedidoEntity(model.getId(), model.getClienteId(), model.getStatus(), itens);
    }
}
