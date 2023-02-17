package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class FilmService {

    private final FilmStorage filmStorage;
    private final UserStorage userStorage;

    public FilmService(FilmStorage filmStorage, UserStorage userStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
    }

    public Collection<Film> getFilms() {
        return filmStorage.getAll();
    }

    public Film getFilm(Long id) {
        return filmStorage.get(id);
    }

    public List<Film> getPopular(int count) {
        return filmStorage.getAll().stream()
                .sorted(Comparator.comparingInt(Film::getRate))
                .limit(count)
                .collect(Collectors.toList());
    }

    public void addLike(Long id, Long userId) {
        if (userStorage.getAll().contains(userStorage.get(userId))) {
            Film film = filmStorage.get(id);
            Set<Long> likes = film.getLikes();
            likes.add(userId);
            film.setLikes(likes);
            film.setRate(likes.size());
        }
    }

    public Film addFilm(Film film) {
        if (validation(film)) {
            filmStorage.create(film);
        }
        return film;
    }

    public Film update(Film film) {
        return filmStorage.update(film);
    }

    public void deleteLike(Long id, Long userId) {
        if (userStorage.getAll().contains(userStorage.get(userId))) {
            Film film = filmStorage.get(id);
            Set<Long> likes = film.getLikes();
            likes.remove(userId);
            film.setLikes(likes);
            film.setRate(likes.size());
        }
    }

    private boolean validation(Film film) {
        if (film.getReleaseDate().isBefore(LocalDate.of(1895,12,28))) {
            throw new ValidationException("Film release date invalid");
        }
        return true;
    }

}