package ilist.gabrielrunescape.com.br.object;

import java.io.Serializable;

/**
 * Serializa o objeto do tipo Status com suas propriedades.
 *
 * @author Gabriel Filipe
 * @version 1.0
 * @since 2016-12-25
 */
public class Status implements Serializable {
    private int ID;
    private String descrição;

    /**
     * Construtor simples sem paramêtros.
     */
    public Status() {

    }

    /**
     * Construtor com paramêtros.
     *
     * @param ID Código identificador do Status.
     * @param descrição Descrição do Status.
     */
    public Status(int ID, String descrição) {
        this.ID = ID;
        this.descrição = descrição;
    }

    /**
     * @return Descrição do Status.
     */
    public String getDescrição() {
        return descrição;
    }

    /**
     * @param descrição Define uma descrição para o Status.
     */
    public void setDescrição(String descrição) {
        this.descrição = descrição;
    }

    /**
     * @return Código identificador do Status.
     */
    public int getID() {
        return ID;
    }

    /**
     * @param ID Define o código identificador do Status.
     */
    public void setID(int ID) {
        this.ID = ID;
    }

    @Override
    /**
     * Sobreescreve a função para retornar uma String do Objeto.
     *
     * @return String do objeto no formato JSON.
     */
    public String toString() {
        String _return = "Status {\n\tID: %d, \n\tDescricao: %s\n}";

        return String.format(_return, ID, descrição);
    }
}
