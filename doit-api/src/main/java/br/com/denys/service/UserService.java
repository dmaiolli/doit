package br.com.denys.service;

import br.com.denys.domain.User;
import br.com.denys.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User save(User user) {
        user.setPassword(AuthenticationService.getPasswordEncoder().encode(user.getPassword()));
        return userRepository.save(user);
    }

}
