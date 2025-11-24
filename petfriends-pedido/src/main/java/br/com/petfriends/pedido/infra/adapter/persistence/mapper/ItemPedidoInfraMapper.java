package br.com.petfriends.pedido.infra.adapter.persistence.mapper;

import br.com.petfriends.pedido.core.model.Dinheiro;
import br.com.petfriends.pedido.core.model.ItemPedido;
import br.com.petfriends.pedido.infra.adapter.persistence.entity.ItemPedidoEntity;
import org.springframework.stereotype.Component;

@Component
public class ItemPedidoInfraMapper {
    public ItemPedido toModel(ItemPedidoEntity entity) {
        return new ItemPedido(
                entity.produtoId(),
                Dinheiro.valueOf(entity.valorUnitario()),
                entity.quantidade()
        );
    }

    public ItemPedidoEntity toEntity(ItemPedido model) {
        return new ItemPedidoEntity(
                model.getProdutoId(),
                model.getValorUnitario().getValor(),
                model.getQuantidade()
        );
    }
}
