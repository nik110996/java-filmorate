package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.Validator;
import ru.yandex.practicum.filmorate.exceptions.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.properties.FilmGenre;
import ru.yandex.practicum.filmorate.model.properties.RatingMPA;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Primary
@Component("filmDbStorage")
public class FilmDbStorage implements FilmStorage {

    private final JdbcTemplate jdbcTemplate;

    public FilmDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Film> getFilms() {
        String sqlQuery = "select id, name, description, release_date, duration, mpa " +
                "from films";
        return jdbcTemplate.query(sqlQuery, this::mapRowToFilm);
    }

    private List<FilmGenre> getGenresByFilmId(long id) {
        String sqlQuery = "SELECT genre_id FROM film_genres WHERE id = ?";
        return jdbcTemplate.query(sqlQuery, this::mapRowToFilm);

    }

    private FilmGenre mapRowToGenre(ResultSet resultSet, int rowNum) throws SQLException {
        return FilmGenre.builder(resultSet)
                .id()
                .name()
                .build();
    }

    @Override
    public Film createFilm(Film film) {
        Validator.validationCheck(film);
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("films")
                .usingGeneratedKeyColumns("id");
        film.setId(jdbcInsert.executeAndReturnKey(film.toMap()).longValue());
        return film;
    }

    @Override
    public Film updateFilm(Film film) {
        Validator.validationCheck(film);
        long filmId = film.getId();
        findFilmById(filmId);
        String sqlQuery = "update films set " +
                "name = ?, description = ?, release_date = ?, duration = ?, mpa = ? " +
                "where id = ?";
        jdbcTemplate.update(sqlQuery,
                film.getName(),
        film.getDescription(),
        film.getReleaseDate(),
        film.getDuration(),
        film.getMpa().getId(),
        film.getId());
        return film;
    }

    @Override
    public Film findFilmById(long id) {
        String sqlQuery = "select id, name, description, release_date, duration, mpa " +
                "from films where id = ?";
        try {
            return jdbcTemplate.queryForObject(sqlQuery, this::mapRowToFilm, id);
        } catch (EmptyResultDataAccessException e){
            throw new UserNotFoundException("Такого idCounter не существует");
        }
    }

    @Override
    public void deleteFilms() {
        String sqlQuery = "delete from films";
        jdbcTemplate.update(sqlQuery);
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


    private Film mapRowToFilm(ResultSet resultSet, int rowNum) throws SQLException {
        return Film.builder()
                .id(resultSet.getLong("id"))
                .name(resultSet.getString("name"))
                .description(resultSet.getString("description"))
                .releaseDate(resultSet.getDate("release_date").toLocalDate())
                .duration(resultSet.getLong("duration"))
                .mpa(findRatingById(resultSet.getLong("mpa")))
                .build();
    }

    private RatingMPA mapRowToRating(ResultSet resultSet, int rowNum) throws SQLException {
        return RatingMPA.builder()
                .id(resultSet.getLong("id"))
                .name(resultSet.getString("name"))
                .build();
    }

    private RatingMPA findRatingById(long id) {
        String sqlQuery = "SELECT id, name " +
                "from ratingMPA where id = ?";
        try {
            return jdbcTemplate.queryForObject(sqlQuery, this::mapRowToRating, id);
        } catch (EmptyResultDataAccessException e){
            throw new UserNotFoundException("Такого idCounter не существует");
        }
    }

}
