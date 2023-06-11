package com.ltp.contacts.pojo;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;


@Entity
@Table(name = "users")
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Length(min = 5, max = 15, message = "Розмір повинен бути від 5 до 15 символів")
    @Pattern(regexp = "^[a-z0-9]+$", message = "Поле повинно містити тільки цифри і малі латинські літери")
    @NotBlank(message = "Не може бути пустим")
    @NonNull
    @Column(name = "username")
    private String username;


    @NotBlank(message = "Не може бути пустим")
    @NonNull
    @Column(name = "password")
    private String password;

    @OneToOne()
    @JoinColumn(name = "user_description_id", referencedColumnName = "id")
    private UserDescription userDescription;


    @ManyToMany
    @JoinTable(
            name = "invitations",
            joinColumns = @JoinColumn(name = "recipient_id"),
            inverseJoinColumns = @JoinColumn(name = "sender_id")
    )
    private List<User> senders;

    @ManyToMany(mappedBy = "senders")
    private List<User> recipients;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Keywords> keywordsList;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", userDescription=" + userDescription +
                '}';
    }
}
