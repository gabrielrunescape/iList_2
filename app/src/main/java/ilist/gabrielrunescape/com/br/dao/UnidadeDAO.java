package ilist.gabrielrunescape.com.br.dao;

import android.util.Log;
import android.content.Context;
import android.database.Cursor;
import android.widget.ArrayAdapter;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import ilist.gabrielrunescape.com.br.object.Unidade;
import ilist.gabrielrunescape.com.br.database.VersionUtils;
import ilist.gabrielrunescape.com.br.database.CustomSQLiteOpenHelper;

/**
 * Classe  responsável por todos os eventos de inserir, alterar, apagar e exibir todas as unidades
 * de médidas.
 *
 * @author Gabriel Filipe
 * @version 1.0
 * @since 2016-12-19
 */
public class UnidadeDAO {
    private SQLiteDatabase database;
    private CustomSQLiteOpenHelper helper;
    private static String TAG = UnidadeDAO.class.getSimpleName();

    /**
     * Método construtor da classe.
     *
     * @param context Contexto da aplicação.
     */
    public UnidadeDAO(Context context) {
        try {
            Log.i(TAG, "Lendo banco banco de dados ...");

            helper = new CustomSQLiteOpenHelper(context, "iList_dbase", VersionUtils.getVersionCode(context));
        } catch (SQLException ex) {
            Log.e(TAG, "Erro ao ler o banco de dados");
            ex.printStackTrace();
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
            Log.e(TAG, "Erro ao abrir o banco de dados");
            ex.printStackTrace();
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
                Log.i(TAG, "Finalizando banco de dados");
                database.close();
            }
        } catch (Throwable ex) {
            Log.e(TAG, "Erro ao finalizar o banco de dados");
            ex.printStackTrace();
        }
    }

    /**
     * Obtem todos os registros de transações dentro do banco de dados.
     *
     * @return Um ArrayAdapter com todas as informações.
     */
    public ArrayAdapter<Unidade> getArrayAdapter (Context c) {
        String query = "SELECT * FROM Unidade";
        ArrayAdapter<Unidade> adapter = new ArrayAdapter(c, android.R.layout.simple_list_item_1);

        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();

        try {
            while (!cursor.isAfterLast()) {
                Unidade u = new Unidade();

                u.setID(cursor.getInt(0));
                u.setDescrição(cursor.getString(1));
                u.setAbreviação(cursor.getString(2));

                adapter.add(u);
                cursor.moveToNext();

                Log.i(TAG, "Obtendo unidades ...");
            }

            return adapter;
        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage());

            return null;
        } finally {
            cursor.close();
        }
    }
}
