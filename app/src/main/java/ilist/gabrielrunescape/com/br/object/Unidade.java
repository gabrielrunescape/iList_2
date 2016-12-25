package ilist.gabrielrunescape.com.br.object;

import java.io.Serializable;

/**
 * Serializa o objeto do tipo Unidade com suas propriedades.
 *
 * @author Gabriel Filipe
 * @version 1.0
 * @since 2016-12-25
 */
public class Unidade implements Serializable {
    private int ID;
    private String descrição;
    private String abreviação;

    /**
     * Construtor simples sem paramêtros.
     */
    public Unidade() {

    }

    /**
     * Construtor com paramêtros.
     *
     * @param descrição Descrição da Unidade.
     * @param abreviação Abreviação da Unidade.
     */
    public Unidade(String descrição, String abreviação) {
        this.descrição = descrição;
        this.abreviação = abreviação;
    }

    /**
     * Construtor com paramêtros.
     *
     * @param ID Código identificador.
     * @param descrição Descrição da Unidade.
     * @param abreviação Abreviação da Unidade.
     */
    public Unidade(int ID, String descrição, String abreviação) {
        this.ID = ID;
        this.descrição = descrição;
        this.abreviação = abreviação;
    }

    /**
     * @return Abreviação da Unidade.
     */
    public String getAbreviação() {
        return abreviação;
    }

    /**
     * @param abreviação Define uma abreviação para a Unidade.
     */
    public void setAbreviação(String abreviação) {
        this.abreviação = abreviação;
    }

    /**
     * @return Descrição da Unidade.
     */
    public String getDescrição() {
        return descrição;
    }

    /**
     * @param descrição Define uma descrição para a Unidade.
     */
    public void setDescrição(String descrição) {
        this.descrição = descrição;
    }

    /**
     * @return Código identificador da Unidade.
     */
    public int getID() {
        return ID;
    }

    /**
     * @param ID Define código identificador para Unidade.
     */
    public void setID(int ID) {
        this.ID = ID;
    }

    @Override/**
     * Sobreescreve a função para retornar uma String do Objeto.
     *
     * @return String do objeto no formato JSON.
     */
    public String toString() {
        String _return = "Unidade {\n\tID: %d,\n\tDescricao: %s,\n\tAbreviacao: %s\n}";

        return String.format(_return, ID, descrição, abreviação);
    }
}
