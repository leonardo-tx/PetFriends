package br.com.petfriends.almoxarifado.infra.adapter.persistence.repository;

import br.com.petfriends.almoxarifado.infra.adapter.persistence.entity.AlmoxarifadoEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AlmoxarifadoMongoRepository extends MongoRepository<AlmoxarifadoEntity, UUID> {
    @Query("""
{
  "$expr": {
    "$gt": [
      {
        "$size": {
          "$filter": {
            "input": "$estoques",
            "as": "e",
            "cond": {
              "$and": [
                { "$eq": ["$$e.itemId", ?0] },
                {
                  "$gte": [
                    { "$subtract": ["$$e.quantidadeDisponivel", "$$e.quantidadeReservada"] },
                    ?1
                  ]
                }
              ]
            }
          }
        }
      },
      0
    ]
  }
}
""")
    Optional<AlmoxarifadoEntity> findFirstByItemDisponivel(String itemId, int quantidadePedida);

    @Query("""
{
  "estoques": {
    "$elemMatch": {
      "reservas": {
        "$elemMatch": {
          "pedidoId": ?0
        }
      }
    }
  }
}
""")
    List<AlmoxarifadoEntity> findByPedidoIdInReservas(String pedidoId);
}
