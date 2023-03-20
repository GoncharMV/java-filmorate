package ru.yandex.practicum.filmorate.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Component
public class GenreDbStorage {

    private final JdbcTemplate jdbcTemplate;


    public GenreDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Collection<Genre> getAll() {
        String sql = "select * from genre order by genre_id";
        return jdbcTemplate.query(sql, this::rsToGenres);
    }

    public Genre getById(Long id) {
        try {
            String sql = "select * from genre where genre_id = ?";
            return jdbcTemplate.queryForObject(sql, this::rsToGenres, id);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException("Жанра нет");
        }
    }

    public void addGenre(Long filmId, Set<Genre> genres) {
        String sqlQuery = "insert into films_genres (film_id, genre_id) values (?, ?);";
        jdbcTemplate.batchUpdate(
                sqlQuery,
                genres,
                10,
                (PreparedStatement ps, Genre g) -> {
                    ps.setLong(1, filmId);
                    ps.setLong(2, g.getId());
                }
        );
    }

    public List<Genre> getFilmGenres(Long filmId) {
        String sql = "select * from films_genres " +
                "left join genre on genre.genre_id = films_genres.genre_id " +
                "where film_id = ? " +
                "order by genre.genre_id";
        return jdbcTemplate.query(sql, this::rsToGenres, filmId);
    }

    public void deleteFilmGenre(Long filmId) {
        String sql = "delete from films_genres where film_id = ?";
        jdbcTemplate.update(sql, filmId);
    }

    public Genre rsToGenres(ResultSet rs, int rowNum) throws SQLException {
        return Genre.builder()
                .id(rs.getLong("genre_id"))
                .name(rs.getString("name"))
                .build();
    }
}
