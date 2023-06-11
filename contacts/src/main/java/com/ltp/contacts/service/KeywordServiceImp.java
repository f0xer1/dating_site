package com.ltp.contacts.service;

import com.ltp.contacts.pojo.Keywords;
import com.ltp.contacts.pojo.User;
import com.ltp.contacts.repository.KeywordsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
@Service
public class KeywordServiceImp implements KeywordService {
    private final KeywordsRepository keywordsRepository;

    @Override
    public List<User> getUsersByKeyword(String keyword) {
        List<Keywords> keywordList = keywordsRepository.findAllByKeyword(keyword);
        List<User> userList = new ArrayList<>();

        for (Keywords keywordObj : keywordList) {
            userList.add(keywordObj.getUser());
        }

        return userList;
    }



    @Override
    public String getKeywordsByUserId(Long id) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Keywords keywords : keywordsRepository.getKeywordsByUserId(id)) {
            stringBuilder.append(keywords.getKeyword()).append(" ");
        }
        String keywordsString = stringBuilder.toString().trim();
        System.out.println(keywordsString);
        return keywordsString;
    }

    @Override
    public void deleteAllKeywordsByUserId(User user) {
        keywordsRepository.deleteAllByUserId(user.getId());
    }

    @Override
    public void saveKeywordsForUser(String keywords, User user) {
        List<String> keywordsList = Arrays.stream(keywords.split("\\s+"))
                .filter(word -> !keywords.isEmpty()).toList();
        for (String key : keywordsList) {
            keywordsRepository.save(new Keywords(key, user));
        }
    }
}
