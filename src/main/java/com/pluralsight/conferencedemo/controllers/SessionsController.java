package com.pluralsight.conferencedemo.controllers;

import com.pluralsight.conferencedemo.models.Session;
import com.pluralsight.conferencedemo.repositories.SessionRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/sessions")
public class SessionsController {

    @Autowired
    private SessionRepository sessionRepository;

    @GetMapping
    public List<Session> list() {
        return sessionRepository.findAll();
    }

    @GetMapping
    @RequestMapping("{id}")
    public Session get(@PathVariable Long id) {
        Optional<Session> sessionOptional = sessionRepository.findById(id);
        return sessionOptional.orElse(null);
    }

    @PostMapping
    public Session create(@RequestBody final Session session) {
        return sessionRepository.saveAndFlush(session);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Long id) {
        sessionRepository.deleteById(id);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public Session update(@PathVariable Long id, @RequestBody Session session) {
        Optional<Session> existingSessionOptional = sessionRepository.findById(id);

        // Check if a session exists for the given ID
        if (existingSessionOptional.isPresent()) {
            Session existingSession = existingSessionOptional.get();
            BeanUtils.copyProperties(session, existingSession, "session_id");
            return sessionRepository.saveAndFlush(existingSession);
        } else {
            // Handle the case where no session is found for the given ID
            // You can throw an exception or return a specific HTTP status code (e.g., 404 Not Found)
            return null;
        }
    }

}