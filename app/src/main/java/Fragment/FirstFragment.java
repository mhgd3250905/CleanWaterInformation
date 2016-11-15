package Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import DataBean.HuXiuBean;
import MyUtils.LogUtils;
import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import skkk.gogogo.cwinformation.R;

/**
 * Created by admin on 2016/11/13.
 */
/*
* 
* 描    述：
* 作    者：ksheng
* 时    间：2016/11/13$ 21:43$.
*/
public class FirstFragment extends Fragment {
    //    @Bind(R.id.rv_first)
//    RecyclerView rvHome;
    @Bind(R.id.rv_first)
    RecyclerView rvFirst;
    @Bind(R.id.btn_test)
    Button btnTest;
    private Document doc;
    private Element frameData;
    private Elements aDatas;
    private List<HuXiuBean> huXiuBeanList = new ArrayList<HuXiuBean>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first, container, false);
        ButterKnife.bind(this, view);
        initUI();
        spiderWebData();
        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spiderWebData();
            }
        });
        return view;
    }

    /*
        ***************************************************
        * @方法 初始化UI
        * @参数
        * @返回值
        */
    private void initUI() {

//        /* @描述 设置Adapter */
//        //adapter = new NoteListAdapter(getContext(),mNoteListPresenter.getMyNotes());
//        /* @描述 布局 */
//
//        /* @描述 设置间距 */
//        //mDecoration = new SpacesItemDecoration(0);
//        /* @描述 添加间距 */
//        //rvHome.addItemDecoration(mDecoration);
//        /* @描述 设置基本动画 */
//        rvFirst.setItemAnimator(new DefaultItemAnimator());
//        /* @描述 rvNoteList */
//        //rvHome.setAdapter(adapter);
//        rvFirst.setHasFixedSize(true);
    }

    /*
   ***************************************************
   * @方法 爬虫上网爬
   * @参数
   * @返回值
   */
    private void spiderWebData() {

        /* @描述 初始化Retrofit */
        Retrofit retrofit = new Retrofit.Builder()
                .client(new OkHttpClient())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//新的配置
                .baseUrl("https://www.huxiu.com/")
                .build();

        FirstService service = retrofit.create(FirstService.class);

        /* @描述 获取Observable对象 */
        service.getData()
                .subscribeOn(Schedulers.newThread())//请求在新的线程中执行
                .observeOn(Schedulers.io())         //请求完成后在io线程中执行
                .doOnNext(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        doc = Jsoup.parse(s);
                        frameData = doc.select("div.mod-info-flow").get(0);
                        aDatas = frameData.select("div.mod-b");
                        for (Element eElement : aDatas) {
                            if (eElement.child(0).className().equals("mod-thumb")) {
                                HuXiuBean huXiuBean = new HuXiuBean();
                                huXiuBean.setTitle(eElement.select("div.mod-art").select("a.transition").select("img.lazy").attr("alt"));
                                huXiuBean.setContentURL(eElement.select("div.mod-art").select("a.transition").attr("href"));
                                huXiuBean.setImgSrc(eElement.select("div.mod-art").select("a.transition").select("img.lazy").attr("data-original"));
                                huXiuBeanList.add(huXiuBean);
                            }
                        }
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())//最后在主线程中执行
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        LogUtils.Log("结束");
                    }

                    @Override
                    public void onError(Throwable e) {
                        //请求失败
                    }

                    @Override
                    public void onNext(String s) {
                        //请求成功
                        for (int i = 0; i < huXiuBeanList.size(); i++) {
                            LogUtils.Log(huXiuBeanList.get(i).getTitle());
                            LogUtils.Log(huXiuBeanList.get(i).getContentURL());
                            LogUtils.Log(huXiuBeanList.get(i).getImgSrc());
                        }
                    }
                });


    }


}
