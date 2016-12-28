package ilist.gabrielrunescape.com.br.model;

import android.view.View;
import android.content.Context;
import android.graphics.Canvas;
import ilist.gabrielrunescape.com.br.R;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.support.v4.content.ContextCompat;

/**
 * Desenha divisórias em cada ItemView do RecyclerView como uma decoração.
 *
 * @author Henry
 * @version 1.0
 * @since 2016-12-27
 */
public class SimpleDividerItemDecoration extends RecyclerView.ItemDecoration {
    private Drawable mDivider;

    /**
     * Construtor com paramẽtros.
     *
     * @param context Contexto atual da aplicação.
     */
    public SimpleDividerItemDecoration(Context context) {
        mDivider = ContextCompat.getDrawable(context, R.drawable.item_background);
    }

    @Override
    /**
     * Desenha e define as divosórias em cada um dos ItemView.
     *
     * @param c Valor a ser desenhado.
     * @param parent RecyclerView a ser aplicada a mudança.
     * @param state Estado a RecyclerView.
     */
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();

        int childCount = parent.getChildCount();

        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);

            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

            int top = child.getBottom() + params.bottomMargin;
            int bottom = top + mDivider.getIntrinsicHeight();

            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }
}