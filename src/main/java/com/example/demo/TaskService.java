package com.example.demo;

import org.springframework.stereotype.Service;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Optional;

@Service
public class TaskService {
    private final ConcurrentHashMap<String, String> taskStore = new ConcurrentHashMap<>();

    public void createTask(String id, String title) {
        if (id != null && title != null) {
            taskStore.put(id, title);
        }
    }

    public Optional<String> getTask(String id) {
        return Optional.ofNullable(taskStore.get(id));
    }
}
