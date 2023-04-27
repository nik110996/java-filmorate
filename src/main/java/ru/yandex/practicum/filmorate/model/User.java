package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Builder
@Data
public class User {
    @NotBlank
    private final String login;
    private String name;
    private int id;
    @Email
    private final String email;
    private final LocalDate birthday;
}
