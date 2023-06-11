package com.ltp.contacts.pojo;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "keywords")
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class Keywords {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "keyword")
    private String keyword;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Keywords(String keyword, User user) {
        this.keyword = keyword;
        this.user = user;
    }

    @Override
    public String toString() {
        return "Keywords{" +
                "id=" + id +
                ", keyword='" + keyword + '\'' +
                ", user=" + user +
                '}';
    }
}
