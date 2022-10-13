package ruitis.isdbcv1.models;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
public class Dish {
    private Long id;

    @NonNull
    private String name;

    @NonNull
    private Integer price;
}
