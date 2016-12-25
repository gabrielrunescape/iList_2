package ilist.gabrielrunescape.com.br.database;

import android.util.Log;
import java.io.IOException;
import android.content.Context;
import android.database.SQLException;
import ilist.gabrielrunescape.com.br.R;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * A classe extende um OpenHelper do tipo SQL para ter como função
 * controlar o fluxo de informações entre o banco de dados e a aplicação.
 *
 * Baseado em: http://www.michenux.net/android-database-sqlite-creation-upgrade-245.html
 *
 * @author Gabriel Filipe
 * @version 1.0
 * @since 2016-08-13
 */
public class CustomSQLiteOpenHelper extends SQLiteOpenHelper {
    private Context context;
    private static String CREATEFILE = "create_script";
    private static String UPGRADEFILE_PREFIX = "upgrade_v_";
    private static String TAG = CustomSQLiteOpenHelper.class.getSimpleName(); // Logging

    /**
     * Método construtor da classe.
     *
     * @param context Contexto da aplicação.
     * @param name Nome do banco de dados.
     * @param version Versão do banco de dados.
     */
    public CustomSQLiteOpenHelper(Context context, String name, int version) {
        super(context, name, null, version);
        this.context = context;
    }

    /**
     * Cria as tableas iniciais caso não encontre o banco de dados.
     *
     * @param db Banco de dados.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            Log.i(TAG, "Criando banco de dados!");
            execSqlFile(CREATEFILE, db);

            Log.i(TAG, "Banco de dados criado com sucesso. Verificando scripts ...");

            for (String sqlFile : ResourceUtils.list(R.raw.class)) {
                if (sqlFile.startsWith(UPGRADEFILE_PREFIX)) {
                    int fileVersion = Integer.parseInt(sqlFile.substring(UPGRADEFILE_PREFIX.length()));

                    if (fileVersion > db.getVersion()) {
                        execSqlFile(sqlFile, db);
                        db.setVersion(fileVersion);
                    }
                }
            }

            execSqlFile("insert_itens", db);
        } catch (RuntimeException ex) {
            Log.e(TAG, ex.getMessage());
            ex.printStackTrace();
        } catch (IOException ex) {
            Log.e(TAG, ex.getMessage());
            ex.printStackTrace();
        }
    }

    /**
     * Realiza o tratament de upgrade, caso tenha alteração nas tabelas.
     *
     * @param db Banco de dados.
     * @param oldVersion Versão atual.
     * @param newVersion Versão nova.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            String log = "Atualizando banco de dados da versão %d para versão %d";
            Log.i(TAG, String.format(log, oldVersion, newVersion));

            for (String sqlFile : ResourceUtils.list(R.raw.class)) {
                if (sqlFile.startsWith(UPGRADEFILE_PREFIX)) {
                    int fileVersion = Integer.parseInt(sqlFile.substring(UPGRADEFILE_PREFIX.length()));

                    if (fileVersion > oldVersion && fileVersion <= newVersion) {
                        execSqlFile(sqlFile, db);
                    }
                }
            }
        } catch (IOException ex) {
            throw new RuntimeException("Houve uma falha ao atualizar o banco de dados", ex);
        }
    }

    /**
     * Executa a operação de criar/atualizar banco de dados.
     *
     * @param sqlFile Nome do script.
     * @param db Banco de dados.
     * @throws android.database.SQLException Exceção caso haja algum erro.
     * @throws IOException Exceção caso haja algum erro.
     */
    protected void execSqlFile(String sqlFile, SQLiteDatabase db) throws SQLException, IOException {
        Log.i(TAG, "Executando arquivo: " + sqlFile);
        int file = SQLParser.fileToResource(sqlFile, R.raw.class);

        for(String instruction : SQLParser.parseSqlFile(file, this.context.getResources())) {
            Log.i(TAG, instruction);
            db.execSQL(instruction);
        }
    }
}