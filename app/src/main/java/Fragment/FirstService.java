package Fragment;

import retrofit2.http.GET;
import retrofit2.http.Headers;
import rx.Observable;

/**
 * Created by admin on 2016/11/14.
 */

interface FirstService {
    @Headers({
            "User-Agent: Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.102 Safari/537.36"
    })
    @GET("http://www.ithome.com/ithome/getajaxdata.aspx?page=1&type=indexpage")
    Observable<String> getData();
}
