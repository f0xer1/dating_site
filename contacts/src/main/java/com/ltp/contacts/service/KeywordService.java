package com.ltp.contacts.service;

import com.ltp.contacts.pojo.User;

import java.util.List;

public interface KeywordService {
    List<User> getUsersByKeyword(String keyword);

    String getKeywordsByUserId(Long id);

    public void deleteAllKeywordsByUserId(User user);


    void saveKeywordsForUser(String keywords, User user);
}
