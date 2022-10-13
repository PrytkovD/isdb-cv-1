package ruitis.isdbcv1.dao;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import ruitis.isdbcv1.models.Order;
import ruitis.isdbcv1.models.Worker;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class OrderDAO implements DAO<Order, Long> {
    private static final String SQL_SELECT_ALL = "select * from worker";

    private static final String SQL_SELECT_BY_ID = "select * from worker where id = :id";

    private static final RowMapper<Order> rowMapper = (row, rowNumber) -> Order.builder()
            .id(row.getObject("id", Long.class))
            .build();

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public OrderDAO(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public void save(Order order) {
        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate.getJdbcTemplate());

        Long id = insert.withTableName("worker")
                .usingGeneratedKeyColumns("id")
                .executeAndReturnKey(Map.of())
                .longValue();

        order.setId(id);
    }

    @Override
    public List<Order> findAll() {
        return jdbcTemplate.query(SQL_SELECT_ALL, rowMapper);
    }

    @Override
    public Optional<Order> findById(Long id) {
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
}
