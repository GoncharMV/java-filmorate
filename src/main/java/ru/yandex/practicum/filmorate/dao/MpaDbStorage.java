package ru.yandex.practicum.filmorate.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

@Component
public class MpaDbStorage {

    private final JdbcTemplate jdbcTemplate;

    public MpaDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Collection<Mpa> getAll() {
        String sql = "select * from mpa";
        return jdbcTemplate.query(sql, this::srsToMpa);
    }

    public Mpa getById(int id) {
        try {
            String sql = "select * from mpa where mpa_id = ?";
            return jdbcTemplate.queryForObject(sql, this::srsToMpa, id);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException("id not found");
        }
    }

    private Mpa srsToMpa(ResultSet srs, int rowNumber) throws SQLException {
        int id = srs.getInt("mpa_id");
        String name = srs.getString("mpa_name");
        return Mpa.builder().id(id).name(name).build();
    }
}
