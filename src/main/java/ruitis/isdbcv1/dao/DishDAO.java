package ruitis.isdbcv1.dao;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import ruitis.isdbcv1.models.Dish;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class DishDAO implements DAO<Dish, Long> {
    private static final String SQL_SELECT_ALL = "select * from dish";

    private static final String SQL_SELECT_BY_ID = "select * from dish where id = :id";

    private static final String SQL_SELECT_BY_NAME = "select * from dish where name = :name";

    private static final RowMapper<Dish> rowMapper = (row, rowNumber) -> Dish.builder()
            .id(row.getObject("id", Long.class))
            .name(row.getString("name"))
            .price(row.getObject("price", Integer.class))
            .build();

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public DishDAO(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public void save(Dish dish) {
        Map<String, Object> paramMap = Map.of(
                "name", dish.getName(),
                "price", dish.getPrice()
        );

        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate.getJdbcTemplate());

        Long id = insert.withTableName("dish")
                .usingGeneratedKeyColumns("id")
                .executeAndReturnKey(paramMap)
                .longValue();

        dish.setId(id);
    }

    @Override
    public List<Dish> findAll() {
        return jdbcTemplate.query(SQL_SELECT_ALL, rowMapper);
    }

    @Override
    public Optional<Dish> findById(Long id) {
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

    public Optional<Dish> findByName(String name) {
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
