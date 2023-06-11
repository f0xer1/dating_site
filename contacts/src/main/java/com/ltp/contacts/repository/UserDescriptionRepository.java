package com.ltp.contacts.repository;

import com.ltp.contacts.pojo.UserDescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDescriptionRepository extends JpaRepository<UserDescription, Long> {
}
