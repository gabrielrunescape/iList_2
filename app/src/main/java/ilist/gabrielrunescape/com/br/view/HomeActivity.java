package ilist.gabrielrunescape.com.br.view;

import android.util.Log;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.view.MenuItem;
import android.content.Intent;
import ilist.gabrielrunescape.com.br.R;
import android.support.v7.widget.Toolbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.app.AppCompatActivity;
import ilist.gabrielrunescape.com.br.dao.ItemDAO;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import ilist.gabrielrunescape.com.br.adapter.ItemAdapter;
import android.support.design.widget.FloatingActionButton;
import ilist.gabrielrunescape.com.br.model.SimpleDividerItemDecoration;

/**
 * Cria e exibe todos os elementos (views) necessários na tela inicial (HomeActivity).
 *
 * @author Gabriel Filipe
 * @version 1.0
 * @since 2016-12-25
 */
public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private ItemDAO dao;
    private int opcaoNav = 0;
    private DrawerLayout drawer;
    private RecyclerView recyclerView;
    private ActionBarDrawerToggle toggle;
    private SwipeRefreshLayout swipeContainer;
    private FloatingActionButton floatingButton;
    private static String TAG = HomeActivity.class.getSimpleName();

    @Override
    /**
     * Ao pressionar o botão para ser retornado.
     */
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    /**
     * Cria e carrega todos os elementos ao exibir a view.
     *
     * @param savedInstanceState Salva os dados da instância.
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Log.i(TAG, "Criando e carregando elementos da ActivityView ...");

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.content_home);
        floatingButton = (FloatingActionButton) findViewById(R.id.floatingButton);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());

        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(this));
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    /**
     * Define uma escolha para MenuItem que foi clicado no Navigation Drawer.
     *
     * @param item MenuItem escolhido (clicado).
     * @return Verdadeiro.
     */
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        //Intent intent;
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_az:
                opcaoNav = R.id.nav_az;
                break;
            case R.id.nav_za:
                opcaoNav = R.id.nav_za;
                break;
            case R.id.nav_add:
                //intent = new Intent(HomeActivity.this, ItemActivity.class);
                //startActivity(intent);
                break;
            case R.id.nav_comprar:
                opcaoNav = R.id.nav_comprar;
                break;
            case R.id.nav_comprado:
                opcaoNav = R.id.nav_comprado;
                break;
            case R.id.nav_report:
                Intent intent = new Intent(HomeActivity.this, ReportActivity.class);
                startActivity(intent);
                break;
            default:
                Toast.makeText(this, "Função não programada", Toast.LENGTH_LONG).show();
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        updateData();

        return true;
    }

    /**
     * (Re)carrega todos os elementos para o RecyclerView.
     */
    public void updateData() {
        try {
            dao = new ItemDAO(this);
            dao.open(false);

            final ItemAdapter adapter = new ItemAdapter(dao.getAllOrdened(opcaoNav), getFragmentManager());
            adapter.notifyDataSetChanged();

            recyclerView.setAdapter(adapter);
            swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    swipeContainer.setRefreshing(false);

                    adapter.clear();
                    adapter.addAll(dao.getAllOrdened(opcaoNav));
                }
            });
        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage());
        }
    }
}
