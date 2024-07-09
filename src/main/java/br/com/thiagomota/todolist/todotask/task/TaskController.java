package br.com.thiagomota.todolist.todotask.task;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.config.Task;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    ITaskRepository taskRepository;

    @PostMapping("/")
    public ResponseEntity create(@RequestBody TaskModel taskModel, HttpServletRequest request) {
        var idUser = request.getAttribute("idUser");
        taskModel.setIdUser((UUID) idUser);

        var currentDate = LocalDateTime.now();
        if (currentDate.isAfter(taskModel.getStart_date()) || currentDate.isAfter(taskModel.getFinish_date())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("A data de início / data de término deve ser maior do que a data atual.");
        }
        else if (taskModel.getStart_date().isAfter(taskModel.getFinish_date())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("A data de inicio deve ser menor do que a data de término.");
        }
        var task = this.taskRepository.save(taskModel);
        return ResponseEntity.status(HttpStatus.OK).body(task);
    }

    @GetMapping("/")
    public ResponseEntity list( HttpServletRequest request) {
        var idUser = request.getAttribute("idUser");
        var tasks = this.taskRepository.findByIdUser((UUID) idUser);
        return ResponseEntity.status(HttpStatus.OK).body(tasks);
    }
    @PutMapping("/{id}")
    public TaskModel update(@RequestBody TaskModel taskModel, HttpServletRequest request, @PathVariable UUID id) {
       var idUser = request.getAttribute("idUser");
       taskModel.setIdUser((UUID) idUser);
        taskModel.setId(id);
        return this.taskRepository.save(taskModel);
    }
}
