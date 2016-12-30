package ilist.gabrielrunescape.com.br.dao;

import java.util.List;
import android.util.Log;
import java.util.ArrayList;
import android.content.Context;
import android.database.Cursor;
import android.content.ContentValues;
import android.database.SQLException;
import ilist.gabrielrunescape.com.br.R;
import android.database.sqlite.SQLiteDatabase;
import ilist.gabrielrunescape.com.br.object.Item;
import ilist.gabrielrunescape.com.br.object.Status;
import ilist.gabrielrunescape.com.br.object.Unidade;
import ilist.gabrielrunescape.com.br.database.VersionUtils;
import ilist.gabrielrunescape.com.br.database.CustomSQLiteOpenHelper;

/**
 * Classe responsável por todos os eventos de inserir, alterar, apagar e exibir
 * todos os itens.
 *
 * @author Gabriel Filipe
 * @version 1.0
 * @since 2016-12-18
 */
public class ItemDAO {
    private Context context;
    private SQLiteDatabase database;
    private CustomSQLiteOpenHelper helper;
    private static String TAG = ItemDAO.class.getSimpleName();

    /**
     * Método construtor da classe.
     *
     * @param c Contexto da aplicação.
     */
    public ItemDAO(Context c) {
        context = c;

        try {
            Log.i(TAG, "Lendo banco banco de dados ...");
            helper = new CustomSQLiteOpenHelper(c, "iList_dbase", VersionUtils.getVersionCode(c));
        } catch (SQLException ex) {
            Log.e(TAG, "Erro ao ler o banco de dados.\n" + ex.getMessage());
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
            Log.e(TAG, "Erro ao finalizar o banco de dados.\n" + ex.getMessage());
        }
    }

    /**
     * Fecha o banco de dados.
     */
    public void closeDatabase() {
        Log.i(TAG, "Finalizando banco de dados.");
        database.close();
    }

    /**
     * Apaga todos os itens e orçamentos.
     *
     * @param id Código identificador do Item.
     */
    public void delete(int id) {
        database.delete("`Item`", "ID = " + id, null);

        Log.i(TAG, "Apagado com sucesso!");
    }

