package ilist.gabrielrunescape.com.br.view;

import java.util.List;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.EditText;
import android.widget.ArrayAdapter;
import ilist.gabrielrunescape.com.br.R;
import android.support.v7.widget.RecyclerView;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import ilist.gabrielrunescape.com.br.dao.ItemDAO;
import ilist.gabrielrunescape.com.br.dao.UnidadeDAO;
import ilist.gabrielrunescape.com.br.object.Item;
import ilist.gabrielrunescape.com.br.object.Status;
import ilist.gabrielrunescape.com.br.object.Unidade;

/**
 * É uma extensora da classe Activity(AppCompatActivity) que realiza funções de criar, inicializar
 * e exibir view em sua activity.
 *
 * @author Gabriel Filipe
 * @version 1.0
 * @since 2016-12-26
 */
public class ItemActivity extends AppCompatActivity {
    private UnidadeDAO dao;
    private Spinner spinner;
    private EditText et_Nome;
    private Button btn_orcamento;
    /*private List<Orcamento> itens;
    private OrcamentoDAO orcamento;*/
    private EditText et_Quantidade;
    private RecyclerView recyclerView;
    private ArrayAdapter<Unidade> adapter;
    private static String TAG = ItemActivity.class.getSimpleName();

    @Override
    /**
     * Cria e carrega todos os elementos ao exibir a view.
     *
     * @param savedInstanceState Salva os dados da instância.
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        dao = new UnidadeDAO(this);

        et_Nome = (EditText) findViewById(R.id.et_nome);
        spinner = (Spinner) findViewById(R.id.spinner_unidade);
        btn_orcamento = (Button) findViewById(R.id.btn_orcamento);
        et_Quantidade = (EditText) findViewById(R.id.et_quantidade);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_orcamento);

        dao.open(false);
        Log.i(TAG, "Método onCreate()");
    }

    @Override
    /**
     * (Re)carrega todos os elementos da view após criar, pausar ou destruir a ActivityView.
     */
    protected void onResume() {
        dao.open(false);
        super.onResume();

        try {
            Log.i(TAG, "Método onResume()");

            adapter = dao.getArrayAdapter(this);
            spinner.setAdapter(adapter);
        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage());
        }
    }

    /**
     * Importa um menu para Toolbar padrão da aplicação.
     *
     * @param menu Toolbar da aplicação.
     * @return Verdadeiro.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);

        if (getIntent().getSerializableExtra("Item") == null) {
            menu.findItem(R.id.item_delete).setVisible(false);
        }

        return true;
    }

    /**
     * Define a função de cada item do menu.
     *
     * @param item Item no qual foi selecionado.
     * @return O item selecionado.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                Log.i(TAG, "Voltando para HomeActivity ...");
                break;
            case R.id.item_done:
                doneItem();
                break;
            case R.id.item_delete:
                deleteItem();
                break;
            default:
                Toast.makeText(this, "Função não programada!", Toast.LENGTH_LONG).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Método usado para implementar a exclusão de um item fora do método onOptionsItemSelected().
     */
    public void deleteItem() {
        ItemDAO dao = new ItemDAO(this);
        int id = ((Item) getIntent().getSerializableExtra("Item")).getID();

        dao.open(true);
        dao.delete(id);
        dao.closeDatabase();

        Toast.makeText(this, "Item apagado com sucesso!", Toast.LENGTH_SHORT).show();
        finish();
    }

    /**
     * Método usado para implementar na inserção de um item fora do método onOptionsItemSelected().
     */
    private void doneItem() {
        try {
            ItemDAO dao = new ItemDAO(this);

            String description = et_Nome.getText().toString().trim();
            String quantity = et_Quantidade.getText().toString().trim();

            if (description.isEmpty() || quantity.isEmpty()) {
                Toast.makeText(this, "Existem campos vazios!", Toast.LENGTH_LONG).show();
            } else {
                dao.open(true);
                Item item = new Item();

                item.setNome(description);
                item.setQuantidade(Integer.parseInt(quantity));
                item.setUnidade(adapter.getItem(spinner.getSelectedItemPosition()));

                if (getIntent().getSerializableExtra("Item") == null) {
                    item.setStatus(new Status(1, null));
                    item = dao.insert(item);
                } else {
                    item.setStatus(new Status(2, null));
                    item.setID(((Item) getIntent().getSerializableExtra("Item")).getID());
                    item = dao.update(item);
                }

                if (item != null) {
                    Toast.makeText(this, "Gravado com sucesso!", Toast.LENGTH_SHORT).show();
                    dao.closeDatabase();
                    finish();
                } else {
                    Toast.makeText(this, "Houve um erro ao gravar", Toast.LENGTH_LONG).show();
                }
            }
        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage());
            Toast.makeText(this, "Foi encontrado um erro.", Toast.LENGTH_SHORT).show();
        }
    }

    /*protected void loadViews() {
        try {
            final FragmentManager support = getFragmentManager();
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());

            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());

            if (getIntent().getSerializableExtra("Item") != null) {
                Item i = (Item) getIntent().getSerializableExtra("Item");

                itens = orcamento.getAllFromItens(i.getID());

                et_Nome.setText(i.getNome());
                et_Quantidade.setText(i.getQuantidade() + "");
                spinner.setSelection(Integer.parseInt(i.getUnidade()) - 1);

                OrcamentoAdapter idapter = new OrcamentoAdapter(itens);
                recyclerView.setAdapter(idapter);
            } else {
                recyclerView.setVisibility(View.INVISIBLE);
                btn_orcamento.setVisibility(View.INVISIBLE);
            }

            btn_orcamento.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Item i = (Item) getIntent().getSerializableExtra("Item");

                        DialogFragment dialog = new DialogOrcamento().newInstance("Novo orçamento", i.getID());
                        dialog.show(support, "ItemActivity");
                    } catch (Exception ex) {
                        Log.e(TAG, ex.getMessage());
                    }
                }
            });
        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage());
        }
    }*/
}
