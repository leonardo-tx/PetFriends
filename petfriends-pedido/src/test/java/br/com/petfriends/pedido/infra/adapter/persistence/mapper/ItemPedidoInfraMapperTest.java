package br.com.petfriends.pedido.infra.adapter.persistence.mapper;

import br.com.petfriends.pedido.core.model.Dinheiro;
import br.com.petfriends.pedido.core.model.ItemPedido;
import br.com.petfriends.pedido.infra.adapter.persistence.entity.ItemPedidoEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class ItemPedidoInfraMapperTest {
    @InjectMocks
    private ItemPedidoInfraMapper mapper;

    @Test
    void deveConverterEntityParaModel() {
        ItemPedidoEntity entity = new ItemPedidoEntity("produto-123", new BigDecimal("10.50"), 3);

        ItemPedido model = mapper.toModel(entity);

        assertEquals(entity.produtoId(), model.getProdutoId());
        assertEquals(entity.valorUnitario(), model.getValorUnitario().valor());
        assertEquals(entity.quantidade(), model.getQuantidade());
    }

    @Test
    void deveConverterModelParaEntity() {
        ItemPedido model = new ItemPedido("produto-456", new Dinheiro(new BigDecimal("25.75")), 2);

        ItemPedidoEntity entity = mapper.toEntity(model);

        assertEquals(model.getProdutoId(), entity.produtoId());
        assertEquals(model.getValorUnitario().valor(), entity.valorUnitario());
        assertEquals(model.getQuantidade(), entity.quantidade());
    }
}
