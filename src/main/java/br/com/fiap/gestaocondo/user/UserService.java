package br.com.fiap.gestaocondo.user;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Registra um novo usuário no banco de dados se ele ainda não estiver registrado.
     *
     * @param user Usuário a ser registrado
     */
    public void register(User user) {
        if (userRepository.findByEmail(user.getEmail()).isEmpty()) {
            userRepository.save(user);
        }
    }

    /**
     * Carrega o usuário OAuth2 ao realizar o login. Se o usuário já estiver registrado, retorna o usuário existente,
     * caso contrário, cria um novo registro no banco de dados.
     *
     * @param userRequest Solicitação do usuário OAuth2
     * @return OAuth2User Usuário autenticado
     * @throws OAuth2AuthenticationException em caso de problemas durante a autenticação OAuth2
     */
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User principal = super.loadUser(userRequest);
        String email = principal.getAttribute("email");
        String nome = principal.getAttribute("name");

        // Verifica se o usuário já existe no banco de dados
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            return userOptional.get();
        }

        // Caso o usuário não esteja registrado, cria e salva um novo
        User newUser = new User();
        newUser.setEmail(email);
        newUser.setNome(nome != null ? nome : email); // Se o nome não estiver disponível, usa o email como nome

        userRepository.save(newUser);
        return newUser;
    }

    /**
     * Retorna todos os usuários registrados no banco de dados.
     *
     * @return Lista de usuários
     */
    public List<User> findAll() {
        return userRepository.findAll();
    }

    /**
     * Adiciona uma pontuação ao usuário e atualiza o registro no banco de dados.
     *
     * @param user  Usuário a ser atualizado
     * @param score Pontuação a ser adicionada
     */
    public void addScore(User user, int score) {
        user.setScore(user.getScore() + score);
        userRepository.save(user);
    }
}
