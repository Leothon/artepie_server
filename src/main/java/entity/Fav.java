package entity;

public class Fav {

    private String fav_id;
    private String fav_user_id;
    private String fav_class_id;
    private String fav_time;

    public String getFav_class_id() {
        return fav_class_id;
    }

    public void setFav_class_id(String fav_class_id) {
        this.fav_class_id = fav_class_id;
    }

    public String getFav_id() {
        return fav_id;
    }

    public void setFav_id(String fav_id) {
        this.fav_id = fav_id;
    }

    public String getFav_time() {
        return fav_time;
    }

    public void setFav_time(String fav_time) {
        this.fav_time = fav_time;
    }

    public String getFav_user_id() {
        return fav_user_id;
    }

    public void setFav_user_id(String fav_user_id) {
        this.fav_user_id = fav_user_id;
    }
}
