package com.sistema.blog.seguridad;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class JWTAuthResponseDTO {

    private String tokenDeAcceso;
    private String tipoDeToken = "Bearer";

    public JWTAuthResponseDTO(String tokenDeAcceso){
        this.tokenDeAcceso = tokenDeAcceso;
    }

}
