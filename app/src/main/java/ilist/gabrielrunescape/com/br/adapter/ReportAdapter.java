package ilist.gabrielrunescape.com.br.adapter;

import java.util.List;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import ilist.gabrielrunescape.com.br.R;
import android.support.v7.widget.RecyclerView;
import ilist.gabrielrunescape.com.br.object.Report;

/**
 * A classe extende um ArrayAdapter no qual é utilizado para inserir informações de um objeto a ele
 * exibindo em uma ListView.
 *
 * @author Gabriel Filipe
 * @version 1.0
 * @since 2016-12-18
 */
public class ReportAdapter extends RecyclerView.Adapter<ReportHolder> {
    private List<Report> lista;
    private static String TAG = ReportAdapter.class.getSimpleName();

    /**
     * Metódo construtor da classe.
     *
     * @param lista ArrayList serializado com Item.
     */
    public ReportAdapter(List<Report> lista) {
        this.lista = lista;
    }

    /**
     * Cria o layout de cada item do RecyclerVeiw.
     *
     * @param parent RecyclerView.
     * @param viewType tipo da view.
     *
     * @return Um adaptador de RecyclerView.
     */
    @Override
    public ReportHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_orcamento, parent, false);

        return new ReportHolder(itemView);
    }

    /**
     * Define os valores de cada Textview dentro do list_item.xml.
     *
     * @param holder Adaptador de RecyclerView.
     * @param position Posição o qual está preenchendo.
     */
    @Override
    public void onBindViewHolder(ReportHolder holder, int position) {
        try {
            Report report = lista.get(position);

            holder.texto.setText(report.getTexto());
            holder.total.setText("R$ " + report.getTotal());
        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage());
        }
    }

    /**
     * @return Número de elementos no RecyclerView.
     */
    @Override
    public int getItemCount() {
        return lista.size();
    }
}
