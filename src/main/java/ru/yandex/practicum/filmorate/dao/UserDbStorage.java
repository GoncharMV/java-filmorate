package ru.yandex.practicum.filmorate.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Component
public class UserDbStorage implements UserStorage {

    private final JdbcTemplate jdbcTemplate;


    public UserDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User create(User user) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("user_fmr")
                .usingGeneratedKeyColumns("user_id");
        if (Objects.equals(user.getName(), "")) {
            user.setName(user.getLogin());
        }
        Long id = simpleJdbcInsert.executeAndReturnKey(userMap(user)).longValue();
        user.setId(id);
        return user;
    }

    @Override
    public User update(User user) {
        User checkUser = get(user.getId());
        if (checkUser != null) {
            String sqlQuery = "update user_fmr set " +
                    "email = ?, login = ?, user_name = ? , birthday = ?" +
                    "where user_id = ?";
            jdbcTemplate.update(
                    sqlQuery,
                    user.getEmail(),
                    user.getLogin(),
                    user.getName(),
                    user.getBirthday(),
                    user.getId()
            );
            return user;
        } else {
            throw new NotFoundException("Юзер не найден");
        }
    }

    @Override
    public void delete(Long id) {
        String sqlQuery = "delete from user_fmr where user_id = ?";
        jdbcTemplate.update(sqlQuery, id);
    }


    @Override
    public User get(Long userId) {
        try {
            String sqlQuery = "select * from user_fmr where user_id = ?";
            return jdbcTemplate.queryForObject(sqlQuery, this::rsToUser, userId);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException("Юзера не существует");
        }
    }

    @Override
    public Collection<User> getAll() {
        String sqlQuery = "SELECT * from user_fmr";
        return jdbcTemplate.query(sqlQuery, this::rsToUser);
    }

    private Map<String, Object> userMap(User user) {
        Map<String, Object> values = new HashMap<>();
        values.put("email", user.getEmail());
        values.put("login", user.getLogin());
        if (!Objects.equals(user.getName(), "")) {
            values.put("user_name", user.getName());
        } else {
            values.put("user_name", user.getLogin());
        }
        values.put("birthday", user.getBirthday());
        return values;
    }

    private User rsToUser(ResultSet rs, int rowNum) throws SQLException {
        return User.builder()
                .id(rs.getLong("user_id"))
                .email(rs.getString("email"))
                .login(rs.getString("login"))
                .name(rs.getString("user_name"))
                .birthday(rs.getDate("birthday").toLocalDate())
                .build();
    }
}
