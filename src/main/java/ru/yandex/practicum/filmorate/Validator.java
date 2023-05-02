package ru.yandex.practicum.filmorate;

import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import java.time.LocalDate;

public class Validator {
    public static void validationCheck(Film film) {
        if (film.getName().isBlank()) {
            throw new ValidationException("Поле name - пустое");
        }
        if (film.getDescription().length() > 200) {
            throw new ValidationException("поле description содержит 200 или более символов");
        }
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12,28))) {
            throw new ValidationException("releaseDate дата раньше чем 28 декабря 1895 года");
        }
        if (film.getDuration() < 0) {
            throw new ValidationException("поле duration является отрицательным");
        }
    }

    public static void validationCheck(User user) {
        String email = user.getEmail();
        String login = user.getLogin();
        if (email.isBlank() || !email.contains("@")) {
            throw new ValidationException("поле email заполнено некорректно");
        }
        if (login.isBlank() || login.contains(" ")) {
            throw new ValidationException("поле login заполнено некорректно");
        }
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(login);
        }
        if (user.getBirthday().isAfter(LocalDate.now())) {
            throw new ValidationException("дата рождения не может быть в будущем");
        }
    }
}
