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

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * Creates user given username, email and password (stores only hashed password).
     * Returns success code and the newly created user upon success, or error code upon
     * failure.
     * TODO: implement validation of username, email and password
     * @param username
     * @param email
     * @param password
     * @return
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
     * Deletes the user if the user exists, returns error code if not.
     * @param userId
     * @return
     */
    @DeleteMapping("delete/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
        return userService.deleteUser(userId) ?
                ResponseEntity.ok("User deleted successfully") :
                ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");

    }

    /**
     * Update the user fields the user specifies. Returns error message if user does not exist, or if
     * username or email is already taken.
     * @param userId
     * @param userDto
     * @return
     */
    @PutMapping("update/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable Long userId, @RequestBody UserDTO userDto) {
        try {
            Optional<User> updatedUser = userService.updateUser(userId, userDto);
            return updatedUser.isPresent() ? ResponseEntity.ok(updatedUser.get()) : ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User " + userId + " not found.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }





}
