package ilist.gabrielrunescape.com.br.database;

import java.util.List;
import java.io.IOException;
import java.util.ArrayList;
import java.lang.reflect.Field;
import android.content.res.Resources;

/**
 * Scanneia um determinado diretório em busca de um seus arquivos.
 *
 * Baseado em: http://www.michenux.net/android-database-sqlite-creation-upgrade-245.html
 *
 * @author Michenux
 * @version 1.0
 * @since 2016-08-13
 */
public class ResourceUtils {
    /**
     * Verifica se existe o arquivo dentro de um diretório.
     *
     * @param fileName Nome do arquivo.
     * @param path Diretório onde será feito a busca.
     * @param res Resource da aplicação.
     *
     * @return Se existe ou não o arquivo.
     * @throws IOException Exceção caso haja algum erro.
     */
    public static boolean exists(String fileName, String path, Resources res) throws IOException {
        for(String currentFileName : res.getAssets().list(path)) {
            if ( currentFileName.equals(fileName)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Exibe uma lista de arquivos em um determinado diretório.
     *
     * @param path Diretório onde será realizado a operação.
     *
     * @return Todos os arquivos do diretório.
     * @throws IOException Exceção caso não haja algum erro.
     */
    public static String[] list(Class<?> path) throws IOException {
        List<String> list = new ArrayList<>();

        for (Field f : path.getFields()) {
            if (f.getGenericType() == int.class) {
                list.add(f.getName());
            }
        }

        String[] _return = new String[list.size()];
        _return = list.toArray(_return);

        return _return;
    }
}
