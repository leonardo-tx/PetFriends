package br.com.petfriends.almoxarifado.infra.adapter.persistence.mapper;

import br.com.petfriends.almoxarifado.core.model.Almoxarifado;
import br.com.petfriends.almoxarifado.core.model.AlmoxarifadoNome;
import br.com.petfriends.almoxarifado.core.model.ItemEstoque;
import br.com.petfriends.almoxarifado.infra.adapter.persistence.entity.AlmoxarifadoEntity;
import br.com.petfriends.almoxarifado.infra.adapter.persistence.entity.ItemEstoqueEntity;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Component
public class AlmoxarifadoInfraMapper {
    private final ItemEstoqueInfraMapper itemEstoqueInfraMapper;

    public AlmoxarifadoInfraMapper(ItemEstoqueInfraMapper itemEstoqueInfraMapper) {
        this.itemEstoqueInfraMapper = itemEstoqueInfraMapper;
    }

    public Almoxarifado toModel(AlmoxarifadoEntity entity) {
        AlmoxarifadoNome nome = AlmoxarifadoNome.valueOf(entity.nome());
        Collector<ItemEstoqueEntity, ?, HashMap<String, ItemEstoque>> collector = Collectors.toMap(
                ItemEstoqueEntity::itemId,
                itemEstoqueInfraMapper::toModel,
                (a, b) -> a,
                HashMap::new
        );
        HashMap<String, ItemEstoque> estoques = entity.estoques()
                .stream()
                .collect(collector);
        return new Almoxarifado(entity.id(), nome, estoques);
    }

    public AlmoxarifadoEntity toEntity(Almoxarifado model) {
        List<ItemEstoqueEntity> estoques = model.getEstoques()
                .values()
                .stream()
                .map(itemEstoqueInfraMapper::toEntity)
                .toList();
        return new AlmoxarifadoEntity(model.getId(), model.getNome().getValor(), estoques);
    }
}
