package br.com.petfriends.almoxarifado.app.response.mapper;

import br.com.petfriends.almoxarifado.app.response.dto.AlmoxarifadoViewDTO;
import br.com.petfriends.almoxarifado.app.response.dto.ItemEstoqueViewDTO;
import br.com.petfriends.almoxarifado.core.model.Almoxarifado;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AlmoxarifadoViewMapper {
    private final ItemEstoqueViewMapper itemEstoqueViewMapper;

    public AlmoxarifadoViewMapper(ItemEstoqueViewMapper itemEstoqueViewMapper) {
        this.itemEstoqueViewMapper = itemEstoqueViewMapper;
    }

    public AlmoxarifadoViewDTO toDTO(Almoxarifado model) {
        List<ItemEstoqueViewDTO> estoques = model.getEstoques()
                .values()
                .stream()
                .map(itemEstoqueViewMapper::toDTO)
                .toList();
        return new AlmoxarifadoViewDTO(model.getId(), model.getNome().valor(), estoques);
    }
}
