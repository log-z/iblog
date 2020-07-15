package com.log.blog.utils;

import com.log.blog.dto.ArticleForm;
import com.log.blog.dto.NameForm;
import com.log.blog.entity.Admin;
import com.log.blog.entity.Article;
import com.log.blog.entity.User;
import org.apache.commons.text.StringEscapeUtils;

import java.util.List;

public class HtmlEscapeUtils {
    public static String escape(String s) {
        return StringEscapeUtils.escapeHtml4(s);
    }

    public static Admin escape(Admin admin) {
        if (admin == null) return null;
//        admin.setAdminId(escape(admin.getAdminId()));
//        admin.setAdminEmail(escape(admin.getAdminEmail()));
        admin.setAdminName(escape(admin.getAdminName()));
        return admin;
    }

    public static User escape(User user) {
        if (user == null) return null;
//        user.setUserId(escape(user.getUserId()));
//        user.setUserEmail(escape(user.getUserEmail()));
        user.setUserName(escape(user.getUserName()));
        return user;
    }

    public static Article escape(Article article) {
        if (article == null) return null;
//        article.setArticleId(escape(article.getArticleId()));
//        article.setAuthorId(escape(article.getAuthorId()));
        article.setTitle(escape(article.getTitle()));
        article.setContent(escape(article.getContent()));
        article.setImage(escape(article.getImage()));
        return article;
    }

    public static ArticleForm escape(ArticleForm form) {
        if (form == null) return null;
        form.setTitle(escape(form.getTitle()));
        form.setContent(escape(form.getContent()));
        return form;
    }

    public static NameForm escape(NameForm form) {
        if (form == null) return null;
        form.setName(escape(form.getName()));
        return form;
    }

    public static List<Admin> escapeAdmins(List<Admin> admins) {
        if (admins == null) return null;
        for (Admin admin : admins) {
            escape(admin);
        }
        return admins;
    }

    public static List<User> escapeUsers(List<User> users) {
        if (users == null) return null;
        for (User user : users) {
            escape(user);
        }
        return users;
    }

    public static List<Article> escapeArticles(List<Article> articles) {
        if (articles == null) return null;
        for (Article article : articles) {
            escape(article);
        }
        return articles;
    }
}
