package br.com.petfriends.almoxarifado.app.response.mapper;

import br.com.petfriends.almoxarifado.app.response.dto.ItemEstoqueViewDTO;
import br.com.petfriends.almoxarifado.app.response.dto.ReservaViewDTO;
import br.com.petfriends.almoxarifado.core.model.ItemEstoque;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ItemEstoqueViewMapper {
    private final ReservaViewMapper reservaViewMapper;

    public ItemEstoqueViewMapper(ReservaViewMapper reservaViewMapper) {
        this.reservaViewMapper = reservaViewMapper;
    }

    public ItemEstoqueViewDTO toDTO(ItemEstoque model) {
        List<ReservaViewDTO> reservas = model.getReservas()
                .values()
                .stream()
                .map(reservaViewMapper::toDTO)
                .toList();
        return new ItemEstoqueViewDTO(
                model.getItemId(),
                model.getQuantidadeDisponivel(),
                model.getQuantidadeReservada(),
                reservas
        );
    }
}
