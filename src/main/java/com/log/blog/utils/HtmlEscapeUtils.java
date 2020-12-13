package com.log.blog.utils;

import com.log.blog.vo.AdminVO;
import com.log.blog.vo.ArticleVO;
import com.log.blog.vo.UserVO;
import org.apache.commons.text.StringEscapeUtils;

import java.util.List;

public class HtmlEscapeUtils {
    public static String escape(String s) {
        return StringEscapeUtils.escapeHtml4(s);
    }

    public static AdminVO escape(AdminVO adminVO) {
        if (adminVO == null) return null;
//        adminVO.setAdminId(escape(adminVO.getAdminId()));
//        adminVO.setAdminEmail(escape(adminVO.getAdminEmail()));
        adminVO.setAdminName(escape(adminVO.getAdminName()));
        return adminVO;
    }

    public static UserVO escape(UserVO userVO) {
        if (userVO == null) return null;
//        userVO.setUserId(escape(userVO.getUserId()));
//        userVO.setUserEmail(escape(userVO.getUserEmail()));
        userVO.setUserName(escape(userVO.getUserName()));
        return userVO;
    }

    public static ArticleVO escape(ArticleVO articleVO) {
        if (articleVO == null) return null;
//        articleVO.setArticleId(escape(articleVO.getArticleId()));
//        articleVO.setAuthorId(escape(articleVO.getAuthorId()));
        articleVO.setTitle(escape(articleVO.getTitle()));
        articleVO.setContent(escape(articleVO.getContent()));
        return articleVO;
    }

    public static List<AdminVO> escapeAdmins(List<AdminVO> adminVOList) {
        if (adminVOList == null) return null;
        for (AdminVO adminVO : adminVOList) {
            escape(adminVO);
        }
        return adminVOList;
    }

    public static List<UserVO> escapeUsers(List<UserVO> userVOList) {
        if (userVOList == null) return null;
        for (UserVO userVO : userVOList) {
            escape(userVO);
        }
        return userVOList;
    }

    public static List<ArticleVO> escapeArticles(List<ArticleVO> articleVOList) {
        if (articleVOList == null) return null;
        for (ArticleVO article : articleVOList) {
            escape(article);
        }
        return articleVOList;
    }
}
