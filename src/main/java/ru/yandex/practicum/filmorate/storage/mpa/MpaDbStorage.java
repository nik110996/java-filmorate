package ru.yandex.practicum.filmorate.storage.mpa;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.properties.RatingMPA;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component("mpaDbStorage")
public class MpaDbStorage implements MpaStorage {

    private final JdbcTemplate jdbcTemplate;

    public MpaDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<RatingMPA> getRating() {
        String sqlQuery = "SELECT id, name FROM ratingMPA";
        return jdbcTemplate.query(sqlQuery, this::mapRowToRating);
    }

    @Override
    public RatingMPA getRatingById(long id) {
        String sqlQuery = "SELECT id, name FROM ratingMPA WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(sqlQuery, this::mapRowToRating, id);
        } catch (EmptyResultDataAccessException e) {
            throw new UserNotFoundException("Такого idCounter не существует");
        }
    }

    private RatingMPA mapRowToRating(ResultSet resultSet, int rowNum) throws SQLException {
        return RatingMPA.builder()
                .id(resultSet.getLong("id"))
                .name(resultSet.getString("name"))
                .build();
    }
}
