package ruitis.isdbcv1.models;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
public class Worker {
    private Long id;

    @NonNull
    private String name;

    @NonNull
    private String password;
}
