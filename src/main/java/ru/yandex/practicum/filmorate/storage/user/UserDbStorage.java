package ru.yandex.practicum.filmorate.storage.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.Validator;
import ru.yandex.practicum.filmorate.exceptions.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Primary
@Component("userDbStorage")
@Slf4j
public class UserDbStorage implements UserStorage {

    private final JdbcTemplate jdbcTemplate;

    public UserDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<User> getUsers() {
        String sqlQuery = "select id, login, name, birthday, email " +
                "from users";
        return jdbcTemplate.query(sqlQuery, this::mapRowToUser);
    }

    @Override
    public User createUser(User user)  {
        Validator.validationCheck(user);
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");
        user.setId(jdbcInsert.executeAndReturnKey(user.toMap()).longValue());
        return user;
    }

    @Override
    public User updateUser(User user) {
        Validator.validationCheck(user);
        long userId = user.getId();
        findUserById(userId);
        String sqlQuery = "update users set " +
                "login = ?, name = ?, birthday = ?, email = ? " +
                "where id = ?";
        jdbcTemplate.update(sqlQuery,
                user.getLogin(),
                user.getName(),
                user.getBirthday(),
                user.getEmail(),
                user.getId());
        return user;
    }

    @Override
    public User findUserById(long id) {
        String sqlQuery = "SELECT id, login, name, birthday, email " +
                "from users where id = ?";
        try {
            return jdbcTemplate.queryForObject(sqlQuery, this::mapRowToUser, id);
        } catch (EmptyResultDataAccessException e){
        throw new UserNotFoundException("Такого idCounter не существует");
        }
    }

    @Override
    public void addFriend(long id, long friendId) {
        checkUserExisting(id);
        checkUserExisting(friendId);
        String sqlInsert = "INSERT INTO friends (user_id, friend_id) VALUES (?, ?)";
        jdbcTemplate.update(sqlInsert, id, friendId);
       // jdbcTemplate.update(sqlInsert, friendId, id);
    }

    @Override
    public void deleteFriend(long id, long friendId) {
        checkUserExisting(id);
        checkUserExisting(friendId);
        String sqlInsert = "DELETE FROM friends WHERE user_id = ? AND friend_id = ?";
        jdbcTemplate.update(sqlInsert, id, friendId);
        jdbcTemplate.update(sqlInsert, friendId, id);
    }

    @Override
    public List<User> getFriendsList(long id) {
        checkUserExisting(id);
        String sqlQuery = "SELECT u.id, u.login, u.name, u.birthday, u.email FROM users u " +
                "JOIN friends f ON f.friend_id = u.id WHERE user_id = ?";
        return jdbcTemplate.query(sqlQuery, this::mapRowToUser, id);
    }

    @Override
    public List<User> getCommonFriends(long id, long otherId) {
        checkUserExisting(id);
        checkUserExisting(otherId);
        List<User> userFriends = getFriendsList(id);
        List<User> friendFriends = getFriendsList(otherId);
        List<User> commonFriends = new ArrayList<>();
        for (User user: userFriends) {
            if (friendFriends.contains(user)) {
                commonFriends.add(user);
            }
        }
        return commonFriends;
    }

    @Override
    public void deleteUsers() {
        String sqlQuery = "delete from users";
        jdbcTemplate.update(sqlQuery);
    }

    private User mapRowToUser(ResultSet resultSet, int rowNum) throws SQLException {
        return User.builder()
                .id(resultSet.getLong("id"))
                .login(resultSet.getString("login"))
                .name(resultSet.getString("name"))
                .email(resultSet.getString("email"))
                .birthday(resultSet.getDate("birthday").toLocalDate())
                .build();
    }

    private Map<Long, Long> friendsToMap(long userId, long friendId) {
        return Map.of(userId, friendId);
    }

    private LocalDate convertToLocalDate(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    private void checkUserExisting(long id) {
        try {
            findUserById(id);
        } catch (EmptyResultDataAccessException e){
            throw new UserNotFoundException("Такого idCounter не существует");
        }
    }

}
