package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ValidatorTest {

    @Test
    void emptyFilmName() {
        Film film1 = Film.builder()
                .id(1)
                .name("")
                .description("Description1")
                .releaseDate(LocalDate.of(2001, 5, 1))
                .duration(100)
                .build();
        final ValidationException exception = assertThrows(ValidationException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                Validator.validationCheck(film1);
            }
        });
        assertEquals("Поле name - пустое", exception.getMessage());
    }

    @Test
    void invalidDescription() {
    String description = "";
    for (int i = 0; i < 200; i++) {
        description += i;
    }
        Film film1 = Film.builder()
                .id(1)
                .name("Film1")
                .description(description)
                .releaseDate(LocalDate.of(2001, 5, 1))
                .duration(100)
                .build();
        final ValidationException exception = assertThrows(ValidationException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                Validator.validationCheck(film1);
            }
        });
        assertEquals("поле description содержит 200 или более символов", exception.getMessage());
    }

    @Test
    void filmInvalidReleaseDate() {
        Film film1 = Film.builder()
                .id(1)
                .name("Film1")
                .description("Description1")
                .releaseDate(LocalDate.of(1895, 12, 27))
                .duration(100)
                .build();
        final ValidationException exception = assertThrows(ValidationException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                Validator.validationCheck(film1);
            }
        });
        assertEquals("releaseDate дата раньше чем 28 декабря 1895 года", exception.getMessage());
    }

    @Test
    void invalidDurationOfFilm() {
        Film film1 = Film.builder()
                .id(1)
                .name("Film1")
                .description("Description1")
                .releaseDate(LocalDate.of(1895, 12, 28))
                .duration(-100)
                .build();
        final ValidationException exception = assertThrows(ValidationException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                Validator.validationCheck(film1);
            }
        });
        assertEquals("поле duration является отрицательным", exception.getMessage());
    }

    @Test
    void emptyUserEmail() {
        User user1 = User.builder()
                .id(1)
                .email("")
                .login("login")
                .name("name")
                .birthday(LocalDate.of(1991, 11, 1))
                .build();
        final ValidationException exception = assertThrows(ValidationException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                Validator.validationCheck(user1);
            }
        });
        assertEquals("поле email заполнено некорректно", exception.getMessage());
    }

    @Test
    void invalidUserEmail() {
        User user1 = User.builder()
                .id(1)
                .email("asddf")
                .login("login")
                .name("name")
                .birthday(LocalDate.of(1991, 11, 1))
                .build();
        final ValidationException exception = assertThrows(ValidationException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                Validator.validationCheck(user1);
            }
        });
        assertEquals("поле email заполнено некорректно", exception.getMessage());
    }

    @Test
    void emptyUserLogin() {
        User user1 = User.builder()
                .id(1)
                .email("asddf@yandex.ru")
                .login("")
                .name("name")
                .birthday(LocalDate.of(1991, 11, 1))
                .build();
        final ValidationException exception = assertThrows(ValidationException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                Validator.validationCheck(user1);
            }
        });
        assertEquals("поле login заполнено некорректно", exception.getMessage());
    }

    @Test
    void invalidUserLogin() {
        User user1 = User.builder()
                .id(1)
                .email("asddf@yandex.ru")
                .login("asd cx")
                .name("name")
                .birthday(LocalDate.of(1991, 11, 1))
                .build();
        final ValidationException exception = assertThrows(ValidationException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                Validator.validationCheck(user1);
            }
        });
        assertEquals("поле login заполнено некорректно", exception.getMessage());
    }

    @Test
    void emptyUserName() {
        User user1 = User.builder()
                .id(1)
                .email("asddf@yandex.ru")
                .login("asdcx")
                .name("")
                .birthday(LocalDate.of(1991, 11, 1))
                .build();
        Validator.validationCheck(user1);
        assertEquals(user1.getLogin(), user1.getName(), "Поле name не эквивалентно полю login");
    }

    @Test
    void futureUserBirthday() {
        User user1 = User.builder()
                .id(1)
                .email("asddf@yandex.ru")
                .login("asdcx")
                .name("name")
                .birthday(LocalDate.of(2024, 11, 1))
                .build();
        final ValidationException exception = assertThrows(ValidationException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                Validator.validationCheck(user1);
            }
        });
        assertEquals("дата рождения не может быть в будущем", exception.getMessage());
    }
}
