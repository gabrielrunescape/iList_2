package ilist.gabrielrunescape.com.br.view;

import java.text.DecimalFormat;
import java.util.List;
import android.util.Log;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Spinner;
import android.app.AlertDialog;
import android.widget.EditText;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.widget.ArrayAdapter;
import android.content.DialogInterface;
import ilist.gabrielrunescape.com.br.R;
import android.support.v7.widget.RecyclerView;
import android.support.v7.app.AppCompatActivity;
import ilist.gabrielrunescape.com.br.dao.ItemDAO;
import ilist.gabrielrunescape.com.br.object.Item;
import ilist.gabrielrunescape.com.br.object.Status;
import ilist.gabrielrunescape.com.br.object.Unidade;
import ilist.gabrielrunescape.com.br.dao.UnidadeDAO;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import ilist.gabrielrunescape.com.br.dao.OrcamentoDAO;
import ilist.gabrielrunescape.com.br.object.Orcamento;
import android.text.method.HideReturnsTransformationMethod;
import ilist.gabrielrunescape.com.br.model.DialogOrcamento;
import ilist.gabrielrunescape.com.br.adapter.OrcamentoAdapter;
import ilist.gabrielrunescape.com.br.model.SimpleDividerItemDecoration;

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
    private List<Orcamento> itens;
    private OrcamentoDAO orcamento;
    private EditText et_Quantidade;
    private RecyclerView recyclerView;
    private ArrayAdapter<Unidade> adapter;
    private RecyclerView.LayoutManager mLayoutManager;
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
        orcamento = new OrcamentoDAO(this);

        et_Nome = (EditText) findViewById(R.id.et_nome);
        spinner = (Spinner) findViewById(R.id.spinner_unidade);
        et_Quantidade = (EditText) findViewById(R.id.et_Quantidade);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView = (RecyclerView) findViewById(R.id.recycler_orcamento);

        recyclerView.setActivated(true);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(this));

        dao.open(false);
        orcamento.open(false);
        Log.i(TAG, "Método onCreate()");
        et_Quantidade.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
    }

    @Override
    /**
     * (Re)carrega todos os elementos da view após criar, pausar ou destruir a ActivityView.
     */
    protected void onResume() {
        super.onResume();

        dao.open(false);
        orcamento.open(false);

        try {
            Log.i(TAG, "Método onResume()");

            adapter = dao.getArrayAdapter(this);
            spinner.setAdapter(adapter);

            loadViews();
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
        final int id = ((Item) getIntent().getSerializableExtra("Item")).getID();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Confirmar exclusão?");
        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ItemDAO dao = new ItemDAO(ItemActivity.this);
                dao.open(true);

                dao.delete(id);
                dao.closeDatabase();

                Toast.makeText(ItemActivity.this, "Item apagado com sucesso!", Toast.LENGTH_SHORT).show();
                finish();
            }
        }).setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.create().show();
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

    protected void loadViews() {
        try {
            if (getIntent().getSerializableExtra("Item") != null) {
                int position = 0;
                Item i = (Item) getIntent().getSerializableExtra("Item");

                for (int j = 0; j < adapter.getCount(); j++) {
                    if (adapter.getItem(j).getID() == i.getUnidade().getID()) {
                        position = j;
                        break;
                    }
                }

                et_Nome.setText(i.getNome());
                spinner.setSelection(position);
                et_Quantidade.setText(i.getQuantidade() + "");

                refreshRecyclerView(i);
            } else {
                recyclerView.setVisibility(View.INVISIBLE);
            }
        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage());
        }
    }

    private void refreshRecyclerView(Item item) {
        itens = orcamento.getAllFromItens(item);

        OrcamentoAdapter idapter = new OrcamentoAdapter(itens);
        recyclerView.setAdapter(idapter);
    }
}
