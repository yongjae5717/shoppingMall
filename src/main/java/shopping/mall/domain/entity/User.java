package shopping.mall.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user")
public class User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private boolean activated;

    private String email;

    private String password;

    private String username;

    private String birth;

    private String nickname;

    @ManyToMany
    @JoinTable(
            name = "user_authority",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "Authority_name")})
    private Set<Authority> authorities;

    public void setUsername(String username) {
        this.username = username;
    }

    public void updateUser(String password, String nickname) {
        this.password = password;
        this.nickname = nickname;
    }

    public void updateUser(String password) {
        this.password = password;
    }
}