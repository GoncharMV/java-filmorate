package ru.yandex.practicum.filmorate.exceptions;

public class ValidationException extends RuntimeException {
    private String message;
    public ValidationException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return message;
    }
}
