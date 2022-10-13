package ruitis.isdbcv1.dao;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import ruitis.isdbcv1.models.Worker;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class WorkderDAO implements DAO<Worker, Long> {
    private static final String SQL_SELECT_ALL = "select * from worker";

    private static final String SQL_SELECT_BY_ID = "select * from worker where id = :id";

    private static final String SQL_SELECT_BY_NAME = "select * from worker where name = :name";

    private static final RowMapper<Worker> rowMapper = (row, rowNumber) -> Worker.builder()
            .id(row.getObject("id", Long.class))
            .name(row.getString("name"))
            .password(row.getString("password"))
            .build();

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public WorkderDAO(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public void save(Worker worker) {
        Map<String, Object> paramMap = Map.of(
                "name", worker.getName(),
                "count", worker.getPassword()
        );

        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate.getJdbcTemplate());

        Long id = insert.withTableName("worker")
                .usingGeneratedKeyColumns("id")
                .executeAndReturnKey(paramMap)
                .longValue();

        worker.setId(id);
    }

    @Override
    public List<Worker> findAll() {
        return jdbcTemplate.query(SQL_SELECT_ALL, rowMapper);
    }

    @Override
    public Optional<Worker> findById(Long id) {
        try {
            return Optional.ofNullable(
                    jdbcTemplate.queryForObject(
                            SQL_SELECT_BY_ID,
                            Map.of("id", id),
                            rowMapper
                    )
            );
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    public Optional<Worker> findByName(String name) {
        try {
            return Optional.ofNullable(
                    jdbcTemplate.queryForObject(
                            SQL_SELECT_BY_NAME,
                            Map.of("name", name),
                            rowMapper
                    )
            );
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }
}
