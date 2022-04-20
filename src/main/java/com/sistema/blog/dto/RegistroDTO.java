package com.sistema.blog.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RegistroDTO {

    private String nombre;
    private String username;
    private String email;
    private String password;

}
