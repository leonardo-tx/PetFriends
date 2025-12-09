package br.com.petfriends.pedido.core.port.out;

import br.com.petfriends.pedido.core.model.Pedido;

import java.util.Optional;
import java.util.UUID;

public interface FindPedidoPort {
    Optional<Pedido> findById(UUID id);
    Optional<Pedido> findByEntregaId(String id);
}
