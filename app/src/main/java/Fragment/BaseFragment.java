package Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.Bmob;
import skkk.gogogo.cwinformation.R;

/**
 * Created by admin on 2016/11/18.
 */
/*
* 
* 描    述：
* 作    者：ksheng
* 时    间：2016/11/18$ 21:31$.
*/
public abstract class BaseFragment<T> extends Fragment{

    @Bind(R.id.btn_test)
    protected Button btnTest;
    @Bind(R.id.tv_test)
    protected TextView tvTest;

    protected List<T> dataBeanList=new ArrayList<T>();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //第一：默认初始化
        Bmob.initialize(getContext(), "9e16e39fa5374f446e5c928da0f83d62");
        View view = inflater.inflate(R.layout.fragment_first, container, false);
        ButterKnife.bind(this, view);
        initUI();
        return view;
    }



    /*
     ***************************************************
     * @方法 初始化UI
     * @参数
     * @返回值
     */
    private void initUI() {
        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spiderWebData();
            }
        });
    }

    /*
   ***************************************************
   * @方法 爬虫上网爬
   * @参数
   * @返回值
   */
    private void spiderWebData() {
        spiderWebDoc();
    }

    public abstract void spiderWebDoc();

}
