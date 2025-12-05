package br.com.petfriends.pedido.infra.adapter.persistence.repository;

import br.com.petfriends.pedido.infra.adapter.persistence.entity.PedidoEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;
import java.util.UUID;

public interface PedidoMongoRepository extends MongoRepository<PedidoEntity, UUID> {
    Optional<PedidoEntity> findByEntregaId(String entregaId);
}
