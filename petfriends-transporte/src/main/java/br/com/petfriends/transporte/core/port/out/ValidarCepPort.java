package br.com.petfriends.transporte.core.port.out;

import br.com.petfriends.transporte.core.model.CEP;
import reactor.core.publisher.Mono;

public interface ValidarCepPort {
    Mono<Boolean> cepExiste(CEP cep);
}
