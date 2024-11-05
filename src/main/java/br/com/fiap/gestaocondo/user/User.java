package br.com.fiap.gestaocondo.user;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.List;
import java.util.Map;

@Entity
@Table(name = "usert")
@Data
public class User extends DefaultOAuth2User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nome", nullable = false, length = 255)
    private String nome;

    @Column(name = "email", unique = true, nullable = false, length = 255)
    private String email;

    @Column(name = "avatar", length = 255)
    private String avatar;

    @Column(name = "score", nullable = false)
    private int score = 0;
    public User(OAuth2User principal) {
        super(
                List.of(new SimpleGrantedAuthority("USER")),
                principal.getAttributes(),
                "email"
        );
        this.nome = principal.getAttribute("nome");
        this.email = principal.getAttribute("email");
        this.avatar = principal.getAttribute("avatar");

    }

    public User() {
        super(
                List.of(new SimpleGrantedAuthority("USER")),
                Map.of("email", "anonymous"),
                "email"
        );
        this.nome = this.getAttribute("nome");
        this.email = this.getAttribute("email");
        this.avatar = this.getAttribute("avatar_url");


    }

}

