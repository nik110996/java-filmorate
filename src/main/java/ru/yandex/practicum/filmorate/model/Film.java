package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Builder
@Data
public class Film {

    private long id;
    @NotBlank
    private String name;
    @Size(min = 1, max = 200)
    private String description;
    @NotNull
    private LocalDate releaseDate;
    @Min(1)
    private long duration;
    @JsonIgnore
    private Set<Long> likes;

    public void addLike(long userId) {
        if (likes == null) {
            likes = new HashSet<>();
        }
        likes.add(userId);
    }

    public void removeLike(long userId) {
        if (likes == null) {
            likes = new HashSet<>();
        }
        likes.remove(userId);
    }
}
