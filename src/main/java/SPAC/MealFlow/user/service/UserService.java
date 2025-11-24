package SPAC.MealFlow.user.service;

import SPAC.MealFlow.user.model.User;
import SPAC.MealFlow.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder encoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    // create user with encoded password
    public User createUser(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    // login/authenticate
    public Optional<User> authenticate(String email, String rawPassword) {

        Optional<User> userOpt = userRepository.findByEmail(email);

        if (userOpt.isEmpty()) {
            return Optional.empty();
        }

        User user = userOpt.get();

        boolean matches = encoder.matches(rawPassword, user.getPassword());

        if (!matches) {
            return Optional.empty();
        }

        return Optional.of(user);
    }

    // find user by id
    public Optional<User> findById(int id) {
        return userRepository.findById(id);
    }
}
