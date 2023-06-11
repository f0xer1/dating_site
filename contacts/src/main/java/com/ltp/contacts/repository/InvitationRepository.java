package com.ltp.contacts.repository;

import com.ltp.contacts.pojo.Invitation;
import com.ltp.contacts.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;
import java.util.List;

@Repository
public interface InvitationRepository extends JpaRepository<Invitation, Long> {
    boolean existsBySenderAndRecipient(User sender, User recipient);

    Invitation findBySenderAndRecipient(User sender, User recipient);
    List<Invitation> getInvitationByRecipient(User recipient);
    Invitation getInvitationById(Long id);

}
