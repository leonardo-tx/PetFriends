package br.com.petfriends.pedido.core.port.out;

import br.com.petfriends.pedido.core.model.CEP;
import reactor.core.publisher.Mono;

public interface ValidarCepPort {
    Mono<Boolean> cepExiste(CEP cep);
}
