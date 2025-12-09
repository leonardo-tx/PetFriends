package br.com.petfriends.transporte.infra.adapter.persistence.mapper;

import br.com.petfriends.transporte.core.model.ItemRemessa;
import br.com.petfriends.transporte.infra.adapter.persistence.entity.ItemRemessaEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class ItemRemessaInfraMapperTest {
    @InjectMocks
    private ItemRemessaInfraMapper mapper;

    @Test
    void deveConverterEntityParaModel() {
        ItemRemessaEntity entity = new ItemRemessaEntity("item-1", 5);

        ItemRemessa model = mapper.toModel(entity);

        assertEquals(entity.itemId(), model.getItemId());
        assertEquals(entity.quantidade(), model.getQuantidade());
    }

    @Test
    void deveConverterModelParaEntity() {
        ItemRemessa model = new ItemRemessa("item-2", 3);

        ItemRemessaEntity entity = mapper.toEntity(model);

        assertEquals(model.getItemId(), entity.itemId());
        assertEquals(model.getQuantidade(), entity.quantidade());
    }
}

