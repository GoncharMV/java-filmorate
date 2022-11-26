package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

public class ValidationTests {
    private User user;
    private Film film;

    @Test
    @DisplayName("User invalid login")
    void userWithNullLoginTest(){
        user = new User("email@mail.ru", null, LocalDate.of(2000, 11, 11));
        Assertions.assertTrue(ValidationTestUtils.errorMessage(user,"login should not be null or blank"));
    }

    @Test
    @DisplayName("invalid Email")
    void userWithInvalidEmailTest() {
        user = new User("email", "login", LocalDate.of(2000, 11, 11));
        Assertions.assertTrue(ValidationTestUtils.errorMessage(user,"invalid email"));
    }

    @Test
    @DisplayName("invalid birthday")
    void userWithInvalidBirthdayTest() {
        user = new User("email@mail.ru", "login", LocalDate.of(3000, 11, 11));
        Assertions.assertTrue(ValidationTestUtils.errorMessage(user,"invalid birthday"));
    }

    @Test
    @DisplayName("Film invalid name")
    void filmWithNullNameTest() {
        film = new Film(null, "film description",
                LocalDate.of(2000, 11, 11), 200);
        Assertions.assertTrue(ValidationTestUtils.errorMessage(film, "name should not be null or blank"));
    }

    @Test
    @DisplayName("Film invalid name")
    void filmWithLongDescriptionTest() {
        film = new Film("film name", "film with too long description description over two hungered " +
                "characters  film with too long description description over two hungered characters  film with too " +
                "long description description over two hun",
                LocalDate.of(2000, 11, 11), 200);
        Assertions.assertTrue(ValidationTestUtils.errorMessage(film, "description should be less then 200"));
    }

    @Test
    @DisplayName("negative duration")
    void filmWithNegativeDurationTest() {
        film = new Film("film name", "film description",
                LocalDate.of(2000, 11, 11), -200);
        Assertions.assertTrue(ValidationTestUtils.errorMessage(film, "duration should not be negative"));
    }

}
