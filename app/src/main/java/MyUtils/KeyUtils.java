package MyUtils;

/**
 * Created by admin on 2016/11/24.
 */
/*
* 
* 描    述：生成关键字
* 作    者：ksheng
* 时    间：2016/11/24$ 20:57$.
*/
public class KeyUtils {
    public static String getKeyUtils(String s){
        return System.currentTimeMillis()+s;
    }
}
