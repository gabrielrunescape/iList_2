package ilist.gabrielrunescape.com.br.adapter;

import java.util.List;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import ilist.gabrielrunescape.com.br.R;
import android.support.v7.widget.RecyclerView;
import ilist.gabrielrunescape.com.br.object.Item;

/**
 * A classe extende um ArrayAdapter no qual é utilizado para inserir informações de um objeto a ele
 * exibindo em uma ListView.
 *
 * @author Gabriel Filipe
 * @version 1.0
 * @since 2016-12-18
 */
public class ItemAdapter extends RecyclerView.Adapter<ItemHolder> {
    private List<Item> listItens;
    private static String TAG = ItemAdapter.class.getSimpleName();

    /**
     * Metódo construtor da classe.
     *
     * @param lista ArrayList serializado com Item.
     */
    public ItemAdapter(List<Item> lista) {
        listItens = lista;
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
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);

        return new ItemHolder(itemView);
    }

    /**
     * Define os valores de cada Textview dentro do list_item.xml.
     *
     * @param holder Adaptador de RecyclerView.
     * @param position Posição o qual está preenchendo.
     */
    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {
        try {
            Item item = listItens.get(position);

            holder.item = item;
            holder.nome.setText(item.getQuantidade() + " x " + item.getNome());
            holder.status.setText(item.getStatus().getDescrição());

            switch (holder.status.getText().toString()) {
                case "À comprar":
                    holder.status.setTextColor(holder.status.getResources().getColor(R.color.colourDanger));
                    break;
                case "Orçamento feito":
                    holder.status.setTextColor(holder.status.getResources().getColor(R.color.colourWarning));
                    break;
                case "Comprado":
                default:
                    holder.status.setTextColor(holder.status.getResources().getColor(R.color.colourSuccess));
                    break;
            }
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
}
