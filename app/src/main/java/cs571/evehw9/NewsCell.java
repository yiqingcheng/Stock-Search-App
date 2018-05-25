package cs571.evehw9;

/**
 * Created by yiqingcheng on 2017/11/24.
 */

public class NewsCell {

    private String Url;
    private String Title;
    private String Date;
    private String Author;

    public NewsCell(String url, String title, String date, String author) {
        Url = url;
        Title = title;
        Date = date;
        Author = author;
    }

    public String getUrl() {
        return Url;
    }

    public String getTitle() {
        return Title;
    }

    public String getDate() {
        return Date;
    }

    public String getAuthor() {
        return Author;
    }

}
