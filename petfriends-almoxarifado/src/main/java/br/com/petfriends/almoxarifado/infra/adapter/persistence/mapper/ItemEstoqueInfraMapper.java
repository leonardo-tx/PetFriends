package br.com.petfriends.almoxarifado.infra.adapter.persistence.mapper;

import br.com.petfriends.almoxarifado.core.model.ItemEstoque;
import br.com.petfriends.almoxarifado.core.model.Reserva;
import br.com.petfriends.almoxarifado.infra.adapter.persistence.entity.ItemEstoqueEntity;
import br.com.petfriends.almoxarifado.infra.adapter.persistence.entity.ReservaEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ItemEstoqueInfraMapper {

    private final ReservaInfraMapper reservaInfraMapper;

    public ItemEstoqueInfraMapper(ReservaInfraMapper reservaInfraMapper) {
        this.reservaInfraMapper = reservaInfraMapper;
    }

    public ItemEstoque toModel(ItemEstoqueEntity entity) {
        Map<String, Reserva> reservasMap = new HashMap<>();
        for (ReservaEntity r : entity.reservas()) {
            Reserva reserva = reservaInfraMapper.toModel(r);
            reservasMap.put(r.pedidoId(), reserva);
        }
        return new ItemEstoque(
                entity.itemId(),
                entity.quantidadeDisponivel(),
                entity.quantidadeReservada(),
                reservasMap
        );
    }

    public ItemEstoqueEntity toEntity(ItemEstoque model) {
        List<ReservaEntity> reservasList = new ArrayList<>();
        for (Reserva reserva : model.getReservas().values()) {
            ReservaEntity reservaEntity = reservaInfraMapper.toEntity(reserva);
            reservasList.add(reservaEntity);
        }
        return new ItemEstoqueEntity(
                model.getItemId(),
                model.getQuantidadeDisponivel(),
                model.getQuantidadeReservada(),
                reservasList
        );
    }
}
