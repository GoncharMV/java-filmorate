package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.dao.MpaDbStorage;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.Collection;

@RestController
@Slf4j
public class MpaController {

    private final MpaDbStorage mpaDbStorage;

    public MpaController(MpaDbStorage mpaDbStorage) {
        this.mpaDbStorage = mpaDbStorage;
    }

    @GetMapping("/mpa")
    public Collection<Mpa> getAll() {
        return mpaDbStorage.getAll();
    }

    @GetMapping("/mpa/{id}")
    public Mpa get(@PathVariable int id) {
        return mpaDbStorage.getById(id);
    }
}
