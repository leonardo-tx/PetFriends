package br.com.petfriends.transporte.infra.adapter.persistence.adapter;

import br.com.petfriends.transporte.core.model.Entrega;
import br.com.petfriends.transporte.core.model.EntregaStatus;
import br.com.petfriends.transporte.core.model.Remessa;
import br.com.petfriends.transporte.core.model.RemessaStatus;
import br.com.petfriends.transporte.infra.adapter.persistence.entity.EntregaEntity;
import br.com.petfriends.transporte.infra.adapter.persistence.entity.ItemRemessaEntity;
import br.com.petfriends.transporte.infra.adapter.persistence.entity.RemessaEntity;
import br.com.petfriends.transporte.infra.adapter.persistence.mapper.EntregaInfraMapper;
import br.com.petfriends.transporte.infra.adapter.persistence.repository.EntregaMongoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.kafka.KafkaContainer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Testcontainers
@SpringBootTest
class FindEntregaAdapterTest {

    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:8.2.2-rc0-noble");

    @Container
    static KafkaContainer kafkaContainer = new KafkaContainer("apache/kafka:4.1.1");

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("MONGODB_DATABASE", () -> "testdb");
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);

        registry.add("KAFKA_BOOTSTRAP_SERVERS", kafkaContainer::getBootstrapServers);
    }

    @Autowired
    private EntregaMongoRepository entregaMongoRepository;

    @Autowired
    private EntregaInfraMapper entregaInfraMapper;

    private FindEntregaAdapter findEntregaAdapter;

    @BeforeEach
    void setupBeforeEach() {
        findEntregaAdapter = new FindEntregaAdapter(entregaMongoRepository, entregaInfraMapper);
        entregaMongoRepository.deleteAll();
    }

    @Test
    void deveRetornarEntregaPeloId() {
        RemessaEntity remessaEntity = new RemessaEntity(
                "almox-1",
                RemessaStatus.SEPARADA,
                List.of(new ItemRemessaEntity("item-1", 2))
        );

        EntregaEntity entregaEntity = new EntregaEntity(
                UUID.randomUUID(),
                "pedido-123",
                EntregaStatus.EM_PROCESSAMENTO,
                List.of(remessaEntity)
        );

        entregaMongoRepository.save(entregaEntity);

        Optional<Entrega> result = findEntregaAdapter.findById(entregaEntity.id());

        assertTrue(result.isPresent());
        assertEquals(entregaEntity.id(), result.get().getId());
        assertEquals(entregaEntity.pedidoId(), result.get().getPedidoId());
        assertEquals(entregaEntity.status(), result.get().getStatus());
        assertEquals(1, result.get().getRemessas().size());

        Remessa remessa = result.get().getRemessas().get("almox-1");
        assertEquals(remessaEntity.almoxarifadoId(), remessa.getAlmoxarifadoId());
        assertEquals(remessaEntity.status(), remessa.getStatus());
        assertEquals(1, remessa.getItems().size());
        assertEquals(remessaEntity.items().get(0).itemId(), remessa.getItems().get(0).getItemId());
        assertEquals(remessaEntity.items().get(0).quantidade(), remessa.getItems().get(0).getQuantidade());
    }

    @Test
    void deveRetornarEntregaPeloPedidoId() {
        RemessaEntity remessaEntity = new RemessaEntity(
                "almox-1",
                RemessaStatus.SEPARADA,
                List.of(new ItemRemessaEntity("item-1", 2))
        );

        EntregaEntity entregaEntity = new EntregaEntity(
                UUID.randomUUID(),
                "pedido-456",
                EntregaStatus.EM_PROCESSAMENTO,
                List.of(remessaEntity)
        );

        entregaMongoRepository.save(entregaEntity);

        Optional<Entrega> result = findEntregaAdapter.findByPedidoId("pedido-456");

        assertTrue(result.isPresent());
        assertEquals(entregaEntity.id(), result.get().getId());
        assertEquals(entregaEntity.pedidoId(), result.get().getPedidoId());
    }
}
