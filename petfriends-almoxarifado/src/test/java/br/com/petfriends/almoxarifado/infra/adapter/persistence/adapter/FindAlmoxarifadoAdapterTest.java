package br.com.petfriends.almoxarifado.infra.adapter.persistence.adapter;

import br.com.petfriends.almoxarifado.core.model.Almoxarifado;
import br.com.petfriends.almoxarifado.infra.adapter.persistence.entity.AlmoxarifadoEntity;
import br.com.petfriends.almoxarifado.infra.adapter.persistence.entity.ItemEstoqueEntity;
import br.com.petfriends.almoxarifado.infra.adapter.persistence.entity.ReservaEntity;
import br.com.petfriends.almoxarifado.infra.adapter.persistence.mapper.AlmoxarifadoInfraMapper;
import br.com.petfriends.almoxarifado.infra.adapter.persistence.repository.AlmoxarifadoMongoRepository;
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
class FindAlmoxarifadoAdapterTest {

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
    private AlmoxarifadoMongoRepository almoxarifadoMongoRepository;

    @Autowired
    private AlmoxarifadoInfraMapper almoxarifadoInfraMapper;

    private FindAlmoxarifadoAdapter findAlmoxarifadoAdapter;

    @BeforeEach
    void setup() {
        findAlmoxarifadoAdapter = new FindAlmoxarifadoAdapter(almoxarifadoMongoRepository, almoxarifadoInfraMapper);
        almoxarifadoMongoRepository.deleteAll();
    }

    @Test
    void deveRetornarAlmoxarifadoPeloId() {
        UUID id = UUID.randomUUID();
        AlmoxarifadoEntity entity = new AlmoxarifadoEntity(
                id,
                "Almoxarifado Teste",
                List.of()
        );

        almoxarifadoMongoRepository.save(entity);

        Optional<Almoxarifado> result = findAlmoxarifadoAdapter.findById(id);

        assertTrue(result.isPresent());
        assertEquals(entity.id(), result.get().getId());
        assertEquals(entity.nome(), result.get().getNome().valor());
    }

    @Test
    void deveRetornarPrimeiroAlmoxarifadoComItemDisponivel() {
        AlmoxarifadoEntity entity1 = new AlmoxarifadoEntity(
                UUID.randomUUID(),
                "Almox 1",
                List.of(new ItemEstoqueEntity("item-1", 5, 0, List.of()))
        );

        AlmoxarifadoEntity entity2 = new AlmoxarifadoEntity(
                UUID.randomUUID(),
                "Almox 2",
                List.of(new ItemEstoqueEntity("item-1", 10, 0, List.of()))
        );

        almoxarifadoMongoRepository.saveAll(List.of(entity1, entity2));

        Optional<Almoxarifado> result = findAlmoxarifadoAdapter.findFirstByItemDisponivel("item-1", 6);

        assertTrue(result.isPresent());
        assertEquals(entity2.id(), result.get().getId());
    }

    @Test
    void deveRetornarListaDeAlmoxarifadosPorPedidoIdNasReservas() {
        String pedidoId = "pedido-123";

        AlmoxarifadoEntity entity1 = new AlmoxarifadoEntity(
                UUID.randomUUID(),
                "Almox 1",
                List.of(new ItemEstoqueEntity("item-1", 5, 0, List.of(new ReservaEntity(pedidoId, 5))))
        );

        AlmoxarifadoEntity entity2 = new AlmoxarifadoEntity(
                UUID.randomUUID(),
                "Almox 2",
                List.of(new ItemEstoqueEntity("item-2", 3, 0, List.of(new ReservaEntity(pedidoId, 3))))
        );

        almoxarifadoMongoRepository.saveAll(List.of(entity1, entity2));

        List<Almoxarifado> result = findAlmoxarifadoAdapter.findByPedidoIdInReservas(pedidoId);

        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(a -> a.getId().equals(entity1.id())));
        assertTrue(result.stream().anyMatch(a -> a.getId().equals(entity2.id())));
    }
}
