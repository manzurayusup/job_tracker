package dev.manzurayusup.job_tracker.service;

import dev.manzurayusup.job_tracker.dto.UserDTO;
import dev.manzurayusup.job_tracker.model.User;
import dev.manzurayusup.job_tracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public User createUser(String username, String email, String password) {
        String hashedPassword = passwordEncoder.encode(password);
        User user = new User(username, email, hashedPassword);
        return userRepository.save(user);
    }

    public boolean deleteUser(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            userRepository.deleteById(userId);
            return true;
        }
        return false;
    }

    /**
     * Updated user details. Throws a runtime exception if the new username or email are already taken by
     * another user.
     * @param userId The id of the user that needs to be updated
     * @param userDto Data transfer object containing the fields that need to be updated
     * @return Optional User, throw exception if username or email violate uniqueness
     */
    public Optional<User> updateUser(Long userId, UserDTO userDto) throws RuntimeException {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User existingUser = userOptional.get();
            // throw exception if the new username or email is already taken by another user
            if (userDto.getUsername() != null && !existingUser.getUsername().equals(userDto.getUsername())) {
                if (userRepository.existsByUsername(userDto.getUsername())) {
                    throw new RuntimeException("This username is already taken.");
                }
                existingUser.setUsername(userDto.getUsername());
            }

            if (userDto.getEmail() != null && !existingUser.getEmail().equals(userDto.getEmail())) {
                if (userRepository.existsByEmail(userDto.getEmail())) {
                    throw new RuntimeException("This email is already taken.");
                }
                existingUser.setEmail(userDto.getEmail());
            }
            if (userDto.getPassword() != null) {
                if (passwordEncoder.matches(userDto.getPassword(), existingUser.getPassword())) {
                    throw new IllegalArgumentException("The password must be different than the old one.");
                }
                existingUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
            }
            // save the updated user TODO: CHECK IF THIS SUCCESSFULLY SAVES THE NEW UPDATES
            return Optional.of(userRepository.save(existingUser));
//            return Optional.of(existingUser);
        }

        return Optional.empty();

    }



}
