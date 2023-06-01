package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Builder
@Data
public class User {
    @NotBlank
    private String login;
    private String name;
    private long id;
    @Email
    private String email;
    private LocalDate birthday;
    @JsonIgnore
    private Set<Long> friends;

    public Map<String, Object> toMap() {
        Map<String,Object> values = new HashMap<>();
        values.put("login", login);
        values.put("name", name);
        values.put("email", email);
        values.put("birthday", birthday);
        return values;
    }
}
