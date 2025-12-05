package br.com.petfriends.transporte.core.port.out;

import br.com.petfriends.transporte.core.model.Entrega;

import java.util.Optional;
import java.util.UUID;

public interface FindEntregaPort {
    Optional<Entrega> findById(UUID id);
    Optional<Entrega> findByPedidoId(String id);
}
