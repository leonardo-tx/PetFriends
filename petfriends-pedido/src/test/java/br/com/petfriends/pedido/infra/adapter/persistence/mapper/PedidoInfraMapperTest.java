package br.com.petfriends.pedido.infra.adapter.persistence.mapper;

import br.com.petfriends.pedido.core.model.*;
import br.com.petfriends.pedido.infra.adapter.persistence.entity.EnderecoEntity;
import br.com.petfriends.pedido.infra.adapter.persistence.entity.ItemPedidoEntity;
import br.com.petfriends.pedido.infra.adapter.persistence.entity.PedidoEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PedidoInfraMapperTest {
    @Mock
    private ItemPedidoInfraMapper itemPedidoInfraMapper;

    @Mock
    private EnderecoInfraMapper enderecoInfraMapper;

    @InjectMocks
    private PedidoInfraMapper mapper;

    @Test
    void deveConverterEntityParaModel() {
        UUID pedidoId = UUID.randomUUID();
        EnderecoEntity enderecoEntity = new EnderecoEntity("Rua A", "123", null, "Bairro", "Cidade", "Estado", "12345678");
        ItemPedidoEntity itemEntity = new ItemPedidoEntity("produto-1", new BigDecimal("10.50"), 2);

        PedidoEntity pedidoEntity = new PedidoEntity(
                pedidoId,
                "cliente-123",
                PedidoStatus.CRIADO,
                enderecoEntity,
                "entrega-123",
                List.of(itemEntity)
        );

        Endereco enderecoModel = new Endereco("Rua A", "123", null, "Bairro", "Cidade", "Estado", new CEP("12345678"));
        ItemPedido itemModel = new ItemPedido("produto-1", new Dinheiro(new BigDecimal("10.50")), 2);

        when(enderecoInfraMapper.toModel(enderecoEntity)).thenReturn(enderecoModel);
        when(itemPedidoInfraMapper.toModel(itemEntity)).thenReturn(itemModel);

        Pedido model = mapper.toModel(pedidoEntity);

        assertEquals(pedidoEntity.id(), model.getId());
        assertEquals(pedidoEntity.clienteId(), model.getClienteId());
        assertEquals(pedidoEntity.status(), model.getStatus());
        assertEquals(pedidoEntity.entregaId(), model.getEntregaId());
        assertEquals(1, model.getItens().size());
        assertEquals(itemModel, model.getItens().get(0));
        assertEquals(enderecoModel, model.getEndereco());
    }

    @Test
    void deveConverterModelParaEntity() {
        UUID pedidoId = UUID.randomUUID();
        Endereco enderecoModel = new Endereco("Rua B", "456", "Apto 1", "Bairro", "Cidade", "Estado", new CEP("87654321"));
        ItemPedido itemModel = new ItemPedido("produto-2", new Dinheiro(new BigDecimal("20.00")), 5);

        Pedido pedidoModel = new Pedido(
                pedidoId,
                "cliente-456",
                PedidoStatus.INICIADO,
                enderecoModel,
                "entrega-456",
                List.of(itemModel)
        );

        EnderecoEntity enderecoEntity = new EnderecoEntity("Rua B", "456", "Apto 1", "Bairro", "Cidade", "Estado", "87654321");
        ItemPedidoEntity itemEntity = new ItemPedidoEntity("produto-2", new BigDecimal("20.00"), 5);

        when(enderecoInfraMapper.toEntity(enderecoModel)).thenReturn(enderecoEntity);
        when(itemPedidoInfraMapper.toEntity(itemModel)).thenReturn(itemEntity);

        PedidoEntity entity = mapper.toEntity(pedidoModel);

        assertEquals(pedidoModel.getId(), entity.id());
        assertEquals(pedidoModel.getClienteId(), entity.clienteId());
        assertEquals(pedidoModel.getStatus(), entity.status());
        assertEquals(pedidoModel.getEntregaId(), entity.entregaId());
        assertEquals(1, entity.itens().size());
        assertEquals(itemEntity, entity.itens().get(0));
        assertEquals(enderecoEntity, entity.endereco());
    }
}
