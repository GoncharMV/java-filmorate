package ru.yandex.practicum.filmorate.exceptions;

public class InvalidFilmExeption extends RuntimeException {
    public InvalidFilmExeption(String message) {
        super(message);
    }
}
