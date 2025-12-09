package br.com.petfriends.almoxarifado.infra.adapter.persistence.mapper;

import br.com.petfriends.almoxarifado.core.model.Reserva;
import br.com.petfriends.almoxarifado.infra.adapter.persistence.entity.ReservaEntity;
import org.springframework.stereotype.Component;

@Component
public class ReservaInfraMapper {
    public Reserva toModel(ReservaEntity entity) {
        return new Reserva(entity.pedidoId(), entity.quantidade());
    }

    public ReservaEntity toEntity(Reserva model) {
        return new ReservaEntity(model.getPedidoId(), model.getQuantidade());
    }
}
