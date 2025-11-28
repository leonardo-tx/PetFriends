package br.com.petfriends.transporte.infra.adapter.endereco.dto;

public record ProcuraInfoCepDTO(
        String cep,
        String logradouro,
        String complemento,
        String unidade,
        String bairro,
        String localidade,
        String uf,
        String estado,
        String regiao,
        String ibge,
        String gia,
        String ddd,
        String siafi,
        String erro
) {
}
