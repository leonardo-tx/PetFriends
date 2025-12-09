package br.com.petfriends.transporte.infra.adapter.persistence.mapper;

import br.com.petfriends.transporte.core.model.ItemRemessa;
import br.com.petfriends.transporte.core.model.Remessa;
import br.com.petfriends.transporte.core.model.RemessaStatus;
import br.com.petfriends.transporte.infra.adapter.persistence.entity.ItemRemessaEntity;
import br.com.petfriends.transporte.infra.adapter.persistence.entity.RemessaEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RemessaInfraMapperTest {
    @Mock
    private ItemRemessaInfraMapper itemRemessaInfraMapper;

    @InjectMocks
    private RemessaInfraMapper mapper;

    @Test
    void deveConverterEntityParaModel() {
        ItemRemessaEntity itemEntity = new ItemRemessaEntity("item-1", 2);
        RemessaEntity remessaEntity = new RemessaEntity(
                "almox-1",
                RemessaStatus.SEPARADA,
                List.of(itemEntity)
        );
        ItemRemessa itemModel = new ItemRemessa("item-1", 2);

        when(itemRemessaInfraMapper.toModel(itemEntity)).thenReturn(itemModel);

        Remessa model = mapper.toModel(remessaEntity);

        assertEquals(remessaEntity.almoxarifadoId(), model.getAlmoxarifadoId());
        assertEquals(remessaEntity.status(), model.getStatus());
        assertEquals(1, model.getItems().size());
        assertEquals(itemModel, model.getItems().get(0));
    }

    @Test
    void deveConverterModelParaEntity() {
        ItemRemessa itemModel = new ItemRemessa("item-2", 3);
        Remessa remessa = new Remessa(
                "almox-2",
                RemessaStatus.EM_ROTA_DE_ENTREGA,
                List.of(itemModel)
        );
        ItemRemessaEntity itemEntity = new ItemRemessaEntity("item-2", 3);

        when(itemRemessaInfraMapper.toEntity(itemModel)).thenReturn(itemEntity);

        RemessaEntity entity = mapper.toEntity(remessa);

        assertEquals(remessa.getAlmoxarifadoId(), entity.almoxarifadoId());
        assertEquals(remessa.getStatus(), entity.status());
        assertEquals(1, entity.items().size());
        assertEquals(itemEntity, entity.items().get(0));
    }
}

