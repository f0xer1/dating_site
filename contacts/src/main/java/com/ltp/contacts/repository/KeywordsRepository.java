package com.ltp.contacts.repository;

import com.ltp.contacts.pojo.Keywords;
import com.ltp.contacts.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface KeywordsRepository extends JpaRepository<Keywords, Long> {

    List<Keywords> getKeywordsByUserId(Long id);


    @Transactional
    void deleteAllByUserId(Long user_id);


    List<Keywords> findAllByKeyword(String keyword);
}
