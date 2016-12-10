package Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import DataBean.HuXiuBean;
import GsonBean.HuiuGsonBean;
import MyUtils.LogUtils;
import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.Bmob;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
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

        //第一：默认初始化
        Bmob.initialize(getContext(), "9e16e39fa5374f446e5c928da0f83d62");

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

        WebService service = retrofit.create(WebService.class);

        /* @描述 获取Observable对象 */
        service.getHuxiuData("1")
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
                        LogUtils.Log(huiuGsonBean.getData());
                    }
                });


    }


}
