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
import java.util.TreeSet;
import java.util.stream.Collectors;

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



    @Override
    public Film createFilm(Film film) {
        Validator.validationCheck(film);
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("films")
                .usingGeneratedKeyColumns("id");
        film.setId(jdbcInsert.executeAndReturnKey(film.toMap()).longValue());
        updateFilmGenres(film);
        return film;
    }

    @Override
    public Film updateFilm(Film film) {
        Validator.validationCheck(film);
        long filmId = film.getId();
        String sqlInsert = "DELETE FROM film_genres WHERE film_id = ?";
        jdbcTemplate.update(sqlInsert,filmId);
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
        List<FilmGenre> genres = film.getGenres();
        if (genres != null) {
            film.setGenres(genres.stream().distinct().collect(Collectors.toList()));
        }
        updateFilmGenres(film);
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
    @Override
    public List<FilmGenre> getAllGenre() {
        String sqlQuery = "SELECT id AS genre_id, name FROM genres";
        return jdbcTemplate.query(sqlQuery, this::mapRowToGenre);
    }
    @Override
    public FilmGenre getGenreById(long id) {
        String sqlQuery = "SELECT id AS genre_id, name FROM genres WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(sqlQuery, this::mapRowToGenre, id);
        } catch (EmptyResultDataAccessException e){
            throw new UserNotFoundException("Такого idCounter не существует");
        }
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
        } catch (EmptyResultDataAccessException e){
            throw new UserNotFoundException("Такого idCounter не существует");
        }
    }

    private Film mapRowToFilm(ResultSet resultSet, int rowNum) throws SQLException {
        long filmId = resultSet.getLong("id");
        return Film.builder()
                .id(filmId)
                .name(resultSet.getString("name"))
                .description(resultSet.getString("description"))
                .releaseDate(resultSet.getDate("release_date").toLocalDate())
                .duration(resultSet.getLong("duration"))
                .mpa(findRatingById(resultSet.getLong("mpa")))
                .genres(getGenresByFilmId(filmId))
                .likes(getLikesByFilmId(filmId))
                .build();
    }

    private List<FilmGenre> getGenresByFilmId(long id) {
        String sqlQuery = "SELECT f.genre_id, g.name FROM film_genres f " +
                "JOIN genres g ON g.id = f.genre_id WHERE f.film_id = ?";
        List<FilmGenre> genres = jdbcTemplate.query(sqlQuery, this::mapRowToGenre, id);
        return genres.stream().distinct().collect(Collectors.toList());
    }

    private TreeSet<Long> getLikesByFilmId(long id) {
        String sqlQuery = "SELECT user_id FROM likes WHERE film_id = ?";
        List<Long> likes = jdbcTemplate.queryForList(sqlQuery, Long.class, id);
        return new TreeSet<>(likes);

    }

    private FilmGenre mapRowToGenre(ResultSet resultSet, int rowNum) throws SQLException {
        return FilmGenre.builder()
                .id(resultSet.getLong("genre_id"))
                .name(resultSet.getString("name"))
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

    private void updateFilmGenres(Film film) {
        List<FilmGenre> genres = film.getGenres();
        if (genres == null) {
            return;
        }
        //for (FilmGenre genre: new HashSet<>(genres)) {
        for (FilmGenre genre: genres) {
            String sqlInsert = "INSERT INTO film_genres (film_id, genre_id) VALUES (?, ?)";
            jdbcTemplate.update(sqlInsert, film.getId(), genre.getId());
        }
    }

}
