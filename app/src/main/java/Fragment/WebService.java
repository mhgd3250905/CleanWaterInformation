package Fragment;

import GsonBean.HuiuGsonBean;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by admin on 2016/11/14.
 */

interface WebService {
    @Headers({
            "User-Agent: Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.102 Safari/537.36"
    })
    @FormUrlEncoded
    @POST("v2_action/article_list")
    Observable<HuiuGsonBean> getHuxiuData(@Field("page") String page);



    @Headers({
            "User-Agent: Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.102 Safari/537.36"
    })
    @GET("http://www.ithome.com/ithome/getajaxdata.aspx")
    Observable<String> getITHomeData(@Query("page") String page,@Query("type") String type);
}
