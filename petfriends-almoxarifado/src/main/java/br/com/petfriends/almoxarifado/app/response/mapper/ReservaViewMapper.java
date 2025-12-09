package br.com.petfriends.almoxarifado.app.response.mapper;

import br.com.petfriends.almoxarifado.app.response.dto.ReservaViewDTO;
import br.com.petfriends.almoxarifado.core.model.Reserva;
import org.springframework.stereotype.Component;

@Component
public class ReservaViewMapper {
    public ReservaViewDTO toDTO(Reserva model) {
        return new ReservaViewDTO(model.getPedidoId(), model.getQuantidade());
    }
}
