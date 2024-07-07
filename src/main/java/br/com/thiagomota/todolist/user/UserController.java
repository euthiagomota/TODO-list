package br.com.thiagomota.todolist.user;

import at.favre.lib.crypto.bcrypt.BCrypt;
import jakarta.persistence.Id;
import org.apache.catalina.User;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.yaml.snakeyaml.events.Event;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private IUserRepository userRepository;

    @PostMapping("/")
    public ResponseEntity create(@RequestBody UserModel userModel) {

       var user = this.userRepository.findByUsername(userModel.getUsername());
        if (user != null) {
            System.out.println("Usuário já existe!");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário já existente.");
        }
        var passwordHashred = BCrypt.withDefaults()
                .hashToString(12, userModel.getPassword().toCharArray());

        userModel.setPassword(passwordHashred);

        var userCreated = this.userRepository.save(userModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(userCreated);
    }

    @GetMapping("/")
    public ResponseEntity findAllUsers() {
        var users = this.userRepository.findAll();
        System.out.println(users);
        return  ResponseEntity.status(200).body(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity findOneUser(@PathVariable UUID id) {
      UserModel user = this.userRepository.findById(id).orElse(null);
        if (user != null) {
            return ResponseEntity.status(HttpStatus.OK).body(user);
        }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario não encontrado.");

    }
}
