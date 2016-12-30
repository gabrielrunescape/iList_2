package ilist.gabrielrunescape.com.br.object;

import java.io.Serializable;

/**
 * Serializa o objeto do tipo Orçamento com suas propriedades.
 *
 * @author Gabriel Filipe
 * @version 1.0
 * @since 2016-12-25
 */
public class Orcamento implements Serializable {
    private int ID;
    private Item item;
    private double preço;
    private int quantidade;
    private String observação;
    private String estabelecimento;

    /**
     * @return Obtem o estabelecimento do Orçamento.
     */
    public String getEstabelecimento() {
        return estabelecimento;
    }

    /**
     * @param estabelecimento Define o estabelecimento do Orçamento.
     */
    public void setEstabelecimento(String estabelecimento) {
        this.estabelecimento = estabelecimento;
    }

    /**
     * @return Código identificador do Orçamento.
     */
    public int getID() {
        return ID;
    }

    /**
     * @param ID Define o código identificador do Orçamento.
     */
    public void setID(int ID) {
        this.ID = ID;
    }

    /**
     * @return O item do orçamento.
     */
    public Item getItem() {
        return item;
    }

    /**
     * @param item Define o Item.
     */
    public void setItem(Item item) {
        this.item = item;
    }

    /**
     * @return Obtem observação do Orçamento.
     */
    public String getObservação() {
        return observação;
    }

    /**
     * @param observação Define uma observação para o Orçamento.
     */
    public void setObservação(String observação) {
        this.observação = observação;
    }

    /**
     * @return Quantidade de Itens do Orçamento.
     */
    public int getQuantidade() {
        return quantidade;
    }

    /**
     * @param quantidade Define a quantidade de produtos para Orçamento.
     */
    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    /**
     * @return Obtem o preço do Orçamento.
     */
    public double getPreço() {
        return preço;
    }

    /**
     * @param preço Define o preço do Orçamento.
     */
    public void setPreço(double preço) {
        this.preço = preço;
    }

    @Override
    /**
     * Sobreescreve a função para retornar uma String do Objeto.
     *
     * @return String do objeto no formato JSON.
     */
    public String toString() {
        String _return = "Orçamento {\n\tID: %d,\n\tEstabelecimento: %s,\n\tQuantidade: %d,\n\tPreço: %f,\n\tObservação: %s,\n\tItem: %d\n}";

        return String.format(_return, ID, estabelecimento, quantidade, preço, observação, item.getID());
    }
}
