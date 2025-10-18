package org;

import org.dto.UserDto;
import org.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
@RestController
@RequestMapping("/api/users")
@Validated
public class UserController {
    private final UserService userService;


    @Autowired
    public UserController(UserService userService) { this.userService = userService; }


    @PostMapping
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto dto, Principal p) {
        String actor = p == null ? "anon" : p.getName();
        return ResponseEntity.ok(userService.createUser(dto, actor));
    }
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUser(id));
    }
    @PutMapping("/{id}")
    public ResponseEntity<UserDto> update(@PathVariable Long id, @Valid @RequestBody UserDto dto, Principal p) {
        String actor = p == null ? "anon" : p.getName();
        return ResponseEntity.ok(userService.updateUser(id, dto, actor));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, Principal p) {
        String actor = p == null ? "anonymous" : p.getName();
        userService.deleteUser(id, actor);
        return ResponseEntity.noContent().build();
    }
}
