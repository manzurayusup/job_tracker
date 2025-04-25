package dev.manzurayusup.job_tracker.service;

import dev.manzurayusup.job_tracker.dto.UserDTO;
import dev.manzurayusup.job_tracker.model.User;
import dev.manzurayusup.job_tracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service class for managing user-related operations such as creation, retrieval,
 * updating, and deletion of users.
 */
@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    /**
     * Creates and saves a new user with a hashed password.
     *
     * @param username the desired username for the new user
     * @param email the user's email address
     * @param password the plain text password to be hashed and stored
     * @return the newly created {@link User} entity
     */
    public User createUser(String username, String email, String password) {
        String hashedPassword = passwordEncoder.encode(password);
        User user = new User(username, email, hashedPassword);
        return userRepository.save(user);
    }

    /**
     * Retrieves a user by their unique ID.
     *
     * @param userId the ID of the user to retrieve
     * @return an {@link Optional} containing the {@link User} if found, or an empty {@link Optional} if not
     */
    public Optional<User> findUserById(Long userId) {
        return userRepository.findById(userId);
    }


    /**
     * Updates an existing user's details, such as username, email, or password.
     * Performs uniqueness checks on the username and email.
     *
     * @param userId the ID of the user to update
     * @param userDto the DTO containing updated user information
     * @return an {@link Optional} containing the updated {@link User} if the update was successful,
     *         or an empty {@link Optional} if the user was not found
     * @throws RuntimeException if the new username or email already exists
     * @throws IllegalArgumentException if the new password matches the existing password
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
        }
        return Optional.empty();
    }

    /**
     * Deletes the user with the specified ID, if they exist.
     *
     * @param userId the ID of the user to delete
     * @return {@code true} if the user was successfully deleted, {@code false} if the user was not found
     */
    public boolean deleteUser(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            userRepository.deleteById(userId);
            return true;
        }
        return false;
    }



}
