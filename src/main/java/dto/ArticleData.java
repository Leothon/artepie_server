package dto;

import entity.Article;
import entity.Banner;

import java.util.ArrayList;

public class ArticleData {

    private ArrayList<Banner> banners;
    private ArrayList<Article> articles;

    public ArrayList<Article> getArticles() {
        return articles;
    }

    public void setArticles(ArrayList<Article> articles) {
        this.articles = articles;
    }

    public ArrayList<Banner> getBanners() {
        return banners;
    }

    public void setBanners(ArrayList<Banner> banners) {
        this.banners = banners;
    }
}
