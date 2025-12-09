package br.com.petfriends.almoxarifado.infra.adapter.persistence.mapper;

import br.com.petfriends.almoxarifado.core.model.ItemEstoque;
import br.com.petfriends.almoxarifado.core.model.Reserva;
import br.com.petfriends.almoxarifado.infra.adapter.persistence.entity.ItemEstoqueEntity;
import br.com.petfriends.almoxarifado.infra.adapter.persistence.entity.ReservaEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class ItemEstoqueInfraMapperTest {
    @Mock
    private ReservaInfraMapper reservaMapper;

    @InjectMocks
    private ItemEstoqueInfraMapper mapper;

    @BeforeEach
    void setup() {
        reservaMapper = new ReservaInfraMapper();
        mapper = new ItemEstoqueInfraMapper(reservaMapper);
    }

    @Test
    void deveConverterItemEstoqueEntityParaModel() {
        ItemEstoqueEntity entity = new ItemEstoqueEntity(
                "item-1",
                10,
                2,
                List.of(new ReservaEntity("pedido-1", 5))
        );

        ItemEstoque model = mapper.toModel(entity);

        assertEquals(entity.itemId(), model.getItemId());
        assertEquals(entity.quantidadeDisponivel(), model.getQuantidadeDisponivel());
        assertEquals(entity.quantidadeReservada(), model.getQuantidadeReservada());
        assertTrue(model.getReservas().containsKey("pedido-1"));
        assertEquals(5, model.getReservas().get("pedido-1").getQuantidade());
    }

    @Test
    void deveConverterItemEstoqueModelParaEntity() {
        Reserva reserva = new Reserva("pedido-1", 5);
        ItemEstoque model = new ItemEstoque(
                "item-1",
                10,
                2,
                Map.of("pedido-1", reserva)
        );

        ItemEstoqueEntity entity = mapper.toEntity(model);

        assertEquals(model.getItemId(), entity.itemId());
        assertEquals(model.getQuantidadeDisponivel(), entity.quantidadeDisponivel());
        assertEquals(model.getQuantidadeReservada(), entity.quantidadeReservada());
        assertEquals(1, entity.reservas().size());
        assertEquals("pedido-1", entity.reservas().get(0).pedidoId());
        assertEquals(5, entity.reservas().get(0).quantidade());
    }
}