    /**
     * Insere um novo Item.
     *
     * @param i Objeto Item.
     * @return O resultado da operação.
     */
    public Item insert(Item i) {
        ContentValues values = new ContentValues();

        try {
            values.put("Nome", i.getNome());
            values.put("Comprado", i.getComprado());
            values.put("Quantidade", i.getQuantidade());
            values.put("Status", i.getStatus().getID());
            values.put("Unidade", i.getUnidade().getID());

            Log.i(TAG, "Inserindo item ... ");
            long id = database.insert("`Item`", null, values);
            String query = "SELECT I.ID, I.Nome, I.Quantidade, I.Comprado, S.ID AS `Status`, S.Descricao AS `Descriçao`, U.ID AS `Unidade`, U.Descricao, U.Abreviacao FROM `Item` I INNER JOIN `Status` S ON S.ID = I.Status INNER JOIN `Unidade` U ON U.ID = I.Unidade WHERE I.ID = " + id;

            Cursor cursor = database.rawQuery(query , null);
            cursor.moveToFirst();

            Item item = new Item();

            item.setID(cursor.getInt(0));
            item.setNome(cursor.getString(1));
            item.setQuantidade(cursor.getInt(2));
            item.setComprado(cursor.getInt(3));
            item.setStatus(new Status(cursor.getInt(4), cursor.getString(5)));
            item.setUnidade(new Unidade(cursor.getInt(6), cursor.getString(7), cursor.getString(8)));

            cursor.close();

            Log.i(TAG, "Item inserido com sucessso!");

            return item;
        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage());

            return null;
        }
    }

    /**
     * Altera um Item já existente.
     *
     * @param i Objeto Item com as novas informações.
     * @return O resultado da operação.
     */
    public Item update(Item i) {
        ContentValues values = new ContentValues();

        try {
            values.put("Nome", i.getNome());
            values.put("Comprado", i.getComprado());
            values.put("Quantidade", i.getQuantidade());
            values.put("Status", i.getStatus().getID());
            values.put("Unidade", i.getUnidade().getID());

            Log.i(TAG, "Alterando item ... ");
            database.update("`Item`", values, "ID = " + i.getID(), null);
            String query = "SELECT I.ID, I.Nome, I.Quantidade, I.Comprado, S.ID AS `Status`, S.Descricao AS `Descriçao`, U.ID AS `Unidade`, U.Descricao, U.Abreviacao FROM `Item` I INNER JOIN `Status` S ON S.ID = I.Status INNER JOIN `Unidade` U ON U.ID = I.Unidade WHERE I.ID = " + i.getID();

            Cursor cursor = database.rawQuery(query , null);
            cursor.moveToFirst();

            Item item = new Item();

            item.setID(cursor.getInt(0));
            item.setNome(cursor.getString(1));
            item.setQuantidade(cursor.getInt(2));
            item.setComprado(cursor.getInt(3));
            item.setStatus(new Status(cursor.getInt(4), cursor.getString(5)));
            item.setUnidade(new Unidade(cursor.getInt(6), cursor.getString(7), cursor.getString(8)));

            cursor.close();

            Log.i(TAG, "Item alterado com sucessso!");

            return item;
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
    public List<Item> getAll() {
        List<Item> item = new ArrayList<>();
        String query = "SELECT I.ID, I.Nome, I.Quantidade, I.Comprado, S.ID AS `Status`, S.Descricao AS `Descriçao`, U.ID AS `Unidade`, U.Descricao, U.Abreviacao FROM `Item` I INNER JOIN `Status` S ON S.ID = I.Status INNER JOIN `Unidade` U ON U.ID = I.Unidade";

        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();

        try {
            while (!cursor.isAfterLast()) {
                Item i = new Item();

                i.setID(cursor.getInt(0));
                i.setNome(cursor.getString(1));
                i.setQuantidade(cursor.getInt(2));
                i.setComprado(cursor.getInt(3));
                i.setStatus(new Status(cursor.getInt(4), cursor.getString(5)));
                i.setUnidade(new Unidade(cursor.getInt(6), cursor.getString(7), cursor.getString(8)));

                item.add(i);
                cursor.moveToNext();

                Log.i(TAG, "Obtendo itens ...");
            }

            cursor.close();
            return item;
        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage());

            cursor.close();
            return null;
        }
    }


    /**
     * Obtem todos os registros de transações dentro do banco de dados.
     *
     * @return uma lista do tipo Transaction.
     */
    public List<Item> getAllOrdened(int opc) {
        String query = null;
        List<Item> item = new ArrayList<>();

        switch (opc) {
            case R.id.nav_az:
                query = "SELECT I.ID, I.Nome, I.Quantidade, I.Comprado, S.ID AS `Status`, S.Descricao AS `Descriçao`, U.ID AS `Unidade`, U.Descricao, U.Abreviacao FROM `Item` I INNER JOIN `Status` S ON S.ID = I.Status INNER JOIN `Unidade` U ON U.ID = I.Unidade ORDER BY I.Nome ASC";
                break;
            case R.id.nav_za:
                query = "SELECT I.ID, I.Nome, I.Quantidade, I.Comprado, S.ID AS `Status`, S.Descricao AS `Descriçao`, U.ID AS `Unidade`, U.Descricao, U.Abreviacao FROM `Item` I INNER JOIN `Status` S ON S.ID = I.Status INNER JOIN `Unidade` U ON U.ID = I.Unidade ORDER BY I.NOME DESC";
                break;
            case R.id.nav_comprar:
                query = "SELECT I.ID, I.Nome, I.Quantidade, I.Comprado, S.ID AS `Status`, S.Descricao AS `Descriçao`, U.ID AS `Unidade`, U.Descricao, U.Abreviacao FROM `Item` I INNER JOIN `Status` S ON S.ID = I.Status INNER JOIN `Unidade` U ON U.ID = I.Unidade ORDER BY I.Status ASC";
                break;
            case R.id.nav_comprado:
                query = "SELECT I.ID, I.Nome, I.Quantidade, I.Comprado, S.ID AS `Status`, S.Descricao AS `Descriçao`, U.ID AS `Unidade`, U.Descricao, U.Abreviacao FROM `Item` I INNER JOIN `Status` S ON S.ID = I.Status INNER JOIN `Unidade` U ON U.ID = I.Unidade ORDER BY I.Status DESC";
                break;
            default:
                query = "SELECT I.ID, I.Nome, I.Quantidade, I.Comprado, S.ID AS `Status`, S.Descricao AS `Descriçao`, U.ID AS `Unidade`, U.Descricao, U.Abreviacao FROM `Item` I INNER JOIN `Status` S ON S.ID = I.Status INNER JOIN `Unidade` U ON U.ID = I.Unidade";
                break;
        }

        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();

        try {
            while (!cursor.isAfterLast()) {
                Item i = new Item();

                i.setID(cursor.getInt(0));
                i.setNome(cursor.getString(1));
                i.setQuantidade(cursor.getInt(2));
                i.setComprado(cursor.getInt(3));
                i.setStatus(new Status(cursor.getInt(4), cursor.getString(5)));
                i.setUnidade(new Unidade(cursor.getInt(6), cursor.getString(7), cursor.getString(8)));

                item.add(i);
                cursor.moveToNext();

                Log.i(TAG, "Obtendo itens ...");
            }

            cursor.close();
            return item;
        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage());

            cursor.close();
            return null;
        }
    }
}
