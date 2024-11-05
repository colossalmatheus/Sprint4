package br.com.fiap.gestaocondo.auth;

import br.com.fiap.gestaocondo.user.User;
import br.com.fiap.gestaocondo.user.UserRepository;
import br.com.fiap.gestaocondo.user.UserService;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

@Component
public class LoginListener implements ApplicationListener<AuthenticationSuccessEvent> {

    private final UserService userService;
    private final UserRepository userRepository;

    public LoginListener(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event) {
        // Verifica se o 'principal' é uma instância de 'OAuth2User'
        if (event.getAuthentication().getPrincipal() instanceof OAuth2User) {
            OAuth2User principal = (OAuth2User) event.getAuthentication().getPrincipal();

            // Extrai atributos necessários do OAuth2User
            String nome = principal.getAttribute("name");
            String email = principal.getAttribute("email");

            // Verifica se o email está presente (necessário para identificação única do usuário)
            if (email != null) {
                // Verifica se o usuário já está registrado para evitar duplicação
                if (userRepository.findByEmail(email).isEmpty()) {
                    // Cria um novo objeto User a partir das informações do principal
                    User user = new User();
                    user.setNome(nome != null ? nome : email); // Caso 'nome' não esteja disponível, usa o email como nome
                    user.setEmail(email);

                    // Registra o novo usuário
                    userService.register(user);
                }
            }
        }
    }
}
