package br.com.petfriends.pedido.infra.adapter.endereco.adapter;

import br.com.petfriends.pedido.core.model.CEP;
import br.com.petfriends.pedido.core.port.out.ValidarCepPort;
import br.com.petfriends.pedido.infra.adapter.endereco.dto.ProcuraInfoCepDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Component
public class ValidarCepViaCepAdapter implements ValidarCepPort {
    public static final String BASE_URL = "https://viacep.com.br/ws";
    public static final String PROCURA_INFO_CEP_URL = BASE_URL + "/%s/json";

    private final WebClient webClient;

    public ValidarCepViaCepAdapter(WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public Mono<Boolean> cepExiste(CEP cep) {
        String url = String.format(PROCURA_INFO_CEP_URL, cep.valor());
        return webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(ProcuraInfoCepDTO.class)
                .map(dto -> !Objects.equals(dto.erro(), "true"));
    }
}
