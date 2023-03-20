package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.dao.GenreDbStorage;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Collection;

@RestController
@Slf4j
public class GenreController {

    private final GenreDbStorage genreDbStorage;

    public GenreController(GenreDbStorage genreDbStorage) {
        this.genreDbStorage = genreDbStorage;
    }

    @GetMapping("/genres")
    public Collection<Genre> getAll() {
        return genreDbStorage.getAll();
    }

    @GetMapping("/genres/{id}")
    public Genre get(@PathVariable Long id) {
        return genreDbStorage.getById(id);
    }

}
