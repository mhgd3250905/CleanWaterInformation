package GsonBean;

/**
 * Created by admin on 2016/11/19.
 */
/*
* 
* 描    述：
* 作    者：ksheng
* 时    间：2016/11/19$ 13:31$.
*/
public class HuiuGsonBean {

    private int result;
    private String msg;
    private String data;
    private int total_page;
    private String last_dateline;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getTotal_page() {
        return total_page;
    }

    public void setTotal_page(int total_page) {
        this.total_page = total_page;
    }

    public String getLast_dateline() {
        return last_dateline;
    }

    public void setLast_dateline(String last_dateline) {
        this.last_dateline = last_dateline;
    }
}
