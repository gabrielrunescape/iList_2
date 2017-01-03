package ilist.gabrielrunescape.com.br.object;

import android.text.Html;
import android.text.Spanned;
import java.io.Serializable;

/**
 * Serializa o objeto do tipo Report com suas propriedades.
 *
 * @author Gabriel Filipe
 * @version 1.0
 * @since 2017-01-01
 */
public class Report implements Serializable {
    private int comprado;
    private int acomprar;
    private double total;
    private String texto;

    /**
     * Construtor do objeto sem paramêtros.
     */
    public Report() {

    }

    /**
     * Construtor do objeto com paramêtros.
     *
     * @param acomprar Quantidade a ser comrpada.
     * @param comprado Quantidade comprada.
     * @param texto Texto para descrição.
     * @param total Valor total.
     */
    public Report(int acomprar, int comprado, String texto, double total) {
        this.acomprar = acomprar;
        this.comprado = comprado;
        this.texto = texto;
        this.total = total;
    }

    /**
     * @return Quantidade a ser comprada.
     */
    public int getAcomprar() {
        return acomprar;
    }

    /**
     * @param acomprar Define a quantidade a ser comprada.
     */
    public void setAcomprar(int acomprar) {
        this.acomprar = acomprar;
    }

    /**
     * @return Quantidade comprada.
     */
    public int getComprado() {
        return comprado;
    }

    /**
     * @param comprado Define a quantidade a ser comprada.
     */
    public void setComprado(int comprado) {
        this.comprado = comprado;
    }

    /**
     * @return Texto para descrição.
     */
    public String getTexto() {
        return texto;
    }

    /**
     * @param texto Define texto para descrição.
     */
    public void setTexto(String texto) {
        this.texto = texto;
    }

    /**
     * @return Valor total.
     */
    public double getTotal() {
        return total;
    }

    /**
     * @param total Define valor total.
     */
    public void setTotal(double total) {
        this.total = total;
    }

    public Spanned textFraction() {
        String format = "<sup>%d</sup>/<sub>%d</sub>";

        return Html.fromHtml(String.format(format, comprado, acomprar));
    }
}
