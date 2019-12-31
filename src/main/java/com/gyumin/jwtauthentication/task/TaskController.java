package com.gyumin.jwtauthentication.task;

import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

	private TaskRepository taskRepository;

	public TaskController(TaskRepository taskRepository) {
		this.taskRepository = taskRepository;
	}

	@PostMapping
	public void addTask(@RequestBody Task task) {
		taskRepository.save(task);
	}

	@GetMapping
	public List<Task> getTasks() {
		return taskRepository.findAll();
	}

	@PutMapping("/{id}")
	public void editTask(@PathVariable long id, @RequestBody Task task) {
		Task existingTask = taskRepository.getOne(id);
		Assert.notNull(existingTask, "Task not found");
		existingTask.setDescription(task.getDescription());
		taskRepository.save(existingTask);
	}

	@DeleteMapping("/{id}")
	public void deleteTask(@PathVariable long id) {
		taskRepository.deleteById(id);
	}
}
