package br.com.petfriends.transporte.app.response.mapper;

import br.com.petfriends.transporte.app.response.dto.ItemRemessaViewDTO;
import br.com.petfriends.transporte.app.response.dto.RemessaViewDTO;
import br.com.petfriends.transporte.core.model.Remessa;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RemessaViewMapper {
    private final ItemRemessaViewMapper itemRemessaViewMapper;

    public RemessaViewMapper(ItemRemessaViewMapper itemRemessaViewMapper) {
        this.itemRemessaViewMapper = itemRemessaViewMapper;
    }

    public RemessaViewDTO toDTO(Remessa model) {
        List<ItemRemessaViewDTO> items = model.getItems()
                .stream()
                .map(itemRemessaViewMapper::toDTO)
                .toList();
        return new RemessaViewDTO(model.getAlmoxarifadoId(), model.getStatus(), items);
    }
}
