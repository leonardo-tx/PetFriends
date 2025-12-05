package br.com.petfriends.pedido.infra.adapter.persistence.mapper;

import br.com.petfriends.pedido.core.model.CEP;
import br.com.petfriends.pedido.core.model.Endereco;
import br.com.petfriends.pedido.infra.adapter.persistence.entity.EnderecoEntity;
import org.springframework.stereotype.Component;

@Component
public class EnderecoInfraMapper {
    public Endereco toModel(EnderecoEntity entity) {
        return Endereco.valueOf(
                entity.rua(),
                entity.numero(),
                entity.complemento(),
                entity.bairro(),
                entity.cidade(),
                entity.estado(),
                CEP.valueOf(entity.cep())
        );
    }

    public EnderecoEntity toEntity(Endereco model) {
        return new EnderecoEntity(
                model.getRua(),
                model.getNumero(),
                model.getComplemento(),
                model.getBairro(),
                model.getCidade(),
                model.getEstado(),
                model.getCep().getValor()
        );
    }
}
