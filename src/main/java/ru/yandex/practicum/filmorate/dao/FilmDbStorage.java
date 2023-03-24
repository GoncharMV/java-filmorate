package ru.yandex.practicum.filmorate.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Component
public class FilmDbStorage implements FilmStorage {

    private final JdbcTemplate jdbcTemplate;
    private final GenreDbStorage genreDbStorage;

    public FilmDbStorage(JdbcTemplate jdbcTemplate, GenreDbStorage genreDbStorage) {
        this.jdbcTemplate = jdbcTemplate;
        this.genreDbStorage = genreDbStorage;
    }

    @Override
    public Collection<Film> getAll() {
        String sql = "select * from films left join mpa on films.mpa_id = mpa.mpa_id";
        return jdbcTemplate.query(sql, this::filmFromSrs);
    }


    @Override
    public Film get(Long id) {
        try {
            String sql = "select * from films left join mpa on films.mpa_id = mpa.mpa_id where film_id = ?";
            return jdbcTemplate.queryForObject(sql, this::filmFromSrs, id);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException("Фильма не существует");
        }
    }

    @Override
    public Film create(Film data) {
        SimpleJdbcInsert sji = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("films")
                .usingGeneratedKeyColumns("film_id");
        Map<String, Object> films = findId(data);
        Long id = sji.executeAndReturnKey(films).longValue();
        data.setId(id);
        addGenreName(data);
        genreDbStorage.addGenre(data.getId(), data.getGenres());
        return data;
    }

    @Override
    public Film update(Film data) {
        Film checkFilm = get(data.getId());
        if (checkFilm != null) {
            String sql = "update films set FILM_NAME = ?, DESCRIPTION = ?, RELEASE_DATE = ?, DURATION = ?, MPA_ID = ? " +
                    "where film_id = ?";
            jdbcTemplate.update(sql,
                    data.getName(),
                    data.getDescription(),
                    data.getReleaseDate(),
                    data.getDuration(),
                    data.getMpa().getId(),
                    data.getId()
            );
            genreDbStorage.deleteFilmGenre(data.getId());
            addGenreName(data);
            genreDbStorage.addGenre(data.getId(), data.getGenres());
            return data;

        } else {
            throw new NotFoundException("Фильм не найден");
        }
    }

    @Override
    public void delete(Long id) {
        String sql = "delete from films where film_id = ?";
        jdbcTemplate.update(sql);
    }

    @Override
    public Collection<Film> getPopular(int count) {
        String sql = "SELECT f.film_id id, f.film_name, f.description, f.release_date, f.duration, f.mpa_id, " +
                "m.mpa_name mpa_name, COUNT(l.user_id) films_users " +
                "from films f LEFT JOIN MPA m ON f.mpa_id = m.MPA_ID " +
                "LEFT JOIN films_users l on f.FILM_ID  = l.FILM_ID " +
                "GROUP BY F.FILM_ID " +
                "ORDER BY films_users DESC " +
                "LIMIT ?";
        return jdbcTemplate.query(sql, this::filmFromSrs, count);
    }

    private Film filmFromSrs(ResultSet srs, int rowNum) throws SQLException {
        Film film = Film.builder()
                .id(srs.getLong("film_id"))
                .name(srs.getString("film_name"))
                .description(srs.getString("description"))
                .releaseDate(srs.getDate("release_date").toLocalDate())
                .duration(srs.getInt("duration"))
                .mpa(Mpa.builder()
                        .id(srs.getInt("mpa_id"))
                        .name(srs.getString("mpa_name"))
                        .build())
                .genres(new LinkedHashSet<>())
                .build();
        film.getGenres().addAll(genreDbStorage.getFilmGenres(film.getId()));
        return film;
    }

    private Map<String, Object> findId(Film film) {
        Map<String, Object> films = new HashMap<>();
        films.put("film_name", film.getName());
        films.put("description", film.getDescription());
        films.put("release_date", film.getReleaseDate());
        films.put("duration", film.getDuration());
        films.put("mpa_id", film.getMpa().getId());

        return films;
    }

    private void addGenreName(Film film) {
        for (Genre g : film.getGenres()) {
            g.setName(genreDbStorage.getById(g.getId()).getName());
        }
    }
}
