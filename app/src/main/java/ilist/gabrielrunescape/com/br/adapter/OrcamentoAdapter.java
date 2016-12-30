package ilist.gabrielrunescape.com.br.adapter;

import java.util.List;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import ilist.gabrielrunescape.com.br.R;
import android.support.v7.widget.RecyclerView;
import ilist.gabrielrunescape.com.br.object.Orcamento;

/**
 * A classe extende um ArrayAdapter no qual é utilizado para inserir informações de um objeto a ele
 * exibindo em uma ListView.
 *
 * @author Gabriel
 * @version 1.0
 * @since 2016-12-21
 */
public class OrcamentoAdapter extends RecyclerView.Adapter<OrcamentoHolder> {
    private List<Orcamento> listItens;
    private static String TAG = ItemAdapter.class.getSimpleName();
    /**
     * Metódo construtor da classe.
     *
     * @param lista ArrayList serializado com Item.
     */
    public OrcamentoAdapter(List<Orcamento> lista) {
        listItens = lista;
    }

    /**
     * Sobreescreve o método onCreateViewHolder().
     * Cria o layout de cada item do RecyclerVeiw.
     *
     * @param parent RecyclerView.
     * @param viewType tipo da view.
     *
     * @return Um adaptador de RecyclerView.
     */
    @Override
    public OrcamentoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_orcamento, parent, false);

        return new OrcamentoHolder(itemView);
    }

    /**
     * Sobreescreve o método onBindViewHolder().
     * Define os valores de cada Textview dentro do list_item.xml.
     *
     * @param holder Adaptador de RecyclerView.
     * @param position Posição o qual está preenchendo.
     */
    @Override
    public void onBindViewHolder(OrcamentoHolder holder, int position) {
        try {
            Orcamento orcamento = listItens.get(position);

            holder.preco.setTextColor(holder.preco.getResources().getColor(R.color.colourSuccess));
            holder.quantidade.setTextColor(holder.quantidade.getResources().getColor(R.color.colourSuccess));

            holder.preco.setText("R$ " + orcamento.getPreço());
            holder.descricao.setText(orcamento.getEstabelecimento());
            holder.quantidade.setText(orcamento.getQuantidade() + "");
        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage());
        }
    }

    /**
     * @return Número de elementos no RecyclerView.
     */
    @Override
    public int getItemCount() {
        return listItens.size();
    }


    /**
     * Adiciona todos os objetos Item em uma lista.
     *
     * @param list Lista com os objetos do tipo Item.
     */
    public void addAll(List<Orcamento> list) {
        listItens.addAll(list);

        notifyDataSetChanged();
    }

    /**
     * Limpa todos os valores da lista de itens do recyclerView.
     */
    public void clear() {
        listItens.clear();

        notifyDataSetChanged();
    }

    /**
     * Remove um orçamento de uma determinada posição.
     *
     * @param orcamento Orçamento a ser removido.
     */
    public void removeItem(Orcamento orcamento) {
        int position = listItens.indexOf(orcamento);

        listItens.remove(position);
        notifyItemRemoved(position);
    }
}
