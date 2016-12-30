package ilist.gabrielrunescape.com.br.adapter;

import android.util.Log;
import android.view.View;
import android.content.Intent;
import android.widget.TextView;
import android.app.DialogFragment;
import android.app.FragmentManager;
import ilist.gabrielrunescape.com.br.R;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import com.cocosw.bottomsheet.BottomSheet;
import android.support.v7.widget.RecyclerView;
import android.support.design.widget.Snackbar;
import ilist.gabrielrunescape.com.br.dao.ItemDAO;
import ilist.gabrielrunescape.com.br.object.Item;
import ilist.gabrielrunescape.com.br.view.ItemActivity;
import ilist.gabrielrunescape.com.br.model.DialogOrcamento;

/**
 * Carrega os itens básicos para criar os ItemView da RecycleView.
 *
 * @author Gabriel Filipe
 * @version 1.0
 * @since 2016-12-18
 */
public class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
    public Item item;
    public ItemAdapter adapter;
    public TextView nome, status;
    public FragmentManager support;
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
     * @param v Item do RecyclerView clicado.
     */
    public void onClick(View v) {
        try {
            int position = getAdapterPosition();

            if (position != RecyclerView.NO_POSITION) {
                Log.i(TAG, String.format("O item nº %1$d foi clicado!", position + 1));

                Intent intent = new Intent(v.getContext(), ItemActivity.class);
                intent.putExtra("Item", item);

                v.getContext().startActivity(intent);
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
     */
    public boolean onLongClick(final View view) {
        return new BottomSheet.Builder(view.getContext(), R.style.BottomSheet_Dialog).sheet(R.menu.menu_bottom_sheet) .listener(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(view.getContext(), ItemActivity.class);

                switch (which) {
                    case R.id.btm_buy:
                        DialogFragment dFragment = new DialogOrcamento().newInstance("Nova compra", item);
                        dFragment.show(support, "ItemActivity");

                        break;
                    case R.id.btm_edit:
                        intent.putExtra("Item", item);

                        view.getContext().startActivity(intent);
                    case R.id.btm_insert:
                        view.getContext().startActivity(intent);
                        break;
                    case R.id.btm_delete:
                        confirmDelete(view);
                        break;
                    default:
                        Log.i(TAG, "Função não programada!");
                        break;
                }
            }
        }).show().isShowing();
    }

    /**
     * Aparece um AlertDialog confirmando a exclusão do Item.
     *
     * @param view ItemView do RecyclerView.
     */
    protected void confirmDelete(final View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setMessage("Deseja apagar " + item.getNome().toLowerCase() + "?");

        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            /**
             * Ao clicar na opção positiva realiza o procedimento de exclusão.
             *
             * @param dialog AlertDialog em execução.
             * @param which Botão escolhido.
             */
            public void onClick(DialogInterface dialog, int which) {
                ItemDAO dao = new ItemDAO(view.getContext());

                dao.open(true);
                dao.delete(item.getID());
                adapter.removeItem(item);
                dao.closeDatabase();

                Snackbar.make(view, item.getNome() + " apagado com sucesso!", Snackbar.LENGTH_SHORT).show();
            }
        }).setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            /**
             * Ao clicar na opção negativa, realiza algum procedimento.
             *
             * @param dialog AlertDialog em execução.
             * @param which Botão escolhido.
             */
            public void onClick(DialogInterface dialog, int which) {
                Log.i(TAG, "Botão não clicado.");
            }
        });

        builder.create().show();
    }
}
