package ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import skkk.gogogo.cwinformation.R;


/**
 * Created by admin on 2016/11/21.
 */
/*
* 
* 描    述：
* 作    者：ksheng
* 时    间：2016/11/21$ 21:26$.
*/
public class BaseViewHolder extends RecyclerViewHolderBase {

    @Bind(R.id.tv_item)
    public TextView tvItem;

    public BaseViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
}
