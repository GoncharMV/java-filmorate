package ru.yandex.practicum.filmorate.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class FriendDbStorage {

    private final JdbcTemplate jdbcTemplate;

    public FriendDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void addFriend(Long userId, Long friendId) {
        String sql = "insert into friends (user_id, friends_id) values (?, ?)";
        jdbcTemplate.update(sql, userId, friendId);
    }

    public void deleteFriend(Long userId, Long friendId) {
        String sql = "delete from friends where user_id = ? and friends_id = ?";
        jdbcTemplate.update(sql, userId, friendId);
    }

    public List<User> getFriends(Long userId) {
        String sql = "select u.* " +
                "from USER_FMR u, friends f " +
                "where u.USER_ID = f.FRIENDS_ID " +
                "AND f.USER_ID = ?";
        return jdbcTemplate.query(sql, this::rsToFriends, userId);
    }

    public List<User> commonFriends(Long userId, Long friendId) {
        String sql = "select u.* from USER_FMR u, friends f, friends o " +
                "where u.USER_ID = f.FRIENDS_ID " +
                "AND u.USER_ID = o.FRIENDS_ID " +
                "AND f.USER_ID = ? " +
                "AND o.USER_ID = ?";
        return jdbcTemplate.query(sql, this::rsToFriends, userId, friendId);
    }

    private User rsToFriends(ResultSet rs, int rowNum) throws SQLException {
        return User.builder()
                .id(rs.getLong("user_id"))
                .email(rs.getString("email"))
                .login(rs.getString("login"))
                .name(rs.getString("user_name"))
                .birthday(rs.getDate("birthday").toLocalDate())
                .build();
    }
}
