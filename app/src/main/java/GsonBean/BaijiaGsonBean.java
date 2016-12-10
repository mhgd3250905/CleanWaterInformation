package GsonBean;

import java.util.List;

/**
 * Created by admin on 2016/11/19.
 */
/*
* 
* 描    述：
* 作    者：ksheng
* 时    间：2016/11/19$ 13:31$.
*/
public class BaijiaGsonBean {
    private String errno;
    private BaijiaData data;

    public String getErrno() {
        return errno;
    }

    public void setErrno(String errno) {
        this.errno = errno;
    }

    public BaijiaData getData() {
        return data;
    }

    public void setData(BaijiaData data) {
        this.data = data;
    }

    public class BaijiaData {
        private String total;
        private List<BaijiaItem> list;

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public List<BaijiaItem> getList() {
            return list;
        }

        public void setList(List<BaijiaItem> list) {
            this.list = list;
        }

        public class BaijiaItem {
            private String ID;
            private String m_create_time;
            private String m_title;
            private String m_summary;
            private String hotcount;
            private List<BaijiaID> m_label_names;
            private String m_image_url;
            private String m_display_url;
            private String m_writer_name;
            private String m_writer_url;
            private String m_writer_account_type;
            private String m_attr_exclusive;
            private String m_attr_first_pub;

            public String getID() {
                return ID;
            }

            public void setID(String ID) {
                this.ID = ID;
            }

            public String getM_create_time() {
                return m_create_time;
            }

            public void setM_create_time(String m_create_time) {
                this.m_create_time = m_create_time;
            }

            public String getM_title() {
                return m_title;
            }

            public void setM_title(String m_title) {
                this.m_title = m_title;
            }

            public String getM_summary() {
                return m_summary;
            }

            public void setM_summary(String m_summary) {
                this.m_summary = m_summary;
            }

            public String getHotcount() {
                return hotcount;
            }

            public void setHotcount(String hotcount) {
                this.hotcount = hotcount;
            }

            public List<BaijiaID> getM_label_names() {
                return m_label_names;
            }

            public void setM_label_names(List<BaijiaID> m_label_names) {
                this.m_label_names = m_label_names;
            }

            public String getM_image_url() {
                return m_image_url;
            }

            public void setM_image_url(String m_image_url) {
                this.m_image_url = m_image_url;
            }

            public String getM_display_url() {
                return m_display_url;
            }

            public void setM_display_url(String m_display_url) {
                this.m_display_url = m_display_url;
            }

            public String getM_writer_name() {
                return m_writer_name;
            }

            public void setM_writer_name(String m_writer_name) {
                this.m_writer_name = m_writer_name;
            }

            public String getM_writer_url() {
                return m_writer_url;
            }

            public void setM_writer_url(String m_writer_url) {
                this.m_writer_url = m_writer_url;
            }

            public String getM_writer_account_type() {
                return m_writer_account_type;
            }

            public void setM_writer_account_type(String m_writer_account_type) {
                this.m_writer_account_type = m_writer_account_type;
            }

            public String getM_attr_exclusive() {
                return m_attr_exclusive;
            }

            public void setM_attr_exclusive(String m_attr_exclusive) {
                this.m_attr_exclusive = m_attr_exclusive;
            }

            public String getM_attr_first_pub() {
                return m_attr_first_pub;
            }

            public void setM_attr_first_pub(String m_attr_first_pub) {
                this.m_attr_first_pub = m_attr_first_pub;
            }

            public class BaijiaID {
                private String m_id;
                private String m_name;

                public String getM_id() {
                    return m_id;
                }

                public void setM_id(String m_id) {
                    this.m_id = m_id;
                }

                public String getM_name() {
                    return m_name;
                }

                public void setM_name(String m_name) {
                    this.m_name = m_name;
                }
            }
        }
    }
}
