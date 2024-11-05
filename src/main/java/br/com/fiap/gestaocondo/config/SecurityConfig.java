package br.com.fiap.gestaocondo.config;

import br.com.fiap.gestaocondo.user.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http, UserService userService) throws Exception {
        http
                .csrf().disable() // Desativar CSRF para testes, não recomendado para produção
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/css/**", "/js/**", "/images/**").permitAll()  // Permitir acesso ao login e recursos estáticos
                        .anyRequest().authenticated() // Todas as outras requisições precisam estar autenticadas
                )
                .formLogin(form -> form
                        .loginPage("/login")  // Página customizada de login
                        .defaultSuccessUrl("/", true)  // Redireciona após login bem-sucedido
                        .failureUrl("/login?error=true")  // Página de falha no login
                        .permitAll() // Permitir acesso ao formulário de login
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")  // URL para fazer logout
                        .logoutSuccessUrl("/login?logout=true")  // Redireciona para o login após logout
                        .permitAll() // Permitir acesso ao logout
                )
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/login") // Página de login para OAuth2
                        .userInfoEndpoint(userInfo -> userInfo.userService(userService))
                        .defaultSuccessUrl("/", true) // Redireciona após login OAuth bem-sucedido
                );

        return http.build();
    }
}
