package dto;

import entity.Article;
import entity.TeaClasss;
import entity.User;

import java.util.ArrayList;

public class SearchResult {
    private ArrayList<TeaClasss> selectClasses;
    private ArrayList<QAData> qaData;
    private ArrayList<User> users;
    private ArrayList<Article> articles;

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public ArrayList<QAData> getQaData() {
        return qaData;
    }

    public void setQaData(ArrayList<QAData> qaData) {
        this.qaData = qaData;
    }

    public ArrayList<TeaClasss> getSelectClasses() {
        return selectClasses;
    }

    public void setSelectClasses(ArrayList<TeaClasss> selectClasses) {
        this.selectClasses = selectClasses;
    }

    public ArrayList<Article> getArticles() {
        return articles;
    }

    public void setArticles(ArrayList<Article> articles) {
        this.articles = articles;
    }
}
