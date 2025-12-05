package br.com.petfriends.transporte.infra.adapter.persistence.mapper;

import br.com.petfriends.transporte.core.model.ItemRemessa;
import br.com.petfriends.transporte.core.model.Remessa;
import br.com.petfriends.transporte.infra.adapter.persistence.entity.ItemRemessaEntity;
import br.com.petfriends.transporte.infra.adapter.persistence.entity.RemessaEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RemessaInfraMapper {
    private final ItemRemessaInfraMapper itemRemessaInfraMapper;

    public RemessaInfraMapper(ItemRemessaInfraMapper itemRemessaInfraMapper) {
        this.itemRemessaInfraMapper = itemRemessaInfraMapper;
    }

    public Remessa toModel(RemessaEntity entity) {
        List<ItemRemessa> items = entity.items()
                .stream()
                .map(itemRemessaInfraMapper::toModel)
                .toList();
        return new Remessa(entity.almoxarifadoId(), entity.status(), items);
    }

    public RemessaEntity toEntity(Remessa model) {
        List<ItemRemessaEntity> items = model.getItems()
                .stream()
                .map(itemRemessaInfraMapper::toEntity)
                .toList();
        return new RemessaEntity(model.getAlmoxarifadoId(), model.getStatus(), items);
    }
}
