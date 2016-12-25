package ilist.gabrielrunescape.com.br.database;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;

/**
 * Classe utilizada para obter a versão do código e nome da versão.
 * Baseado em: http://www.michenux.net/android-database-sqlite-creation-upgrade-245.html
 *
 * @author Michenux
 * @version 1.0
 * @since 2016-12-13
 */
public class VersionUtils {
    /**
     * Obtem qual é a versão atual do código aplição.
     *
     * @param context Contexto da aplicação
     * @return Número da versão.
     */
    public static int getVersionCode(Context context) {
        PackageInfo manager;

        try {
            manager = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        } catch (NameNotFoundException e) {
            throw new RuntimeException(e);
        }

        return manager.versionCode;
    }

    /**
     * Obtém qual o nome da versão atual da aplicação.
     *
     * @param context Contexto da aplicação.
     * @return Nome da versão.
     */
    public static String getVersionName(Context context) {
        PackageInfo manager = null;

        try {
            manager = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        } catch (NameNotFoundException e) {
            throw new RuntimeException(e);
        }

        return manager.versionName;
    }
}
