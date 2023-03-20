package ru.yandex.practicum.filmorate.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class LikesDbStorage {
    private final JdbcTemplate jdbcTemplate;

    public LikesDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void addLike(Long filmId, Long userId) {
        String sql = "insert into films_users (film_id, user_id) values(?, ?)";
        jdbcTemplate.update(sql, filmId, userId);
    }

    public void deleteLike(Long userId, Long filmId) {
        String sql = "delete from films_users where film_id = ? and user_id = ?";
        jdbcTemplate.update(sql, filmId, userId);
    }
}
