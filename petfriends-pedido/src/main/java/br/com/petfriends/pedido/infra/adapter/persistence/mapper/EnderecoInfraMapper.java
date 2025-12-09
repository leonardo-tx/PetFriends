package br.com.petfriends.pedido.infra.adapter.persistence.mapper;

import br.com.petfriends.pedido.core.model.CEP;
import br.com.petfriends.pedido.core.model.Endereco;
import br.com.petfriends.pedido.infra.adapter.persistence.entity.EnderecoEntity;
import org.springframework.stereotype.Component;

@Component
public class EnderecoInfraMapper {
    public Endereco toModel(EnderecoEntity entity) {
        return new Endereco(
                entity.rua(),
                entity.numero(),
                entity.complemento(),
                entity.bairro(),
                entity.cidade(),
                entity.estado(),
                new CEP(entity.cep())
        );
    }

    public EnderecoEntity toEntity(Endereco model) {
        return new EnderecoEntity(
                model.rua(),
                model.numero(),
                model.complemento(),
                model.bairro(),
                model.cidade(),
                model.estado(),
                model.cep().valor()
        );
    }
}
