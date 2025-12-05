package br.com.petfriends.pedido.infra.adapter.persistence.mapper;

import br.com.petfriends.pedido.core.model.Endereco;
import br.com.petfriends.pedido.core.model.ItemPedido;
import br.com.petfriends.pedido.core.model.Pedido;
import br.com.petfriends.pedido.infra.adapter.persistence.entity.EnderecoEntity;
import br.com.petfriends.pedido.infra.adapter.persistence.entity.ItemPedidoEntity;
import br.com.petfriends.pedido.infra.adapter.persistence.entity.PedidoEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PedidoInfraMapper {
    private final ItemPedidoInfraMapper itemPedidoInfraMapper;
    private final EnderecoInfraMapper enderecoInfraMapper;

    public PedidoInfraMapper(ItemPedidoInfraMapper itemPedidoInfraMapper, EnderecoInfraMapper enderecoInfraMapper) {
        this.itemPedidoInfraMapper = itemPedidoInfraMapper;
        this.enderecoInfraMapper = enderecoInfraMapper;
    }

    public Pedido toModel(PedidoEntity entity) {
        List<ItemPedido> itens = entity.itens()
                .stream()
                .map(itemPedidoInfraMapper::toModel)
                .toList();
        Endereco endereco = enderecoInfraMapper.toModel(entity.endereco());
        return new Pedido(entity.id(), entity.clienteId(), entity.status(), endereco, entity.entregaId(), itens);
    }

    public PedidoEntity toEntity(Pedido model) {
        List<ItemPedidoEntity> itens = model.getItens()
                .stream()
                .map(itemPedidoInfraMapper::toEntity)
                .toList();
        EnderecoEntity endereco = enderecoInfraMapper.toEntity(model.getEndereco());
        return new PedidoEntity(model.getId(), model.getClienteId(), model.getStatus(), endereco, model.getEntregaId(), itens);
    }
}
