package br.com.petfriends.almoxarifado.infra.adapter.persistence.mapper;

import br.com.petfriends.almoxarifado.core.model.Almoxarifado;
import br.com.petfriends.almoxarifado.core.model.AlmoxarifadoNome;
import br.com.petfriends.almoxarifado.core.model.ItemEstoque;
import br.com.petfriends.almoxarifado.infra.adapter.persistence.entity.AlmoxarifadoEntity;
import br.com.petfriends.almoxarifado.infra.adapter.persistence.entity.ItemEstoqueEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AlmoxarifadoInfraMapperTest {
    @Mock
    private ReservaInfraMapper reservaMapper;

    @Mock
    private ItemEstoqueInfraMapper itemMapper;

    @InjectMocks
    private AlmoxarifadoInfraMapper mapper;

    @Test
    void deveConverterAlmoxarifadoEntityParaModel() {
        ItemEstoqueEntity itemEntity = new ItemEstoqueEntity("item-1", 10, 2, List.of());
        AlmoxarifadoEntity entity = new AlmoxarifadoEntity(UUID.randomUUID(), "Almox Teste", List.of(itemEntity));

        ItemEstoque mockItem = mock(ItemEstoque.class);
        when(itemMapper.toModel(itemEntity)).thenReturn(mockItem);
        when(mockItem.getQuantidadeDisponivel()).thenReturn(10);

        Almoxarifado model = mapper.toModel(entity);

        assertEquals(entity.id(), model.getId());
        assertEquals(entity.nome(), model.getNome().valor());
        assertTrue(model.getEstoques().containsKey("item-1"));
        assertEquals(10, model.getEstoques().get("item-1").getQuantidadeDisponivel());
        verify(itemMapper, times(1)).toModel(itemEntity);
    }

    @Test
    void deveConverterAlmoxarifadoModelParaEntity() {
        ItemEstoque mockItem = mock(ItemEstoque.class);
        Almoxarifado model = new Almoxarifado(
                UUID.randomUUID(),
                new AlmoxarifadoNome("Almox Teste"),
                Map.of("item-1", mockItem)
        );

        AlmoxarifadoEntity entity = mapper.toEntity(model);

        assertEquals(model.getId(), entity.id());
        assertEquals(model.getNome().valor(), entity.nome());
        verify(itemMapper, times(1)).toEntity(mockItem);
    }
}
