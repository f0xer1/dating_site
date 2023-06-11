package com.ltp.contacts.service;

import com.ltp.contacts.pojo.Invitation;
import com.ltp.contacts.pojo.User;
import com.ltp.contacts.repository.InvitationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class InvitationServiceImp implements InvitationService {
    InvitationRepository invitationRepository;

    @Override
    public List<Invitation> getInvitationByRecipient(User recipient) {
        List<Invitation> list = invitationRepository.getInvitationByRecipient(recipient);
        list.removeIf(invitation -> invitation.getStatus().equals("accept") || invitation.getStatus().equals("rejected"));
        return list;
    }

    @Override
    public void sendInvitation(User sender, User recipient) {
        if (invitationRepository.existsBySenderAndRecipient(sender, recipient)) {
            return; // Якщо запит уже існує, повертаємося без створення нового запиту
        }
        // Створюємо об'єкт запрошення
        Invitation invitation = new Invitation();
        invitation.setSender(sender);
        invitation.setRecipient(recipient);
        invitation.setStatus("pending"); // Встановлюємо статус "pending" для нового запрошення

        // Зберігаємо запрошення у репозиторії
        invitationRepository.save(invitation);
    }

    @Override
    public String getInvitationStatus(User sender, User recipient) {
        Invitation invitation = invitationRepository.findBySenderAndRecipient(sender, recipient);
        if (invitation != null) {
            return invitation.getStatus();
        }
        return null;
    }

    @Override
    public void updateInvitationById(Long id, String value) {
        Invitation invitation = invitationRepository.getInvitationById(id);
        invitation.setStatus(value);
        invitationRepository.save(invitation);
    }

}
