package ilist.gabrielrunescape.com.br.model;

import android.view.View;
import android.content.Context;
import android.util.AttributeSet;
import android.support.v4.view.ViewCompat;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;

/**
 * Realiza a verificação de rolagem do RecyclerView e implementa no FloatingActionButton como uma
 * ação de ocultar e exibir ao realizar o ato de rolagem.
 *
 * @author Valdio Veliu
 * @version 1.0
 * @since 2016-12-27
 */
public class HideOnScroll extends FloatingActionButton.Behavior {
    /**
     * Construtor com paramêtros.
     *
     * @param context Contexto da aplicação.
     * @param attrs Atributos de FloatingActionButton.
     */
    public HideOnScroll(Context context, AttributeSet attrs) {
        super();
    }

    @Override
    /**
     * Habilita o FloatingActionButton se a rolagem for positiva e desabilita se for negativa.
     *
     * @param coordinatorLayout CoordinalLayout onde se contra o FloatingActionButton.
     * @param child FloatingActionButton.
     * @param target View alvo.
     * @param dxConsumed Eixo X consumido.
     * @param dyConsumed Eixo Y consumido.
     * @param dxUnconsumed Eixo X não consumido.
     * @param dyUnconsumed Eixo Y não consumido.
     */
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);

        if (dyConsumed > 0 && child.getVisibility() == View.VISIBLE) {
            child.hide();
        } else if (dyConsumed < 0 && child.getVisibility() != View.VISIBLE) {
            child.show();
        }
    }

    @Override
    /**
     * Verifica o status ao rolar o RecyclerView.
     *
     * @param coordinatorLayout CoordinalLayout onde se contra o FloatingActionButton.
     * @param child FloatingActionButton.
     * @param directTargetChild
     * @param target View alvo.
     * @param nestedScrollAxes Posição de rolagem.
     *
     * @return Status da verificação.
     */
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton child, View directTargetChild, View target, int nestedScrollAxes) {
        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL;
    }
}
