package ru.yandex.practicum.filmorate.storage.likes;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component("likesDbStorage")
public class LikesDbStorage implements LikesStorage {

    private final JdbcTemplate jdbcTemplate;

    public LikesDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addLike(long id, long userId) {
        String sqlInsert = "INSERT INTO likes (film_id, user_id) VALUES (?, ?)";
        jdbcTemplate.update(sqlInsert, id, userId);
    }

    @Override
    public void deleteLike(long id, long userId) {
        String sqlInsert = "DELETE FROM likes WHERE film_id = ? AND user_id = ?";
        jdbcTemplate.update(sqlInsert, id, userId);
    }
}
