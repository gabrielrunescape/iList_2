package ilist.gabrielrunescape.com.br.view;

import android.util.Log;
import android.os.Bundle;
import android.widget.TextView;
import ilist.gabrielrunescape.com.br.R;
import android.support.v7.widget.RecyclerView;
import android.support.v7.app.AppCompatActivity;
import ilist.gabrielrunescape.com.br.dao.ReportDAO;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import ilist.gabrielrunescape.com.br.adapter.ReportAdapter;
import ilist.gabrielrunescape.com.br.model.SimpleDividerItemDecoration;

/**
 * É uma extensora da classe Activity(AppCompatActivity) que realiza funções de criar, inicializar
 * e exibir view em sua activity.
 *
 * @author Gabriel Filipe
 * @version 1.0
 * @since 2016-12-29
 */
public class ReportActivity extends AppCompatActivity {
    private ReportDAO dao;
    private RecyclerView recyclerView;
    private TextView[] textViews = new TextView[6];
    private RecyclerView.LayoutManager mLayoutManager;
    private static String TAG = ReportActivity.class.getSimpleName();

    @Override
    /**
     * Cria e carrega todos os elementos ao exibir a view.
     *
     * @param savedInstanceState Salva os dados da instância.
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        try {
            dao = new ReportDAO(this);

            textViews[0] = (TextView) findViewById(R.id.txt_parcialmente);
            textViews[1] = (TextView) findViewById(R.id.txt_comprado);
            textViews[2] = (TextView) findViewById(R.id.txt_exceco);
            textViews[3] = (TextView) findViewById(R.id.txt_preco_parcialmente);
            textViews[4] = (TextView) findViewById(R.id.txt_preco_comprado);
            textViews[5] = (TextView) findViewById(R.id.txt_preco_exceco);

            dao.open(false);
            recyclerView = (RecyclerView) findViewById(R.id.recycler_report);
            mLayoutManager = new LinearLayoutManager(getApplicationContext());

            recyclerView.setActivated(true);
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.addItemDecoration(new SimpleDividerItemDecoration(this));
        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage());
        }
    }

    @Override
    /**
     * (Re)carrega todos os elementos da view após criar, pausar ou destruir a ActivityView.
     */
    protected void onResume() {
        dao.open(false);
        super.onResume();

        try {
            ReportAdapter adapter = new ReportAdapter(dao.getAll());

            dao.defineTextView(textViews);
            recyclerView.setAdapter(adapter);
        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage());
        }
    }
}
