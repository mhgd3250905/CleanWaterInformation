package Fragment;

import GsonBean.BaijiaGsonBean;
import GsonBean.HuiuGsonBean;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by admin on 2016/11/14.
 */

interface WebService {
    /* @描述 虎嗅 */
    @Headers({
            "User-Agent: Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.102 Safari/537.36"
    })
    @FormUrlEncoded
    @POST("v2_action/article_list")
    Observable<HuiuGsonBean> getHuxiuData(@Field("page") String page);


    /* @描述 ITHome */
    @Headers({
            "User-Agent: Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.102 Safari/537.36"
    })
    @GET("http://www.ithome.com/ithome/getajaxdata.aspx")
    Observable<String> getITHomeData(@Query("page") String page, @Query("type") String type);

    /* @描述 虎嗅手机页面 */
    @Headers({
            "User-Agent: Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.102 Safari/537.36"
    })
    @FormUrlEncoded
    @POST("maction/article_list")
    Observable<HuiuGsonBean> getMobileHuxiuData(@Field("page") String page);


    /* @描述 百度百家 */
    //    http://baijia.baidu.com/ajax/labellatestarticle
    @Headers({
            "User-Agent: Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.102 Safari/537.36"
    })
    @GET("ajax/labellatestarticle")
    Observable<BaijiaGsonBean> getBaijiaData(@Query("page") String page, @Query("pagesize") String pagesize,
                                             @Query("prevarticalid") String prevarticalid,
                                             @Query("flagtogether") String flagtogether,
                                             @Query("labelid") String labelid);

    /* @描述 凤凰科技 */
    //http://itech.ifeng.com/7_3/data.shtml
    @Headers({
            "User-Agent: Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.102 Safari/537.36"
    })
    @GET("{page}/data.shtml")
    Observable<String> getFenghuangData(@Path("page") String page);
}
