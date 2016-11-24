package Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

import DataBean.HuXiuBean;
import DataBean.HuXiuContentBean;
import GsonBean.HuiuGsonBean;
import MyUtils.LogUtils;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
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
    private static final String HUXIU_URL = "https://m.huxiu.com/";
    private int page = 15;
    private String contentHtml;

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
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//新的配置
                .baseUrl(HUXIU_URL)
                .build();

        WebService service = retrofit.create(WebService.class);

        for (int i = 1; i <= page; i++) {
            service.getMobileHuxiuData(i + "")
                    .subscribeOn(Schedulers.io())
                    .doOnNext(new Action1<HuiuGsonBean>() {
                        @Override
                        public void call(HuiuGsonBean huiuGsonBean) {

                            Document doc = Jsoup.parse(huiuGsonBean.getData());
                            Elements eles_1 = doc.select("li");
                            LogUtils.Log(eles_1.size() + "");

                            for (Element ele_2 : eles_1) {

                                HuXiuBean huXiuBean = new HuXiuBean();

                                huXiuBean.setContentURL(HUXIU_URL + ele_2.select("a").get(1).attr("href"));
                                huXiuBean.setTitle(ele_2.select("a").get(1).select("div.article-info-box").select("div.article-md-title").text());
                                huXiuBean.setImgSrc(ele_2.select("a").get(1).select("img").attr("data-original"));

                                huXiuBean.save(new SaveListener<String>() {
                                    @Override
                                    public void done(String s, BmobException e) {
                                        if (e == null) {
                                            LogUtils.Log("外围爬取成功");
                                        } else {
                                            LogUtils.Log("创建数据失败：" + e.getMessage());
                                        }
                                    }
                                });

                                /* @描述 如果bean本身不为空且内容链接不是空  */

                                if (huXiuBean != null && !TextUtils.isEmpty(huXiuBean.getContentURL())) {
                                    try {

                                        String url = huXiuBean.getContentURL();
                                        Document docContent = Jsoup.connect(url)
                                                .userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.102 Safari/537.36")
                                                .execute().parse();

                                        String css = docContent.getElementsByTag("head")
                                                .select("link[rel=stylesheet]").get(0).attr("href");

                                        Elements contentEle = docContent.select("body")
                                                .select("div.htmlBox")
                                                .select("div.article-box");

                                        Elements titleEle = docContent.select("body")
                                                .select("div.htmlBox")
                                                .select("div.article-content-title-box");


                                        contentHtml = "";
                                        contentHtml = "<!DOCTYPE HTML>\n" +
                                                "<HTML>\n" +
                                                "<head lang=\"en\">\n" +
                                                "   <meta charset=\"GB2312\" >  " +
                                                "    <title>" + huXiuBean.getTitle() + "</title>";

                                        contentHtml += "<link href=\"" +
                                                HUXIU_URL +
                                                css +
                                                "\" rel=\"stylesheet\" type=\"text/css\">";

                                        contentHtml +=
                                                " <style type=\"text/css\">\n" +
                                                        "   html, body {\n" +
                                                        "    padding: 0;\n" +
                                                        "    margin: 0 auto;\n" +
                                                        "    font-size: 20px;\n" +
                                                        "    -webkit-tap-highlight-color: rgba(255, 255, 255, 0);\n" +
                                                        "    background: #fff;\n" +
                                                        "    color: #333;\n" +
                                                        "    width: 100%;\n" +
                                                        "    font-family: Helvetica;\n" +
                                                        "    -webkit-overflow-scrolling: touch;\n" +
                                                        "    max-width: 10000px !important;\n" +
                                                        "\t}\n" +
                                                        ".article-content p {\n" +
                                                        "    padding: 0px;\n" +
                                                        "    margin: 0px;\n" +
                                                        "    font-size: 45px;\n" +
                                                        "    line-height: 1.5;\n" +
                                                        "    word-wrap: break-word;\n" +
                                                        "}" +
                                                        ".neirong-shouquan {\n" +
                                                        "    font-size: 45px;\n" +
                                                        "}" +
                                                        ".article-content-title-box .title {\n" +
                                                        "    font-size: 45px;\n" +
                                                        "    font-weight: bold;\n" +
                                                        "    line-height: 1.5;\n" +
                                                        "    padding-top: 10px;\n" +
                                                        "}\n" +
                                                        ".article-author-box span {\n" +
                                                        "    margin-right: 10px;\n" +
                                                        "    color: #bbbbbb;\n" +
                                                        "    font-size: 30px;\n" +
                                                        "}\n" +
                                                        ".article-author-box .face-box img {\n" +
                                                        "    width: 45px;\n" +
                                                        "    height: 45px;\n" +
                                                        "    border-radius: 46px;\n" +
                                                        "    overflow: hidden;\n" +
                                                        "}"+
                                                        ".article-box a.rec-link {\n" +
                                                        "    display: inline-block;\n" +
                                                        "    font-size: 45px;\n" +
                                                        "    border: none;\n" +
                                                        "    color: #bbb;\n" +
                                                        "    margin-top: 25px;\n" +
                                                        "    margin-bottom: 15px;\n" +
                                                        "}"+
                                                        "</style>" +
                                                        "</head>\n" +
                                                        "\n" +
                                                        "<body>" +
                                                        titleEle.toString() +
                                                        contentEle.toString() +
                                                        "</body>\n" +
                                                        "</HTML>";

                                        HuXiuContentBean contentBean = new HuXiuContentBean();
                                        contentBean.setKey(huXiuBean.getContentURL());
                                        contentBean.setContent(contentHtml);

                                        contentBean.save(new SaveListener<String>() {
                                            @Override
                                            public void done(String s, BmobException e) {
                                                if (e == null) {
                                                    LogUtils.Log("内容爬取成功");
                                                } else {
                                                    LogUtils.Log("创建数据失败：" + e.getMessage());
                                                }
                                            }
                                        });
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }

                                }
                            }
                        }
                    })
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
                        }
                    });
        }

    }


}
