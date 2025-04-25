package dev.manzurayusup.job_tracker.controller;

import dev.manzurayusup.job_tracker.dto.UserDTO;
import dev.manzurayusup.job_tracker.model.User;
import dev.manzurayusup.job_tracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * REST controller for handling user-related operations such as creating,
 * reading, updating, and deleting users.
 */
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * Creates a new user account with the given details.
     *
     * @param username the desired username
     * @param email the user's email address
     * @param password the user's password (will be hashed before saving)
     * @return a {@link ResponseEntity} containing the created {@link User}
     *         and HTTP status 201 (Created)
     */
    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestParam String username,
                           @RequestParam String email,
                           @RequestParam String password) {

        User newUser = userService.createUser(username, email, password);
        return newUser != null ? ResponseEntity.ok(newUser) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User creation failed");
    }

    /**
     * Retrieves a user's details by ID.
     *
     * @param userId the ID of the user to fetch
     * @return a {@link ResponseEntity} with the user's data and HTTP 200 if found,
     *         or HTTP 404 (Not Found) if the user doesn't exist
     */
    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable Long userId) {
        Optional<User> user = userService.findUserById(userId);
        return user.isPresent() ? ResponseEntity.ok(user) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No user with id: " + userId);
    }


    /**
     * Updates user details such as username, email, or password.
     *
     * @param userId the ID of the user to update
     * @param userDto the DTO containing fields to update (optional fields)
     * @return {@link ResponseEntity} containing the updated {@link User} and HTTP 200 if successful,
     *         HTTP 400 if validation fails (e.g., duplicate username/email or invalid password),
     *         or HTTP 404 if the user is not found
     */
    @PutMapping("update/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable Long userId, @RequestBody UserDTO userDto) {
        try {
            Optional<User> updatedUser = userService.updateUser(userId, userDto);
            return updatedUser.isPresent() ?
                    ResponseEntity.ok(updatedUser.get()) :
                    ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User " + userId + " not found.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    /**
     * Deletes a user account by ID.
     *
     * @param userId the ID of the user to delete
     * @return a {@link ResponseEntity} with a success message and HTTP 200,
     *         or HTTP 404 if the user doesn't exist
     */
    @DeleteMapping("delete/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
        return userService.deleteUser(userId) ?
                ResponseEntity.ok("User " + userId + " deleted successfully") :
                ResponseEntity.status(HttpStatus.NOT_FOUND).body("User " + userId + " not found");

    }







}
