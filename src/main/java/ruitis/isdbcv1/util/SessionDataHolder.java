package ruitis.isdbcv1.util;

import lombok.Data;
import ruitis.isdbcv1.models.Dish;

import java.util.HashMap;
import java.util.Map;

@Data
public class SessionDataHolder {
    private final Map<Dish, Integer> orderInfo = new HashMap<>();
}
