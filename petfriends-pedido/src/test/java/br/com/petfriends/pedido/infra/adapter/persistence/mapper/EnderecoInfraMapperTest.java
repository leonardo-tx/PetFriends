package br.com.petfriends.pedido.infra.adapter.persistence.mapper;

import br.com.petfriends.pedido.core.model.CEP;
import br.com.petfriends.pedido.core.model.Endereco;
import br.com.petfriends.pedido.infra.adapter.persistence.entity.EnderecoEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class EnderecoInfraMapperTest {
    @InjectMocks
    private EnderecoInfraMapper mapper;

    @Test
    void deveMapearParaModel() {
        EnderecoEntity entity = new EnderecoEntity(
                "Rua A",
                "123",
                "Apto 1",
                "Bairro B",
                "Cidade C",
                "Estado D",
                "12345678"
        );

        Endereco model = mapper.toModel(entity);

        assertEquals(entity.rua(), model.rua());
        assertEquals(entity.numero(), model.numero());
        assertEquals(entity.complemento(), model.complemento());
        assertEquals(entity.bairro(), model.bairro());
        assertEquals(entity.cidade(), model.cidade());
        assertEquals(entity.estado(), model.estado());
        assertEquals(entity.cep(), model.cep().valor());
    }

    @Test
    void deveMapearParaEntity() {
        Endereco model = new Endereco(
                "Rua A",
                "123",
                "Apto 1",
                "Bairro B",
                "Cidade C",
                "Estado D",
                new CEP("12345678")
        );

        EnderecoEntity entity = mapper.toEntity(model);

        assertEquals(model.rua(), entity.rua());
        assertEquals(model.numero(), entity.numero());
        assertEquals(model.complemento(), entity.complemento());
        assertEquals(model.bairro(), entity.bairro());
        assertEquals(model.cidade(), entity.cidade());
        assertEquals(model.estado(), entity.estado());
        assertEquals(model.cep().valor(), entity.cep());
    }
}

