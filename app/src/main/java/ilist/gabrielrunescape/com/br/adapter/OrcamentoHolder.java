package ilist.gabrielrunescape.com.br.adapter;

import android.util.Log;
import android.view.View;
import android.widget.Toast;
import android.widget.TextView;
import android.content.DialogInterface;
import ilist.gabrielrunescape.com.br.R;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import ilist.gabrielrunescape.com.br.object.Orcamento;

/**
 * Carrega os orcamentos para criar os ItemView da RecycleView.
 *
 * @author Gabriel Filipe
 * @version 0.3
 * @since 2016-12-21
 */
public class OrcamentoHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener  {
    public Orcamento orcamento;
    public ItemAdapter adapter;
    public TextView descricao, preco, quantidade;
    private static String TAG = OrcamentoHolder.class.getSimpleName();

    /**
     * Método construtor da classe.
     *
     * @param v Activity da RecycleView.
     */
    public OrcamentoHolder(View v) {
        super(v);

        preco = (TextView) v.findViewById(R.id.txt_preco);
        descricao = (TextView) v.findViewById(R.id.txt_descricao);
        quantidade = (TextView) v.findViewById(R.id.txt_quantidade);

        v.setOnLongClickListener(this);
    }

    @Override
    /**
     * Ao pressionar o item, exibe um AlertDialog.
     *
     * @param view Item do RecyclerView pressionado.
     */
    public boolean onLongClick(final View view) {
        int position = getAdapterPosition();
        Log.i(TAG, String.format("O item nº %1$d foi pressionado!", position));

        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
            builder.setTitle("Escolha uma ação");

            String[] options = new String[] {"Apagar", "Comprar", "Editar", "Inserir"};
            view.setPressed(true);

            builder.setItems(options, new DialogInterface.OnClickListener() {
                /**
                 * Ao clicar em um dos itens, encaminha para uma Intent especifica da ação.
                 *
                 * @param dialog AlertDialog.Builder utilizado.
                 * @param which  Posição do item clicado.
                 */
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case 0:
                        case 1:
                        case 2:
                        case 3:
                            Toast.makeText(view.getContext(), "Função não programada", Toast.LENGTH_LONG).show();
                            break;
                    }
                }
            });

            return builder.show().isShowing();
        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage());

            return false;
        }
    }
}
