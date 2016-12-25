package ilist.gabrielrunescape.com.br.object;

import java.io.Serializable;

/**
 * Serializa o objeto do tipo Item com suas propriedades.
 *
 * @author Gabriel Filipe
 * @version 1.0
 * @since 2016-12-25
 */
public class Item implements Serializable {
    private int ID;
    private String nome;
    private Status status;
    private int quantidade;
    private Unidade unidade;

    /**
     * Construtor simples sem paramêtros.
     */
    public Item() {

    }

    /**
     * Construtor com paramêtros.
     *
     * @param ID Código identificador.
     * @param nome Nome do Item.
     * @param status Status do Item
     * @param quantidade Quantidade.
     * @param unidade Unidade do Item.
     */
    public Item(int ID, String nome, Status status, int quantidade, Unidade unidade) {
        this.ID = ID;
        this.nome = nome;
        this.status = status;
        this.unidade = unidade;
        this.quantidade = quantidade;
    }

    /**
     * @return Código identificador do Item.
     */
    public int getID() {
        return ID;
    }

    /**
     * @param ID Define código identificador do Item.
     */
    public void setID(int ID) {
        this.ID = ID;
    }

    /**
     * @return Nome do Item.
     */
    public String getNome() {
        return nome;
    }

    /**
     * @param nome Define nome para o Item.
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * @return Quantidade do Item.
     */
    public int getQuantidade() {
        return quantidade;
    }

    /**
     * @param quantidade Define a quantidade do Item.
     */
    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    /**
     * @return Objeto Status.
     */
    public Status getStatus() {
        return status;
    }

    /**
     * @param status Define um Status (objeto) para o Item.
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * @return Objeto Unidade.
     */
    public Unidade getUnidade() {
        return unidade;
    }

    /**
     * @param unidade Define uma Unidade (objeto) para o Item.
     */
    public void setUnidade(Unidade unidade) {
        this.unidade = unidade;
    }

    @Override
    /**
     * Sobreescreve a função para retornar uma String do Objeto.
     *
     * @return String do objeto no formato JSON.
     */
    public String toString() {
        String _return = "Item {\n\tID: %d,\n\tNome: %s,\n\tStatus: %s,\n\tQuantidade: %d,\n\tUnidade: %s\n}";

        return String.format(_return, ID, nome, status.toString().replace("Status ", ""), quantidade, unidade.toString().replace("Unidade ", ""));
    }
}
