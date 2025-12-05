package br.com.petfriends.almoxarifado.app.response.mapper;

import br.com.petfriends.almoxarifado.app.response.dto.ItemEstoqueViewDTO;
import br.com.petfriends.almoxarifado.core.model.ItemEstoque;
import org.springframework.stereotype.Component;

@Component
public class ItemEstoqueViewMapper {
    public ItemEstoqueViewDTO toDTO(ItemEstoque model) {
        return new ItemEstoqueViewDTO(
                model.getItemId(),
                model.getQuantidadeDisponivel(),
                model.getQuantidadeReservada()
        );
    }
}
