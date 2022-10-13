package ruitis.isdbcv1.dto;

import lombok.Value;

@Value
public class SignUpForm {
    String name;
    String password1;
    String password2;
}
