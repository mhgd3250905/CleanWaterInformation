package Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;

<<<<<<< HEAD
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
=======
>>>>>>> origin/master
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

<<<<<<< HEAD
import DataBean.FenghuangBean;
import DataBean.FenghuangContentBean;
import DataBean.ITHomeBean;
import DataBean.ITHomeContentBean;
import DataBean.JsonFenghuang;
import DataBean.JsonITHome;
import GsonBean.FenghuangGson;
=======
import DataBean.ITHomeBean;
import DataBean.ITHomeContentBean;
import DataBean.JsonITHome;
>>>>>>> origin/master
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
<<<<<<< HEAD
    private static final String FENGHUANG_URL = "http://itech.ifeng.com/";
    private int page = 10;

=======
    private static final String ITHOME_URL="http://www.ithome.com/ithome/";
    private int page=10;
>>>>>>> origin/master

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
<<<<<<< HEAD
                .baseUrl(FENGHUANG_URL)
=======
                .baseUrl(ITHOME_URL)
>>>>>>> origin/master
                .build();

        WebService service = retrofit.create(WebService.class);

<<<<<<< HEAD
        for (int i = 0; i <= page; i++) {
            service.getFenghuangData("7_" + i)
=======
        for (int i = 1; i <= page; i++) {
            service.getITHomeData(i+"","indexpage")
>>>>>>> origin/master
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
<<<<<<< HEAD
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
=======
                            Document document = Jsoup.parse(s);

                            Elements eles_1=document.select("ul.ulcl").select("li");

                            List<ITHomeBean> itHomeBeenList=new ArrayList<ITHomeBean>();

                            long num=0;

                            for (int j = 0;j <eles_1.size();j++) {

                                Element ele_2=eles_1.get(j);

                                ITHomeBean itHomeBean=new ITHomeBean();
                                String url_last = ele_2.select("div.block").select("h2").select("a").attr("href");

                                itHomeBean.setContentURL(url_last);
                                itHomeBean.setImgSrc(ele_2.select("a.list_thumbnail").select("img").attr("src"));
                                itHomeBean.setTitle(ele_2.select("div.block").select("h2").select("a").text());

                                if (j<1){
                                    //http://www.ithome.com/html/digi/275825.htm
                                    num= Long.parseLong(
                                            url_last.substring(url_last.length()-10,url_last.length()-4));
                                }

                                itHomeBeenList.add(itHomeBean);

                                /* @描述 如果bean本身不为空且内容链接不是空  */

                                if (itHomeBean != null && !TextUtils.isEmpty(itHomeBean.getContentURL())) {

                                    String url = itHomeBean.getContentURL();
                                    //LogUtils.Log("url  :" + url);

                                    try {

                                        Document docContent = Jsoup.connect(url)
                                                .userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.102 Safari/537.36")
                                                .execute().parse();

                                        Elements eleImgs=docContent.select("img.lazy");

                                        for (Element img:eleImgs){
                                            String dataOriginal=img.attr("data-original");
                                            img.attr("src",dataOriginal);
                                        }

//                                        String css = docContent.getElementsByTag("head")
//                                                .select("link[rel=stylesheet]").get(0).attr("href");

                                        Elements contentEle = docContent.select("body")
                                                .select("div.content.fl")
                                                .select("div.post_content");

                                        Elements titleEle = docContent.select("body")
                                                .select("div.content.fl")
                                                .select("div.post_title");

                                        //LogUtils.Log("内容  ：" + contentEle.size() + "  标题  :" + titleEle.size());

                                        String contentHtml = "<!DOCTYPE HTML>\n" +
                                                "<HTML>\n" +
                                                "<head lang=\"en\">\n" +
                                                "   <meta charset=\"GB2312\" >  " +
                                                "    <title>" + itHomeBean.getTitle() + "</title>";

//                                        contentHtml += "<link href=\"" +
//                                                HUXIU_URL +
//                                                css +
//                                                "\" rel=\"stylesheet\" type=\"text/css\">";

                                        contentHtml +=
                                                " <style type=\"text/css\">\n" +
                                                        "   html, body {\n" +
                                                        "    padding: 0;\n" +
                                                        "    margin: 0 auto;\n" +
                                                        "    font-size: 45px;\n" +
                                                        "    -webkit-tap-highlight-color: rgba(255, 255, 255, 0);\n" +
                                                        "    background: #fff;\n" +
                                                        "    color: #333;\n" +
                                                        "    width: 100%;\n" +
                                                        "    font-family: Helvetica;\n" +
                                                        "    -webkit-overflow-scrolling: touch;\n" +
                                                        "    max-width: 10000px !important;\n" +
                                                        "\t}\n" +
                                                        "strong {\n" +
                                                        "  font-size: 45px;\n" +
                                                        "}"+
                                                        "img.lazy {\n" +
                                                        "    width: 100%;\n" +
                                                        "}"+
                                                        "</style>" +
                                                        "</head>\n" +
                                                        "\n" +
                                                        "<body>" +
                                                        titleEle.toString() +
                                                        contentEle.toString() +
                                                        "</body>\n" +
                                                        "</HTML>";



                                        ITHomeContentBean contentBean = new ITHomeContentBean();
                                        contentBean.setKey(itHomeBean.getContentURL());
                                        contentBean.setContent(contentHtml);

                                        contentBean.save(new SaveListener<String>() {
                                            @Override
                                            public void done(String s, BmobException e) {
                                                if (e == null) {
                                                    LogUtils.Log("ITHOME内容爬取成功");
                                                } else {
                                                    LogUtils.Log("ITHOME内容爬取失败");
                                                }
                                            }
                                        });
                                    } catch (IOException e) {
                                        LogUtils.Log(e.toString());
                                    }
                                }
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

>>>>>>> origin/master
                        }
                    });
        }
    }


}
