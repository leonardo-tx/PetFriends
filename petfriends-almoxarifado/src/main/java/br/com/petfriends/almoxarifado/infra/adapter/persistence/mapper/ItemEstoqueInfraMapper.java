package br.com.petfriends.almoxarifado.infra.adapter.persistence.mapper;

import br.com.petfriends.almoxarifado.core.model.ItemEstoque;
import br.com.petfriends.almoxarifado.infra.adapter.persistence.entity.ItemEstoqueEntity;
import org.springframework.stereotype.Component;

@Component
public class ItemEstoqueInfraMapper {
    public ItemEstoque toModel(ItemEstoqueEntity entity) {
        return new ItemEstoque(
                entity.itemId(),
                entity.quantidadeDisponivel(),
                entity.quantidadeReservada()
        );
    }

    public ItemEstoqueEntity toEntity(ItemEstoque model) {
        return new ItemEstoqueEntity(
                model.getItemId(),
                model.getQuantidadeDisponivel(),
                model.getQuantidadeReservada()
        );
    }
}
