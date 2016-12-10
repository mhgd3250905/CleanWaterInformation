package Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import DataBean.FenghuangBean;
import DataBean.FenghuangContentBean;
import DataBean.ITHomeBean;
import DataBean.ITHomeContentBean;
import DataBean.JsonFenghuang;
import DataBean.JsonITHome;
import GsonBean.FenghuangGson;
import GsonBean.ITHomeGson;
import MyUtils.LogUtils;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import rx.Subscriber;
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
public class FenghuangSpiderFragment extends BaseFragment<ITHomeBean> {
    private static final String FENGHUANG_URL = "http://itech.ifeng.com/";
    private int page = 10;


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
                .baseUrl(FENGHUANG_URL)
                .build();

        WebService service = retrofit.create(WebService.class);

        for (int i = 0; i <= page; i++) {
            service.getFenghuangData("7_" + i)
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
                    .subscribe(new Subscriber<String>() {
                        @Override
                        public void onCompleted() {
                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(String s) {
                            String jsonStr = s.substring(20, s.length() - 2);

                            //LogUtils.Log(jsonStr);
                            try {
                                JSONArray arr = new JSONArray(jsonStr);

                                List<FenghuangBean> fenghuangBeanList = new ArrayList<FenghuangBean>();

                                long num = 0;

                                for (int j = 0; j < arr.length(); j++) {
                                    JSONObject temp = (JSONObject) arr.get(j);
                                    FenghuangBean fenghuangBean = new FenghuangBean();
                                    fenghuangBean.setTitle(temp.getString("title"));
                                    //fenghuangBean.setImgSrc(temp.getString("i_thumbnail"));
                                    fenghuangBean.setContentURL(FENGHUANG_URL + temp.getString("pageUrl"));
                                    LogUtils.Log(fenghuangBean.getContentURL());

                                    // /44505643/news.shtml
                                    if (j < 1) {
                                        num = Long.parseLong(
                                                temp.getString("pageUrl").substring(1, 9));
                                    }

                                    fenghuangBeanList.add(fenghuangBean);

                                    /* @描述 如果bean本身不为空且内容链接不是空  */

                                    if (fenghuangBean != null && !TextUtils.isEmpty(fenghuangBean.getContentURL())) {

                                        String url = fenghuangBean.getContentURL();
                                        //LogUtils.Log("url  :" + url);

                                        try {

                                            Document docContent = Jsoup.connect(url)
                                                    .userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.102 Safari/537.36")
                                                    .execute().parse();

                                            Elements headEle=docContent.getElementsByTag("head");


                                            /*获取内容*/
                                            Elements contentEle = docContent.select("body")
                                                    .select("div.acTxt.wrap_w100");

                                            LogUtils.Log("头文件  ：  "+headEle.size()+"内容  ：" + contentEle.size());


                                            String contentHtml = "<!DOCTYPE HTML>\n" +
                                                    "<HTML>\n" +
                                                    "<head lang=\"en\">\n" +
                                                    headEle.toString()+
                                                    "    <title>" + fenghuangBean.getTitle() + "</title>";

                                            contentHtml += "</head>\n" +
                                                            "\n" +
                                                            "<body>" +
                                                            contentEle.toString() +
                                                            "</body>\n" +
                                                            "</HTML>";



                                            FenghuangContentBean contentBean = new FenghuangContentBean();
                                            contentBean.setKey(fenghuangBean.getContentURL());
                                            contentBean.setContent(contentHtml);

                                            contentBean.save(new SaveListener<String>() {
                                                @Override
                                                public void done(String s, BmobException e) {
                                                    if (e == null) {
                                                        LogUtils.Log("凤凰网内容爬取成功");
                                                    } else {
                                                        LogUtils.Log("凤凰网内容爬取失败"+e.toString());
                                                    }
                                                }
                                            });
                                        } catch (IOException e) {
                                            LogUtils.Log(e.toString());
                                        }
                                    }


                                }

                                FenghuangGson gsonData = new FenghuangGson();
                                gsonData.setData(fenghuangBeanList);
                                Gson gson = new Gson();
                                String gsonStr = gson.toJson(gsonData);

                                JsonFenghuang jsonFenghuang = new JsonFenghuang();
                                jsonFenghuang.setSpiderTime(String.valueOf(System.currentTimeMillis()));
                                jsonFenghuang.setJsonData(gsonStr);
                                jsonFenghuang.setNum(num);
                                num++;

                                jsonFenghuang.save(new SaveListener<String>() {
                                    @Override
                                    public void done(String s, BmobException e) {
                                        if (e == null) {
                                            LogUtils.Log("JSON外围内容爬取成功");
                                        } else {
                                            LogUtils.Log("创建数据失败：" + e.getMessage());
                                        }
                                    }
                                });

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
        }
    }


}
