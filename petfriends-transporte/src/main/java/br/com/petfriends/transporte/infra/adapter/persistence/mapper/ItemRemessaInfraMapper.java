package br.com.petfriends.transporte.infra.adapter.persistence.mapper;

import br.com.petfriends.transporte.core.model.ItemRemessa;
import br.com.petfriends.transporte.infra.adapter.persistence.entity.ItemRemessaEntity;
import org.springframework.stereotype.Component;

@Component
public class ItemRemessaInfraMapper {
    public ItemRemessa toModel(ItemRemessaEntity entity) {
        return new ItemRemessa(entity.itemId(), entity.quantidade());
    }

    public ItemRemessaEntity toEntity(ItemRemessa model) {
        return new ItemRemessaEntity(model.getItemId(), model.getQuantidade());
    }
}
