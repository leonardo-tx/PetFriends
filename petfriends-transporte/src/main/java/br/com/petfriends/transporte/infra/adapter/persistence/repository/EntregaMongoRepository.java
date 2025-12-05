package br.com.petfriends.transporte.infra.adapter.persistence.repository;

import br.com.petfriends.transporte.infra.adapter.persistence.entity.EntregaEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;
import java.util.UUID;

public interface EntregaMongoRepository extends MongoRepository<EntregaEntity, UUID> {
    Optional<EntregaEntity> findByPedidoId(String pedidoId);
}
