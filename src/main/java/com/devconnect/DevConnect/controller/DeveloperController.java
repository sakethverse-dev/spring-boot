package com.devconnect.DevConnect.controller;

import com.devconnect.DevConnect.model.Developer;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class DeveloperController {

    private final Map<Long, Developer> developerStore = new ConcurrentHashMap<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    @GetMapping("/api/v1/developers")
    public List<Developer> getAllDevelopers() {
        return new ArrayList<>(developerStore.values());
    }

    @GetMapping("/api/v1/developers/{id}")
    public Developer getDeveloperById(@PathVariable Long id) {
        return developerStore.get(id);
    }

    @GetMapping("/api/v1/developer/search")
    public List<Developer> getDeveloperBySkill(@RequestParam String skill) {
        List<Developer> matchedDevelopers = new ArrayList<>();
        for (Developer dev : developerStore.values()) {
            if (dev.getSkills() != null && dev.getSkills().contains(skill)) {
                matchedDevelopers.add(dev);
            }
        }
        return matchedDevelopers;
    }

    @PostMapping("/api/v1/developers")
    public Developer createDeveloper(@RequestBody Developer developer) {
        Long newId = idCounter.getAndIncrement();
        developer.setId(newId);
        developerStore.put(newId, developer);
        return developer;
    }

}
