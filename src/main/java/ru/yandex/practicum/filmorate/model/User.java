package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;

@Builder
@Data
public class User {

    private final String login;
    private String name;
    private int id;
    private final String email;
    private final LocalDate birthday;
}
