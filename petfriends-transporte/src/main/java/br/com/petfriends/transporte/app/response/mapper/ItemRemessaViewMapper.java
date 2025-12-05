package br.com.petfriends.transporte.app.response.mapper;

import br.com.petfriends.transporte.app.response.dto.ItemRemessaViewDTO;
import br.com.petfriends.transporte.core.model.ItemRemessa;
import org.springframework.stereotype.Component;

@Component
public class ItemRemessaViewMapper {
    public ItemRemessaViewDTO toDTO(ItemRemessa model) {
        return new ItemRemessaViewDTO(model.getItemId(), model.getQuantidade());
    }
}
