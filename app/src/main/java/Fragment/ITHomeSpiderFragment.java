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

import DataBean.ITHomeBean;
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
public class ITHomeSpiderFragment extends BaseFragment<ITHomeBean> {
    private static final String ITHOME_URL="http://www.ithome.com/ithome/";
    private int page=1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return super.onCreateView(inflater, container, savedInstanceState);

    }


    @Override
    public void spiderWebDoc() {
  /* @描述 获取Observable对象 */
         WebService service=getWebService(ITHOME_URL);
        for (int i = 1; i <= page; i++) {
            service.getITHomeData(i+"","indexpage")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<String>() {
                        @Override
                        public void onCompleted() {
                        }

                        @Override
                        public void onError(Throwable e) {
                        }

                        @Override
                        public void onNext(String s) {
                            Document document = Jsoup.parse(s);
                            Elements eles_1=document.select("ul.ulcl").select("li");
                            LogUtils.Log(eles_1.size()+"");
                            for (Element ele_2:eles_1){
                                ITHomeBean itHomeBean=new ITHomeBean();
                                itHomeBean.setContentURL(ele_2.select("div.block").select("h2").select("a").attr("href"));
                                itHomeBean.setImgSrc(ele_2.select("a.list_thumbnail").select("img").attr("src"));
                                itHomeBean.setTitle(ele_2.select("div.block").select("h2").select("a").text());
                                itHomeBean.save(new SaveListener<String>() {
                                    @Override
                                    public void done(String s, BmobException e) {
                                        if(e==null){
                                            LogUtils.Log("爬取并保存完毕！");
                                        }else{
                                            LogUtils.Log("创建数据失败：" + e.getMessage());
                                        }
                                    }
                                });
//                                LogUtils.Log(ele_2.select("a.list_thumbnail").select("img").attr("src"));
//                                LogUtils.Log(ele_2.select("div.block").select("h2").select("a").attr("href"));
//                                LogUtils.Log(ele_2.select("div.block").select("h2").select("a").text());
                            }
                        }
                    });
        }
    }


}
