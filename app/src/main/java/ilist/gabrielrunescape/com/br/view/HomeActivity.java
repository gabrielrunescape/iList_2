package ilist.gabrielrunescape.com.br.view;

import java.util.List;
import android.util.Log;
import android.os.Bundle;
import android.view.View;
import java.util.ArrayList;
import ilist.gabrielrunescape.com.br.R;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.app.AppCompatActivity;
import ilist.gabrielrunescape.com.br.object.Item;
import ilist.gabrielrunescape.com.br.object.Status;
import ilist.gabrielrunescape.com.br.object.Unidade;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import ilist.gabrielrunescape.com.br.adapter.ItemAdapter;
import android.support.design.widget.FloatingActionButton;

/**
 * Cria e exibe todos os elementos (views) necessários na tela inicial (HomeActivity).
 *
 * @author Gabriel Filipe
 * @version 1.0
 * @since 2016-12-25
 */
public class HomeActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FloatingActionButton floatingButton;
    private static String TAG = HomeActivity.class.getSimpleName();

    @Override
    /**
     * Cria e carrega todos os elementos ao exibir a view.
     *
     * @param savedInstanceState Salva os dados da instância.
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Log.i(TAG, "Criando e carregando elementos da ActivityView ...");

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        floatingButton = (FloatingActionButton) findViewById(R.id.floatingButton);
    }

    @Override
    /**
     * (Re)carrega todos os elementos da view após criar, pausar ou destruir a ActivityView.
     */
    protected void onResume() {
        super.onResume();

        try {
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());

            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());

            List<Item> list = new ArrayList<>();

            for (int i = 0; i < 11; i++) {
                Item item = new Item();

                item.setNome("Parafernalha");
                item.setQuantidade(i + 2);
                item.setStatus(new Status(i, "Comprado"));
                item.setUnidade(new Unidade("Unidade", "un"));

                list.add(item);
            }

            ItemAdapter adapter = new ItemAdapter(list);
            recyclerView.setAdapter(adapter);

            floatingButton.setOnClickListener(new View.OnClickListener() {
                @Override
                /**
                 * Ao clicar na view, realiza uma ação.
                 *
                 * @param view FloatingActionButton clicado.
                 */
                public void onClick(View view) {
                    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                }
            });
        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage());
        }
    }
}
