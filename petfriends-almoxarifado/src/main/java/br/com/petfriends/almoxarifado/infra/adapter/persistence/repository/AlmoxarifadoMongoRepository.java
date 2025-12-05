package br.com.petfriends.almoxarifado.infra.adapter.persistence.repository;

import br.com.petfriends.almoxarifado.infra.adapter.persistence.entity.AlmoxarifadoEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface AlmoxarifadoMongoRepository extends MongoRepository<AlmoxarifadoEntity, UUID> {
    @Query("""
    {
      "estoques": {
        "$elemMatch": {
          "itemId": ?0,
          "$expr": {
            "$gte": [
              { "$subtract": ["$quantidadeDisponivel", "$quantidadeReservada"] },
              ?1
            ]
          }
        }
      }
    }
    """)
    Optional<AlmoxarifadoEntity> findFirstByItemDisponivel(String itemId, int quantidadePedida);
}
