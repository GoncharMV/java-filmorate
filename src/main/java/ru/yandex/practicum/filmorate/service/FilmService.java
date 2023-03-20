package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.FilmDbStorage;
import ru.yandex.practicum.filmorate.dao.LikesDbStorage;
import ru.yandex.practicum.filmorate.dao.UserDbStorage;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.time.LocalDate;
import java.util.*;

@Service
public class FilmService {

    private final FilmStorage filmStorage;
    private final UserStorage userStorage;
    private final LikesDbStorage likesStorage;

    public FilmService(@Qualifier("filmDbStorage") FilmDbStorage filmStorage,
                       @Qualifier("userDbStorage") UserDbStorage userStorage,
                       LikesDbStorage likesDbStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
        this.likesStorage = likesDbStorage;
    }

    public Collection<Film> getFilms() {
        return filmStorage.getAll();
    }

    public Film getFilm(Long id) {
        return filmStorage.get(id);
    }

    public Collection<Film> getPopular(int count) {
        Collection<Film> collect = filmStorage.getPopular(count);
        return collect;
    }

    public void addLike(Long filmId, Long userId) {
        Film film = filmStorage.get(filmId);
        User user = userStorage.get(userId);
        filmStorage.get(film.getId()).getLikes().add(user.getId());
        likesStorage.addLike(film.getId(), user.getId());
    }

    public Film addFilm(Film film) {
        if (validation(film)) {
            return filmStorage.create(film);
        } else {
            throw new ValidationException("Film invalid");
        }
    }

    public Film update(Film film) {
        return filmStorage.update(film);
    }

    public void deleteLike(Long filmId, Long userId) {
        Film film = filmStorage.get(filmId);
        User user = userStorage.get(userId);
        if (film.getLikes().contains(user.getId())) {
            film.getLikes().remove(userId);
            likesStorage.deleteLike(userId,filmId);
        }
    }

    private boolean validation(Film film) {
        if (film.getReleaseDate().isBefore(LocalDate.of(1895,12,28))) {
            throw new ValidationException("Film release date invalid");
        }
        return true;
    }

}