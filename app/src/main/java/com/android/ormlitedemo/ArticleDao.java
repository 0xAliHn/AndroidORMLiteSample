package com.android.ormlitedemo;

import com.android.ormlitedemo.bean.Article;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;


public class ArticleDao {
    private Dao<Article, Integer> articleDaoOpe;
    private DatabaseHelper helper;

    @SuppressWarnings("unchecked")
    public ArticleDao() {
        try {
            helper = DatabaseHelper.getInstance();
            articleDaoOpe = helper.getDao(Article.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void add(Article article) {
        try {
            articleDaoOpe.create(article);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @SuppressWarnings("unchecked")
    public Article getArticleWithUser(int id) {
        Article article = null;
        try {
            article = articleDaoOpe.queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return article;
    }


    public Article get(int id) {
        Article article = null;
        try {
            article = articleDaoOpe.queryForId(id);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return article;
    }


    public List<Article> listByUserId(int userId) {
        try {
            return articleDaoOpe.queryBuilder().where().eq("user_id", userId)
                    .query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
