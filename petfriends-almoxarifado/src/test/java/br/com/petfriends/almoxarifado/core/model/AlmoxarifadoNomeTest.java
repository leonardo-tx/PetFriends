package br.com.petfriends.almoxarifado.core.model;

import br.com.petfriends.almoxarifado.core.exception.AlmoxarifadoNomeNuloException;
import br.com.petfriends.almoxarifado.core.exception.AlmoxarifadoNomeTamanhoInvalidoException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class AlmoxarifadoNomeTest {
    @ParameterizedTest
    @ValueSource(strings = {"Almoxarifado XYZ", "Nome Completamente Valido", "Teste 1234", "ABCDE", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"})
    void deveCriarAlmoxarifadoNomeValido(String valor) {
        AlmoxarifadoNome nome = new AlmoxarifadoNome(valor);
        assertEquals(valor, nome.valor());
    }

    @Test
    void deveLancarExcecaoQuandoNomeNulo() {
        assertThrows(AlmoxarifadoNomeNuloException.class, () -> new AlmoxarifadoNome(null));
    }

    @ParameterizedTest
    @ValueSource(strings = {"A", "Abc", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"})
    void deveLancarExcecaoQuandoTamanhoInvalido(String valorInvalido) {
        assertThrows(AlmoxarifadoNomeTamanhoInvalidoException.class, () -> new AlmoxarifadoNome(valorInvalido));
    }
}
