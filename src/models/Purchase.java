package models;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Purchase {

    public String id, b_id, p_id, c_id;
    public Date date;

    public String[] toArray() {
        return new String[]{b_id, c_id, p_id, new SimpleDateFormat("yyyy-MM-dd").format(date)};
    }

}
