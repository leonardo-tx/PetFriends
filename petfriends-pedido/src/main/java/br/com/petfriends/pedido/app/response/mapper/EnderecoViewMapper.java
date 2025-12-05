package br.com.petfriends.pedido.app.response.mapper;

import br.com.petfriends.pedido.app.response.dto.EnderecoViewDTO;
import br.com.petfriends.pedido.core.model.Endereco;
import org.springframework.stereotype.Component;

@Component
public class EnderecoViewMapper {
    public EnderecoViewDTO toDTO(Endereco endereco) {
        return new EnderecoViewDTO(
                endereco.getRua(),
                endereco.getNumero(),
                endereco.getComplemento(),
                endereco.getBairro(),
                endereco.getCidade(),
                endereco.getEstado(),
                endereco.getCep().getValor()
        );
    }
}
