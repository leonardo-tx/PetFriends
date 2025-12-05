package br.com.petfriends.pedido.infra.adapter.persistence.adapter;

import br.com.petfriends.pedido.core.model.Pedido;
import br.com.petfriends.pedido.core.model.PedidoStatus;
import br.com.petfriends.pedido.core.query.BuscarPedidoPeloIdQuery;
import br.com.petfriends.pedido.infra.adapter.persistence.entity.ItemPedidoEntity;
import br.com.petfriends.pedido.infra.adapter.persistence.entity.PedidoEntity;
import br.com.petfriends.pedido.infra.adapter.persistence.mapper.PedidoInfraMapper;
import br.com.petfriends.pedido.infra.adapter.persistence.repository.PedidoMongoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Testcontainers
@SpringBootTest
class FindPedidoPortTest {
    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:7.0.0");

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("MONGODB_DATABASE", () -> "testdb");
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @Autowired
    private PedidoMongoRepository pedidoMongoRepository;

    @Autowired
    private PedidoInfraMapper pedidoInfraMapper;

    private FindPedidoAdapter findPedidoAdapter;

    @BeforeEach
    void setup() {
        findPedidoAdapter = new FindPedidoAdapter(pedidoMongoRepository, pedidoInfraMapper);
        pedidoMongoRepository.deleteAll(); // Limpa o banco antes de cada teste
    }

    @Test
    void shouldFindPedidoById() {
        PedidoEntity pedidoEntity = new PedidoEntity(
                UUID.randomUUID(),
                "cliente-id",
                PedidoStatus.CRIADO,
                List.of(
                        new ItemPedidoEntity("produto-1", new BigDecimal("10.50"), 2)
                )
        );
        pedidoMongoRepository.save(pedidoEntity);

        Optional<Pedido> result = findPedidoAdapter.findById(pedidoEntity.id());

        assertTrue(result.isPresent());
        assertEquals(pedidoEntity.id(), result.get().getId());
        assertEquals(pedidoEntity.clienteId(), result.get().getClienteId());
        assertEquals(pedidoEntity.status(), result.get().getStatus());
        assertEquals(1, result.get().getItens().size());
        assertEquals(pedidoEntity.itens().get(0).produtoId(), result.get().getItens().get(0).getProdutoId());
        assertEquals(pedidoEntity.itens().get(0).valorUnitario(), result.get().getItens().get(0).getValorUnitario().getValor());
        assertEquals(pedidoEntity.itens().get(0).quantidade(), result.get().getItens().get(0).getQuantidade());
    }
}
