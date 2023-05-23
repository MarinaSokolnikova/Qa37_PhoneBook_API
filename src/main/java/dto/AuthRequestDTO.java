package dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString

public class AuthRequestDTO {
    private String username;
    private String password;

}
