package ilist.gabrielrunescape.com.br.dao;

import java.util.List;
import android.util.Log;
import java.util.ArrayList;
import java.text.NumberFormat;
import android.database.Cursor;
import android.content.Context;
import android.widget.TextView;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import ilist.gabrielrunescape.com.br.object.Report;
import ilist.gabrielrunescape.com.br.database.VersionUtils;
import ilist.gabrielrunescape.com.br.database.CustomSQLiteOpenHelper;

/**
 * Classe responsável por exibir valores de relatório.
 *
 * @author Gabriel Filipe
 * @version 1.0
 * @since 2017-01-01
 */
public class ReportDAO {
    private SQLiteDatabase database;
    private CustomSQLiteOpenHelper helper;
    private static String TAG = ReportDAO.class.getSimpleName();

    /**
     * Método construtor da classe.
     *
     * @param c Contexto da aplicação.
     */
    public ReportDAO(Context c) {
        try {
            Log.i(TAG, "Lendo banco banco de dados ...");
            helper = new CustomSQLiteOpenHelper(c, "iList_dbase", VersionUtils.getVersionCode(c));
        } catch (SQLException ex) {
            Log.e(TAG, "Erro ao ler o banco de dados.\n" + ex.getMessage());
        }
    }

    /**
     * Fecha o banco de dados se estiver em aberto.
     */
    @Override
    protected void finalize() {
        try {
            super.finalize();

            if (database != null && database.isOpen()) {
                Log.i(TAG, "Finalizando banco de dados.");
                database.close();
            }
        } catch (Throwable ex) {
            Log.e(TAG, "Erro ao finalizar o banco de dados." + ex.getMessage());
        }
    }

    /**
     * Abre o banco de dados.
     *
     * @param writable Falso para somente leitura.
     */
    public void open(boolean writable) {
        try {
            Log.i(TAG, "Abrindo banco de dados ...");

            if (writable) {
                database = helper.getWritableDatabase();
            } else {
                database = helper.getReadableDatabase();
            }
        } catch (SQLException ex) {
            Log.e(TAG, "Erro ao abrir o banco de dados.\n" + ex.getMessage());
        }
    }

    /**
     * Fecha o banco de dados.
     */
    public void closeDatabase() {
        Log.i(TAG, "Finalizando banco de dados.");
        database.close();
    }

    public TextView[] defineTextView(TextView[] textViews) {
        int abx = 0;
        int aby = 0;
        int abz = 0;

        double bcx = 0;
        double bcy = 0;
        double bcz = 0;

        String query = "SELECT I.Quantidade, SUM(O.Quantidade), SUM(O.Preco), I.Status FROM Orcamento O INNER JOIN Item I ON O.Item = I.ID GROUP BY O.Item, I.Quantidade, I.Status ORDER BY I.Nome ASC";

        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();

        try {
            while (!cursor.isAfterLast()) {
                switch (cursor.getInt(3)) {
                    case 2:
                        abx += cursor.getInt(1);
                        bcx += cursor.getDouble(2);
                        break;
                    case 3:
                        aby += cursor.getInt(1);
                        bcy += cursor.getDouble(2);
                        break;
                    case 4:
                        abz += cursor.getInt(1);
                        bcz += cursor.getDouble(2);
                        break;
                }

                cursor.moveToNext();
            }

            textViews[0].setText(NumberFormat.getIntegerInstance().format(abx));
            textViews[1].setText(NumberFormat.getIntegerInstance().format(aby));
            textViews[2].setText(NumberFormat.getIntegerInstance().format(abz));
            textViews[3].setText(NumberFormat.getCurrencyInstance().format(bcx));
            textViews[4].setText(NumberFormat.getCurrencyInstance().format(bcy));
            textViews[5].setText(NumberFormat.getCurrencyInstance().format(bcz));
        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage());
        } finally {
            cursor.close();
        }

        return textViews;
    }

    /**
     * Obtem todos os registros de transações dentro do banco de dados.
     *
     * @return uma lista do tipo Transaction.
     */
    public List<Report> getAll() {
        int acomprar = 0;
        int comprado = 0;
        double total = 0;

        List<Report> lista = new ArrayList<>();
        String query = "SELECT I.Quantidade, SUM(O.Quantidade), I.Nome, SUM(O.Preco), I.Status FROM Orcamento O INNER JOIN Item I ON O.Item = I.ID GROUP BY I.Nome, I.Quantidade ORDER BY I.Nome ASC";

        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();

        try {
            while (!cursor.isAfterLast()) {
                Report report = new Report();

                report.setAcomprar(cursor.getInt(0));
                report.setComprado(cursor.getInt(1));
                report.setTexto(cursor.getString(2));
                report.setTotal(cursor.getDouble(3));

                if (cursor.getInt(4) > 1) {
                    lista.add(report);

                    acomprar += cursor.getInt(0);
                    comprado += cursor.getInt(1);
                    total += cursor.getDouble(3);
                }

                cursor.moveToNext();
            }

            lista.add(new Report(acomprar, comprado, "Total dos itens", total));

            return lista;
        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage());

            return null;
        } finally {
            cursor.close();
        }
    }
}
