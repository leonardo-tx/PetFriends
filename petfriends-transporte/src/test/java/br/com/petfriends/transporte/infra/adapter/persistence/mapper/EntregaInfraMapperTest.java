package br.com.petfriends.transporte.infra.adapter.persistence.mapper;

import br.com.petfriends.transporte.core.model.*;
import br.com.petfriends.transporte.infra.adapter.persistence.entity.EntregaEntity;
import br.com.petfriends.transporte.infra.adapter.persistence.entity.ItemRemessaEntity;
import br.com.petfriends.transporte.infra.adapter.persistence.entity.RemessaEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EntregaInfraMapperTest {
    @Mock
    private RemessaInfraMapper remessaInfraMapper;

    @InjectMocks
    private EntregaInfraMapper mapper;

    @Test
    void deveConverterEntityParaModel() {
        RemessaEntity remessaEntity = new RemessaEntity(
                "almox-1",
                RemessaStatus.SEPARADA,
                List.of(new ItemRemessaEntity("item-1", 2))
        );
        EntregaEntity entregaEntity = new EntregaEntity(
                UUID.randomUUID(),
                "pedido-123",
                EntregaStatus.EM_PROCESSAMENTO,
                List.of(remessaEntity)
        );

        Remessa remessaModel = new Remessa("almox-1", RemessaStatus.SEPARADA, List.of(new ItemRemessa("item-1", 2)));

        when(remessaInfraMapper.toModel(remessaEntity)).thenReturn(remessaModel);

        Entrega model = mapper.toModel(entregaEntity);

        assertEquals(entregaEntity.id(), model.getId());
        assertEquals(entregaEntity.pedidoId(), model.getPedidoId());
        assertEquals(entregaEntity.status(), model.getStatus());
        assertEquals(1, model.getRemessas().size());
        assertEquals(remessaModel, model.getRemessas().get("almox-1"));
    }

    @Test
    void deveConverterModelParaEntity() {
        Remessa remessaModel = new Remessa(
                "almox-2",
                RemessaStatus.EM_ROTA_DE_ENTREGA,
                List.of(new ItemRemessa("item-2", 3))
        );
        Entrega entregaModel = new Entrega(
                UUID.randomUUID(),
                "pedido-456",
                EntregaStatus.EM_PROCESSAMENTO,
                new HashMap<>() {{
                    put("almox-2", remessaModel);
                }}
        );

        RemessaEntity remessaEntity = new RemessaEntity(
                "almox-2",
                RemessaStatus.EM_ROTA_DE_ENTREGA,
                List.of(new ItemRemessaEntity("item-2", 3))
        );

        when(remessaInfraMapper.toEntity(remessaModel)).thenReturn(remessaEntity);

        EntregaEntity entity = mapper.toEntity(entregaModel);

        assertEquals(entregaModel.getId(), entity.id());
        assertEquals(entregaModel.getPedidoId(), entity.pedidoId());
        assertEquals(entregaModel.getStatus(), entity.status());
        assertEquals(1, entity.remessas().size());
        assertEquals(remessaEntity, entity.remessas().get(0));
    }
}
