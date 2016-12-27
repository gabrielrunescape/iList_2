package ilist.gabrielrunescape.com.br.view;

import android.util.Log;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import ilist.gabrielrunescape.com.br.R;
import android.support.v7.widget.RecyclerView;
import android.support.v7.app.AppCompatActivity;
import ilist.gabrielrunescape.com.br.dao.ItemDAO;
import android.support.v4.widget.SwipeRefreshLayout;
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
    private ItemDAO dao;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeContainer;
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
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        floatingButton = (FloatingActionButton) findViewById(R.id.floatingButton);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());

        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        swipeContainer.setColorSchemeResources(android.R.color.holo_red_light, android.R.color.holo_orange_light);
    }

    @Override
    /**
     * (Re)carrega todos os elementos da view após criar, pausar ou destruir a ActivityView.
     */
    protected void onResume() {
        super.onResume();

        try {
            updateData();

            floatingButton.setOnClickListener(new View.OnClickListener() {
                @Override
                /**
                 * Ao clicar na view, realiza uma ação.
                 *
                 * @param view FloatingActionButton clicado.
                 */
                public void onClick(View view) {
                    Intent intent = new Intent(HomeActivity.this, ItemActivity.class);
                    startActivity(intent);
                }
            });
        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage());
        }
    }

    /**
     * (Re)carrega todos os elementos para o RecyclerView.
     */
    public void updateData() {
        try {
            dao = new ItemDAO(this);
            dao.open(false);

            final ItemAdapter adapter = new ItemAdapter(dao.getAll());
            adapter.notifyDataSetChanged();

            recyclerView.setAdapter(adapter);
            swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    swipeContainer.setRefreshing(false);

                    adapter.clear();
                    adapter.addAll(dao.getAll());
                }
            });
        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage());
        }
    }
}
