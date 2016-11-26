package Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import DataBean.ITHomeBean;
import DataBean.JsonITHome;
import GsonBean.ITHomeGson;
import MyUtils.LogUtils;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
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
    private int page=14;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return super.onCreateView(inflater, container, savedInstanceState);

    }


    @Override
    public void spiderWebDoc() {
  /* @描述 获取Observable对象 */
        /* @描述 初始化Retrofit */
        Retrofit retrofit = new Retrofit.Builder()
                .client(new OkHttpClient())
//                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//新的配置
                .baseUrl(ITHOME_URL)
                .build();

        WebService service = retrofit.create(WebService.class);

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


                            List<ITHomeBean> itHomeBeenList=new ArrayList<ITHomeBean>();

                            long num=0;

                            for (int j = 0; j <eles_1.size(); j++) {

                                Element ele_2=eles_1.get(j);

                                ITHomeBean itHomeBean=new ITHomeBean();
                                String url_last = ele_2.select("div.block").select("h2").select("a").attr("href");

                                itHomeBean.setContentURL(url_last);
                                itHomeBean.setImgSrc(ele_2.select("a.list_thumbnail").select("img").attr("src"));
                                itHomeBean.setTitle(ele_2.select("div.block").select("h2").select("a").text());

                                if (j==0){
                                    //http://www.ithome.com/html/digi/275825.htm
                                    num= Long.parseLong(
                                            url_last.substring(url_last.length()-10,url_last.length()-4));
                                    LogUtils.Log(url_last.substring(url_last.length()-10,url_last.length()-4));
                                }

                                itHomeBeenList.add(itHomeBean);

                            }

                            ITHomeGson gsonData = new ITHomeGson();
                            gsonData.setData(itHomeBeenList);
                            Gson gson = new Gson();
                            String gsonStr = gson.toJson(gsonData);
                            JsonITHome jsonITHome = new JsonITHome();
                            jsonITHome.setSpiderTime(String.valueOf(System.currentTimeMillis()));
                            jsonITHome.setJsonData(gsonStr);
                            jsonITHome.setNum(num);
                            num++;

                            jsonITHome.save(new SaveListener<String>() {
                                @Override
                                public void done(String s, BmobException e) {
                                    if (e == null) {
                                        LogUtils.Log("JSON外围内容爬取成功");
                                    } else {
                                        LogUtils.Log("创建数据失败：" + e.getMessage());
                                    }
                                }
                            });

                        }
                    });
        }
    }


}
