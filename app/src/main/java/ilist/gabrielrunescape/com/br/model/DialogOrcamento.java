package ilist.gabrielrunescape.com.br.model;

import android.util.Log;
import android.os.Bundle;
import android.view.View;
import android.app.Dialog;
import android.widget.Toast;
import android.app.Activity;
import android.text.Editable;
import java.text.NumberFormat;
import android.widget.TextView;
import android.app.AlertDialog;
import android.widget.EditText;
import android.text.TextWatcher;
import android.app.DialogFragment;
import android.view.LayoutInflater;
import android.content.DialogInterface;
import ilist.gabrielrunescape.com.br.R;
import ilist.gabrielrunescape.com.br.object.Item;
import ilist.gabrielrunescape.com.br.dao.OrcamentoDAO;
import ilist.gabrielrunescape.com.br.object.Orcamento;

/**
 * Essa classe constroi e exibe o layout dialog_orcamento.
 *
 * @author Gabriel Filipe
 * @version 1.0
 * @since 2016-12-22
 */
public class DialogOrcamento extends DialogFragment implements TextWatcher {
    public int ID = 0;
    private Activity activity;
    private EditText et_preco;
    private TextView txt_total;
    private EditText et_observacao;
    private EditText et_quantidade;
    private EditText et_estabelecimento;
    private static String TAG = DialogOrcamento.class.getSimpleName();

    /**
     * Cria uma nova instância de um DialogFragment.
     *
     * @param title Título do DialogFragment.
     * @param item Item a ser serializado.
     *
     * @return DialogFramgment.
     */
    public static DialogOrcamento newInstance(String title, Item item) {
        DialogOrcamento fragment = new DialogOrcamento();

        Bundle args = new Bundle();
        args.putString("title", title);
        args.putSerializable("item", item);

        fragment.setArguments(args);

        return fragment;
    }

    @Override
    /**
     * TextWatcher depois da mudança de texto.
     *
     * @param s Obtenção do EditText.
     */
    public void afterTextChanged(Editable s) {

    }

    /**
     * TextWatcher antes de ser mudado o texto.
     *
     * @param s String do EditText.
     * @param start Posição no qual foi modificada.
     * @param count Caracteres modificados.
     * @param after Posição da mudança.
     */
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    /**
     * Cria um AlertDialog com um determinado layout.
     *
     * @param savedInstanceState Salva os dados da instância.
     * @return AlertDialog criado.
     */
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);

        try {
            activity = getActivity();
            LayoutInflater inflater = getActivity().getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.dialog_orcamento, null, false);

            et_preco = (EditText) dialogView.findViewById(R.id.et_preco);
            txt_total = (TextView) dialogView.findViewById(R.id.txt_total);
            et_observacao = (EditText) dialogView.findViewById(R.id.et_observacao);
            et_quantidade = (EditText) dialogView.findViewById(R.id.et_quantidade);
            et_estabelecimento = (EditText) dialogView.findViewById(R.id.et_estabelecimento);

            AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
            alert.setView(dialogView);

            if (!getArguments().getString("title").isEmpty()) {
                alert.setTitle(getArguments().getString("title"));
            }

            alert.setPositiveButton("Salvar", new DialogInterface.OnClickListener() {
                @Override
                /**
                 * Ao clicar na view, realiza uma ação.
                 *
                 * @param dialog AlertDialog em execução.
                 * @param which Escolha feita.
                 */
                public void onClick(DialogInterface dialog, int which) {
                    doPositiveClick();
                }
            });

            et_preco.addTextChangedListener(this);
            et_quantidade.addTextChangedListener(this);

            if (ID != 0) {
                alert.setNeutralButton("Apagar", new DialogInterface.OnClickListener() {
                    @Override
                    /**
                     * Ao clicar na view, realiza uma ação.
                     *
                     * @param dialog AlertDialog em execução.
                     * @param which Escolha feita.
                     */
                    public void onClick(DialogInterface dialog, int which) {
                        OrcamentoDAO dao = new OrcamentoDAO(getActivity());
                        dao.open(true);

                        dao.delete(ID);
                        dao.closeDatabase();
                    }
                });
            }

            alert.setNegativeButton("Fechar", new DialogInterface.OnClickListener() {
                @Override
                /**
                 * Ao clicar na view, realiza uma ação.
                 *
                 * @param dialog AlertDialog em execução.
                 * @param which Escolha feita.
                 */
                public void onClick(DialogInterface dialog, int which) {
                    Log.i(TAG, "Finalizando caixa de dialogo. Alterações não foram feitas.");
                }
            });

            return alert.create();
        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage());
            return null;
        }
    }

    @Override
    /**
     * TextWatcher durante a mudança do EditText.
     *
     * @param s String do EditText.
     * @param start Posição no qual foi modificado.
     * @param before Posição da mudança.
     * @param count Caracteres modificados.
     */
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        try {
            String preco = et_preco.getText().toString();
            String quant = et_quantidade.getText().toString();

            if (preco.isEmpty() || quant.isEmpty()) {
                txt_total.setText(null);
            } else {
                double preço = Double.parseDouble(preco);
                int quantidade = Integer.parseInt(quant);

                if (preço < 0 || quantidade < 0) {
                    txt_total.setText(null);
                } else {
                    String format = "Total: " + NumberFormat.getCurrencyInstance().format(preço * quantidade);

                    txt_total.setText(format);
                }
            }
        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage());
        }
    }

    /**
     * Função para implementar o botão positivo do AlertDialog.
     */
    protected void doPositiveClick() {
        try {
            OrcamentoDAO dao = new OrcamentoDAO(activity);
            dao.open(true);

            Orcamento orcamento = create();

            if (orcamento != null) {
                dao.insert(orcamento);
                Toast.makeText(activity, "Gravado com sucesso!", Toast.LENGTH_SHORT).show();

                dao.closeDatabase();
            } else {
                Toast.makeText(activity, "Houve um erro ao gravar", Toast.LENGTH_LONG).show();
            }
        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage());
        }
    }

    /**
     * Cria um Orçamento.
     *
     * @return o Orçamento retornado.
     */
    private Orcamento create() {
        Log.i(TAG, "Criando orçamento ...");
        Orcamento orcamento = new Orcamento();

        try {
            Item item = (Item) getArguments().getSerializable("item");

            String observacao = et_observacao.getText().toString().trim();
            String estabelecimento = et_estabelecimento.getText().toString().trim();

            double preco = Double.parseDouble(et_preco.getText().toString().trim());
            int quantidade = Integer.parseInt(et_quantidade.getText().toString().trim());

            if (quantidade < 0 || preco < 0) {
                Toast.makeText(activity, "Valores não informados corretamente", Toast.LENGTH_LONG).show();

                return null;
            } else {
                orcamento.setItem(item);
                orcamento.setPreço(preco);
                orcamento.setQuantidade(quantidade);
                orcamento.setObservação(observacao);
                orcamento.setEstabelecimento(estabelecimento);

                return orcamento;
            }
        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage());

            return null;
        }
    }
}
