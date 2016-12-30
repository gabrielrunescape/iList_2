package ilist.gabrielrunescape.com.br.dao;

import java.util.List;
import android.util.Log;
import java.util.ArrayList;
import android.content.Context;
import android.database.Cursor;
import android.content.ContentValues;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import ilist.gabrielrunescape.com.br.object.Item;
import ilist.gabrielrunescape.com.br.object.Orcamento;
import ilist.gabrielrunescape.com.br.database.VersionUtils;
import ilist.gabrielrunescape.com.br.database.CustomSQLiteOpenHelper;

/**
 * Classe responsável por todos os eventos de inserir, alterar, apagar e exibir
 * todos os orçamentos.
 *
 * @author Gabriel Filipe
 * @version 1.0
 * @since 2016-12-21
 */
public class OrcamentoDAO {
    private SQLiteDatabase database;
    private CustomSQLiteOpenHelper helper;
    private static String TAG = OrcamentoDAO.class.getSimpleName(); // Logging

    /**
     * Método construtor da classe.
     *
     * @param c Contexto da aplicação.
     */
    public OrcamentoDAO(Context c) {
        try {
            Log.i(TAG, "Lendo banco banco de dados ...");
            helper = new CustomSQLiteOpenHelper(c, "iList_dbase", VersionUtils.getVersionCode(c));
        } catch (SQLException ex) {
            Log.e(TAG, "Erro ao ler o banco de dados");
            ex.printStackTrace();
        }
    }

    /**
     * Abre o banco de dados.
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
     * Verifica se o valor é nulo.
     *
     * @param value Valor a ser verificado.
     * @return O valor verificado ou valor nulo.
     */
    private String checkNullValue(String value) {
        if (value == null || value.isEmpty() || value.equalsIgnoreCase("null")) {
            return "";
        } else {
            return value;
        }
    }

    /**
     * Fecha o banco de dados.
     */
    public void closeDatabase() {
        database.close();
    }

    /**
     * Apaga o orçamento e atualiza o status e quantidade do Item.
     *
     * @param id
     */
    public void delete(int id) {
        database.delete("`Orcamento`", "ID = " + id, null);
        updateQuantidade(id);
    }

    /**
     * Insere um novo Orçamento no banco de dados.
     *
     * @param o Orçamento a ser inserido.
     * @return Orçamento inserido.
     */
    public Orcamento insert(Orcamento o) {
        ContentValues values = new ContentValues();

        try {
            values.put("Preco", o.getPreço());
            values.put("Item", o.getItem().getID());
            values.put("Observacao", o.getObservação());
            values.put("Quantidade", o.getQuantidade());
            values.put("Estabelecimento", o.getEstabelecimento());

            Log.i(TAG, "Inserindo orçamento ... ");
            long id = database.insert("`Orcamento`", null, values);

            Cursor cursor = database.rawQuery("SELECT * FROM `Orcamento` WHERE ID = " + id, null);
            cursor.moveToFirst();

            Orcamento orc = new Orcamento();

            orc.setID(cursor.getInt(0));
            orc.setEstabelecimento(checkNullValue(cursor.getString(1)));
            orc.setQuantidade(cursor.getInt(2));
            orc.setPreço(cursor.getDouble(3));
            orc.setObservação(checkNullValue(cursor.getString(4)));
            orc.setItem(new Item(cursor.getInt(5)));

            cursor.close();

            updateQuantidade(orc.getID());
            Log.i(TAG, "Orçamento inserido com sucessso!");

            return orc;
        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage());

            return null;
        }
    }

    /**
     * Obtem todos os registros de transações dentro do banco de dados.
     *
     * @return uma lista do tipo Transaction.
     */
    public List<Orcamento> getAllFromItens(Item item) {
        List<Orcamento> orcamento = new ArrayList<>();
        String query = "SELECT * FROM Orcamento WHERE Item = " + item.getID();

        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();

        try {
            while (!cursor.isAfterLast()) {
                Orcamento o = new Orcamento();

                o.setID(cursor.getInt(0));
                o.setEstabelecimento(checkNullValue(cursor.getString(1)));
                o.setQuantidade(cursor.getInt(2));
                o.setPreço(cursor.getDouble(3));
                o.setObservação(checkNullValue(cursor.getString(4)));
                o.setItem(new Item(cursor.getInt(5)));

                orcamento.add(o);
                cursor.moveToNext();

                Log.i(TAG, "Obtendo orçamentos ...");
            }

            return orcamento;
        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage());

            return null;
        } finally {
            cursor.close();
        }
    }

    /**
     * Atualizade a quantidade e o status do Item.
     *
     * @param id Código identificador do Orçamento.
     */
    private void updateQuantidade(int id) {
        ContentValues cv = new ContentValues();
        String query = "SELECT SUM(O.Quantidade), O.Item, I.Quantidade, I.Comprado FROM Orcamento O INNER JOIN Item I ON O.Item = I.ID WHERE O.Item = (SELECT Item FROM Orcamento WHERE ID = " + id + ")";

        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();

        try {
            int total = cursor.getInt(0);
            int item = cursor.getInt(1);
            int quantidade = cursor.getInt(2);

            if (total > quantidade) {
                cv.put("Status", 4);
            } else if (total == quantidade) {
                cv.put("Status", 3);
            } else if (total < quantidade) {
                cv.put("Status", 2);
            } else {
                cv.put("Status", 1);
            }

            cv.put("Comprado", total);
            database.update("`Item`", cv, "ID = " + item, null);

            Log.i(TAG, "Alterado quantidade com sucesso!");
        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage());
        } finally {
            cursor.close();
        }
    }
}
