package dto;

import lombok.*;

@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

public class Data {

    private String id;
    private String email;
    private String first_name;
    private String last_name;
    private String avatar;
}