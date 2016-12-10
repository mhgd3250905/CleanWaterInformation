package GsonBean;

import java.util.List;

import DataBean.FenghuangBean;
import DataBean.ITHomeBean;

/**
 * Created by admin on 2016/11/26.
 */
/*
* 
* 描    述：
* 作    者：ksheng
* 时    间：2016/11/26$ 14:07$.
*/
public class FenghuangGson {
    public List<FenghuangBean> data;

    public List<FenghuangBean> getData() {
        return data;
    }

    public void setData(List<FenghuangBean> data) {
        this.data = data;
    }
}
