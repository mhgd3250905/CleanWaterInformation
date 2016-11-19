package Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import DataBean.HuXiuBean;
import GsonBean.HuiuGsonBean;
import MyUtils.LogUtils;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by admin on 2016/11/13.
 */
/*
* 
* 描    述：
* 作    者：ksheng
* 时    间：2016/11/13$ 21:43$.
*/
public class HuxiuSpiderFragment extends BaseFragment<HuXiuBean> {
    private static final String HUXIU_URL="https://www.huxiu.com/";
    private int page=1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void spiderWebDoc() {
          /* @描述 获取Observable对象 */
        WebService service=getWebService(HUXIU_URL);
        for (int i = 1; i <= page; i++) {
            service.getHuxiuData(i+"")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<HuiuGsonBean>() {
                        @Override
                        public void onCompleted() {
                        }

                        @Override
                        public void onError(Throwable e) {
                        }

                        @Override
                        public void onNext(HuiuGsonBean huiuGsonBean) {
                            Document doc = Jsoup.parse(huiuGsonBean.getData());
                            Elements eles_1 = doc.select("div.mod-b.mod-art");
                            LogUtils.Log(eles_1.size()+"");
                            for(Element ele_2:eles_1){
                                HuXiuBean huXiuBean=new HuXiuBean();
                                huXiuBean.setContentURL(HUXIU_URL+ele_2.select("div.mod-thumb").select("a.transition").attr("href"));
                                huXiuBean.setTitle(ele_2.select("div.mod-thumb").select("a.transition").attr("title"));
                                huXiuBean.setImgSrc(ele_2.select("div.mod-thumb").select("a.transition").select("img").attr("data-original"));
                                huXiuBean.save(new SaveListener<String>() {
                                    @Override
                                    public void done(String s, BmobException e) {
                                        if(e==null){
                                            LogUtils.Log("添加数据成功，返回objectId为："+s);
                                        }else{
                                            LogUtils.Log("创建数据失败：" + e.getMessage());
                                        }
                                    }
                                });
                                LogUtils.Log(HUXIU_URL+ele_2.select("div.mod-thumb").select("a.transition").attr("href"));
                                LogUtils.Log(ele_2.select("div.mod-thumb").select("a.transition").attr("title"));
                                LogUtils.Log(ele_2.select("div.mod-thumb").select("a.transition").select("img").attr("data-original"));
                            }
                        }
                    });
        }

    }


}
