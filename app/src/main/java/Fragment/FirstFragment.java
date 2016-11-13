package Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

import MyUtils.LogUtils;
import butterknife.Bind;
import butterknife.ButterKnife;
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
    @Bind(R.id.tvHome)
    TextView tvHome;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_first,container,false);
        ButterKnife.bind(this,view);
        initUI();
        spiderWebData();
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
//        rvHome.setItemAnimator(new DefaultItemAnimator());
//        /* @描述 rvNoteList */
//        //rvHome.setAdapter(adapter);
//        rvHome.setHasFixedSize(true);
    }

    /*
   ***************************************************
   * @方法 爬虫上网爬
   * @参数
   * @返回值
   */
    private void spiderWebData() {

            //从一个URL加载一个Document对象。
        Document doc = null;
        try {
            doc = Jsoup.connect("http://www.qiushibaike.com/").get();
        } catch (IOException e) {
            e.printStackTrace();
        }
//            //选择“美食天下”所在节点
//            Elements elements = doc.select("div.cate_list");
//            //打印 <a>标签里面的title
            LogUtils.Log(doc.toString());
            tvHome.setText(doc.toString());

    }
}
