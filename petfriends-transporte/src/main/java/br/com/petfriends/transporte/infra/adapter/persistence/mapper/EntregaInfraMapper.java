package br.com.petfriends.transporte.infra.adapter.persistence.mapper;

import br.com.petfriends.transporte.core.model.Entrega;
import br.com.petfriends.transporte.core.model.Remessa;
import br.com.petfriends.transporte.infra.adapter.persistence.entity.EntregaEntity;
import br.com.petfriends.transporte.infra.adapter.persistence.entity.RemessaEntity;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Component
public class EntregaInfraMapper {
    private final RemessaInfraMapper remessaInfraMapper;

    public EntregaInfraMapper(RemessaInfraMapper remessaInfraMapper) {
        this.remessaInfraMapper = remessaInfraMapper;
    }

    public Entrega toModel(EntregaEntity entity) {
        Collector<RemessaEntity, ?, HashMap<String, Remessa>> collector = Collectors.toMap(
                RemessaEntity::almoxarifadoId,
                remessaInfraMapper::toModel,
                (a, b) -> a,
                HashMap::new
        );
        HashMap<String, Remessa> estoques = entity.remessas()
                .stream()
                .collect(collector);
        return new Entrega(entity.id(), entity.pedidoId(), entity.status(), estoques);
    }

    public EntregaEntity toEntity(Entrega model) {
        List<RemessaEntity> remessas = model.getRemessas()
                .values()
                .stream()
                .map(remessaInfraMapper::toEntity)
                .toList();
        return new EntregaEntity(model.getId(), model.getPedidoId(), model.getStatus(), remessas);
    }
}
