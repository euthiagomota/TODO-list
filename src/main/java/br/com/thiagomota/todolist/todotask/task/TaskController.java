package br.com.thiagomota.todolist.todotask.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    ITaskRepository taskRepository;

    @PostMapping("")
    public TaskModel create(@RequestBody TaskModel taskModel) {
        var task = this.taskRepository.save(taskModel);
        return task;
    }
}
