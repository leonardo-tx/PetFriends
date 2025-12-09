package br.com.petfriends.almoxarifado.infra.adapter.persistence.mapper;

import br.com.petfriends.almoxarifado.core.model.Reserva;
import br.com.petfriends.almoxarifado.infra.adapter.persistence.entity.ReservaEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class ReservaInfraMapperTest {
    @InjectMocks
    private ReservaInfraMapper mapper;

    @Test
    void deveConverterReservaEntityParaModel() {
        ReservaEntity entity = new ReservaEntity("pedido-1", 5);

        Reserva model = mapper.toModel(entity);

        assertEquals(entity.pedidoId(), model.getPedidoId());
        assertEquals(entity.quantidade(), model.getQuantidade());
    }

    @Test
    void deveConverterReservaModelParaEntity() {
        Reserva model = new Reserva("pedido-1", 5);

        ReservaEntity entity = mapper.toEntity(model);

        assertEquals(model.getPedidoId(), entity.pedidoId());
        assertEquals(model.getQuantidade(), entity.quantidade());
    }
}
