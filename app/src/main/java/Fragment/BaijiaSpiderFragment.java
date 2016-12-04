package Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import DataBean.BaijiaBean;
import DataBean.BaijiaContentBean;
import DataBean.HuXiuBean;
import DataBean.JsonBaijia;
import GsonBean.BaijiaGson;
import GsonBean.BaijiaGsonBean;
import MyUtils.LogUtils;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.functions.Func1;
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
public class BaijiaSpiderFragment extends BaseFragment<HuXiuBean> {
    private static final String BAIJIA_URL = "http://baijia.baidu.com/";
    private int page = 10;
    private int num = 0;
    private String prevarticalid;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void spiderWebDoc() {

        rx.Observable.just("http://baijia.baidu.com/?tn=listarticle&labelid=104")
                .observeOn(Schedulers.io())
                .map(new Func1<String, String>() {
                    @Override
                    public String call(String s) {
                        //LogUtils.Log(s);
                        return getPrevarticalid(s);
                    }
                })
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(String s) {
                        /* @描述 获取Observable对象 */
                        /* @描述 初始化Retrofit */
                        Retrofit retrofit = new Retrofit.Builder()
                                .client(new OkHttpClient())
                                .addConverterFactory(GsonConverterFactory.create())
                                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//新的配置
                                .baseUrl(BAIJIA_URL)
                                .build();

                        WebService service = retrofit.create(WebService.class);

                        for (int i = 0; i < page; i++) {

                            service.getBaijiaData(i + "", "20", prevarticalid, "1", "3")
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(Schedulers.io())
                                    .subscribe(new Subscriber<BaijiaGsonBean>() {
                                        @Override
                                        public void onCompleted() {
                                        }

                                        @Override
                                        public void onError(Throwable e) {
                                        }

                                        @Override
                                        public void onNext(BaijiaGsonBean baijiaGsonBean) {

                                            List<BaijiaGsonBean.BaijiaData.BaijiaItem> baijiaList
                                                    = baijiaGsonBean.getData().getList();

                                            List<BaijiaBean> baijiaBeanList = new ArrayList<BaijiaBean>();

                                            long num = 0;

                                            for (int j = 0; j < baijiaList.size(); j++) {
                                                BaijiaBean baijiaBean = new BaijiaBean();
                                                BaijiaGsonBean.BaijiaData.BaijiaItem baijiaItem = baijiaList.get(j);

                                                baijiaBean.setTitle(baijiaItem.getM_title());
                                                baijiaBean.setImgSrc(baijiaItem.getM_image_url());

                                                String baijiaItemId = baijiaItem.getM_display_url().substring(
                                                        baijiaItem.getM_display_url().length() - 6,
                                                        baijiaItem.getM_display_url().length()
                                                );

                                                baijiaBean.setContentURL("http://m.news.baidu.com/news?tn=bdbjbody&bjaid=" +
                                                        baijiaItemId);

                                                if (j < 1) {
                                                    num = Long.valueOf(baijiaItem.getID());
                                                }
                                                baijiaBeanList.add(baijiaBean);

                                                /* @描述 如果bean本身不为空且内容链接不是空  */

                                                if (baijiaBean != null && !TextUtils.isEmpty(baijiaBean.getContentURL())) {
                                                    try {

                                                        String url = baijiaBean.getContentURL();
                                                        LogUtils.Log("url:  " + url);

                                                        Document docContent = Jsoup.connect(url)
                                                                .userAgent("Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.75 Mobile Safari/537.36")
                                                                .execute().parse();

                                                        Elements head = docContent.getElementsByTag("head");
                                                        Elements scriptHead = head.select("script");

                                                        for (Element eleScript : scriptHead) {
                                                            head.remove(eleScript);
                                                        }

                                                        Elements scriptBody = docContent.getElementsByTag("body")
                                                                .select("script");

                                                        Elements contentEle = docContent.getElementsByTag("body")
                                                                .select("div.page-view-article");


                                                        LogUtils.Log("content_number:  " + contentEle.size());
                                                        LogUtils.Log("script_number:  " + scriptBody.size());

                                                        String contentHtml = "<!DOCTYPE HTML>\n" +
                                                                "<HTML>\n";


                                                        contentHtml += head.toString() +
                                                                "\n" +
                                                                "<body>" +
                                                                contentEle.toString() +
                                                                scriptBody.get(1).toString() +
                                                                "</body>\n" +
                                                                "</HTML>";

                                                        BaijiaContentBean contentBean = new BaijiaContentBean();
                                                        contentBean.setKey(baijiaBean.getContentURL());
                                                        contentBean.setContent(contentHtml);

                                                        contentBean.save(new SaveListener<String>() {
                                                            @Override
                                                            public void done(String s, BmobException e) {
                                                                if (e == null) {
                                                                    LogUtils.Log("内容爬取成功");
                                                                } else {
                                                                    LogUtils.Log("内容爬取失败");
                                                                }
                                                            }
                                                        });
                                                    } catch (IOException e) {
                                                        e.printStackTrace();
                                                    }

                                                }
                                            }

                                            BaijiaGson gsonData = new BaijiaGson();
                                            gsonData.setData(baijiaBeanList);
                                            Gson gson = new Gson();
                                            String gsonStr = gson.toJson(gsonData);
                                            JsonBaijia jsonBaijia = new JsonBaijia();
                                            jsonBaijia.setSpiderTime(String.valueOf(System.currentTimeMillis()));
                                            jsonBaijia.setJsonData(gsonStr);
                                            jsonBaijia.setNum(num);
                                            num++;

                                            jsonBaijia.save(new SaveListener<String>() {
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
                });


    }


    public String getPrevarticalid(String baijiaURL) {
        String prevarticalidGet = "";
        try {
            Document baijiazBaseDoc = Jsoup.connect(baijiaURL)
                    //.userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.102 Safari/537.36")
                    .get();


            Element elements = baijiazBaseDoc.select("body").select("div").get(8).select("a.feed-item").get(0);

            prevarticalidGet = elements.attr("data-nid").toString();

            return prevarticalidGet;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return prevarticalidGet;
    }


}
