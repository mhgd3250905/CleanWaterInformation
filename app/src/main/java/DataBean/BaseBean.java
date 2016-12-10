package DataBean;

import cn.bmob.v3.BmobObject;

/**
 * Created by admin on 2016/11/19.
 */
/*
* 
* 描    述：
* 作    者：ksheng
* 时    间：2016/11/19$ 12:32$.
*/
public class BaseBean extends BmobObject{
    protected String title;
    protected String contentURL;
    protected String imgSrc;

    public String getContentURL() {
        return contentURL;
    }

    public void setContentURL(String contentURL) {
        this.contentURL = contentURL;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
