package ilist.gabrielrunescape.com.br.adapter;

import android.util.Log;
import android.view.View;
import android.widget.Toast;
import android.widget.TextView;
import ilist.gabrielrunescape.com.br.R;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;

/**
 * Carrega os itens básicos para criar os ItemView da RecycleView.
 *
 * @author Gabriel Filipe
 * @version 1.0
 * @since 2016-12-18
 */
public class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
    public TextView nome, status;
    private static String TAG = ItemHolder.class.getSimpleName();

    /**
     * Método construtor da classe.
     *
     * @param v Activity da RecycleView.
     */
    public ItemHolder(View v) {
        super(v);

        nome = (TextView) v.findViewById(R.id.txt_nome);
        status = (TextView) v.findViewById(R.id.txt_status);

        v.setOnClickListener(this);
        v.setOnLongClickListener(this);
    }

    @Override
    /**
     * Ao clicar no item, realiza uma ação.
     *
     * @param view Item do RecyclerView clicado.
     */
    public void onClick(View v) {
        try {
            int position = getAdapterPosition();

            if (position != RecyclerView.NO_POSITION) {
                Log.i(TAG, String.format("O item nº %1$d foi clicado!", position + 1));
            }
        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage());
        }
    }

    @Override
    /**
     * Ao pressionar o item, exibe um AlertDialog.
     *
     * @param view Item do RecyclerView pressionado.
     * @param position Posição da view (item do RecyclerView).
     */
    public boolean onLongClick(final View view) {
        int position = getAdapterPosition();
        Log.i(TAG, String.format("O item nº %1$d foi pressionado!", position));

        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
            builder.setTitle("Escolha uma ação");

            String[] options = new String[] {"Apagar", "Editar", "Inserir", "Visualizar"};
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
                            Toast.makeText(view.getContext(), "Função não programada!", Toast.LENGTH_LONG).show();
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