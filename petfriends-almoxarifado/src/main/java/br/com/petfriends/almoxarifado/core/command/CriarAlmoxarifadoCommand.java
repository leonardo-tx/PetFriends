package br.com.petfriends.almoxarifado.core.command;

public final class CriarAlmoxarifadoCommand extends BaseCommand {
    private final String nome;

    public CriarAlmoxarifadoCommand(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }
}
