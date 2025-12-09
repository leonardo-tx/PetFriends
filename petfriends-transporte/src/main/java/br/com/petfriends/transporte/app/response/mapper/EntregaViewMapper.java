package br.com.petfriends.transporte.app.response.mapper;

import br.com.petfriends.transporte.app.response.dto.EntregaViewDTO;
import br.com.petfriends.transporte.app.response.dto.RemessaViewDTO;
import br.com.petfriends.transporte.core.model.Entrega;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EntregaViewMapper {
    private final RemessaViewMapper remessaViewMapper;

    public EntregaViewMapper(RemessaViewMapper remessaViewMapper) {
        this.remessaViewMapper = remessaViewMapper;
    }

    public EntregaViewDTO toDTO(Entrega model) {
        List<RemessaViewDTO> remessas = model.getRemessas()
                .values()
                .stream()
                .map(remessaViewMapper::toDTO)
                .toList();
        return new EntregaViewDTO(model.getId(), model.getPedidoId(), model.getStatus(), remessas);
    }
}
