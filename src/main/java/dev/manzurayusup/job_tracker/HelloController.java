package dev.manzurayusup.job_tracker;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestController
public class HelloController {

    @GetMapping("/")
    public String helloWorld() {
        return "<h1>Hello from the REST API!</h1>";
    }

}
