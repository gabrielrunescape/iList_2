package ilist.gabrielrunescape.com.br.adapter;

import android.view.View;
import android.widget.TextView;
import ilist.gabrielrunescape.com.br.R;
import android.support.v7.widget.RecyclerView;

/**
 * Carrega os itens básicos para criar os ItemView da RecycleView.
 *
 * @author Gabriel Filipe
 * @version 1.0
 * @since 2017-01-01
 */
public class ReportHolder extends RecyclerView.ViewHolder {
    public TextView texto, total;

    /**
     * Método construtor da classe.
     *
     * @param v Activity da RecycleView.
     */
    public ReportHolder(View v) {
        super(v);

        total = (TextView) v.findViewById(R.id.txt_preco);
        texto = (TextView) v.findViewById(R.id.txt_descricao);
    }
}
