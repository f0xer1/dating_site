package com.ltp.contacts.pojo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "invitations")
@Getter
@Setter
@NoArgsConstructor
public class Invitation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User sender;

    @ManyToOne
    @JoinColumn(name = "recipient_id")
    private User recipient;

    @Column(name = "status")
    private String status;

    @Override
    public String toString() {
        return "Invitation{" +
                "id=" + id +
                ", sender=" + sender +
                ", recipient=" + recipient +
                ", status='" + status + '\'' +
                '}';
    }
}
