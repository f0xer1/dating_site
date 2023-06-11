package com.ltp.contacts.service;

import com.ltp.contacts.pojo.Invitation;
import com.ltp.contacts.pojo.User;

import java.util.List;

public interface InvitationService {

     List<Invitation> getInvitationByRecipient(User recipient);

     void sendInvitation(User sender, User recipient);

     String getInvitationStatus(User sender, User recipient);
     void updateInvitationById(Long id, String value);
}
