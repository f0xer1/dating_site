package com.ltp.contacts.pojo;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "users_description")
@Getter
@Setter
@RequiredArgsConstructor
public class  UserDescription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(name = "first_name")
    private String name;
    @NonNull
    @Column(name = "description")
    private String description;
    @NonNull
    @Column(name = "last_name")
    private String surname;

    public UserDescription() {
        this.name = "Ім'я";
        this.description = "Опис";
        this.surname = "Прізвище";
    }

    @Override
    public String toString() {
        return "UserDescription{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", surname='" + surname + '\'' +
                '}';
    }
}
