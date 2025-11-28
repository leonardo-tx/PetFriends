package br.com.petfriends.transporte.core.model;

import br.com.petfriends.transporte.core.exception.EnderecoCampoNuloException;
import br.com.petfriends.transporte.core.exception.EnderecoCampoTamanhoInvalidoException;

import java.util.Objects;

public final class Endereco {
    public static final int TAMANHO_MINIMO = 1;
    public static final int TAMANHO_MAXIMO = 255;

    private final String rua;
    private final String numero;
    private final String complemento;
    private final String bairro;
    private final String cidade;
    private final String estado;
    private final CEP cep;

    private Endereco(
            String rua,
            String numero,
            String complemento,
            String bairro,
            String cidade,
            String estado,
            CEP cep
    ) {
        validarCampo("rua", rua);
        validarCampo("numero", numero);
        if (complemento != null) {
            validarCampo("complemento", complemento);
        }
        validarCampo("bairro", bairro);
        validarCampo("cidade", cidade);
        validarCampo("estado", estado);

        this.rua = rua;
        this.numero = complemento;
        this.complemento = complemento;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
        this.cep = Objects.requireNonNull(cep);;
    }

    public String getRua() {
        return rua;
    }

    public String getNumero() {
        return numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public String getEstado() {
        return estado;
    }

    public CEP getCep() {
        return cep;
    }

    public static Endereco valueOf(
            String rua,
            String numero,
            String complemento,
            String bairro,
            String cidade,
            String estado,
            CEP cep
    ) {
        return new Endereco(rua, numero, complemento, bairro, cidade, estado, cep);
    }

    private static void validarCampo(String fieldName, String value) {
        if (value == null) {
            throw new EnderecoCampoNuloException(fieldName);
        }
        int tamanho = value.length();
        if (tamanho < TAMANHO_MINIMO || tamanho > TAMANHO_MAXIMO) {
            throw new EnderecoCampoTamanhoInvalidoException(fieldName);
        }
    }
}