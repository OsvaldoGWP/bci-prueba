package com.bci.prueba.model.request;

import com.bci.prueba.model.Phone;
import com.bci.prueba.model.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class PhoneRequest {

    @NotBlank
    @Schema(description = "Numero de tel.", example = "12345678")
    private String number;
    @NotBlank
    @Schema(description = "Código de la ciudad", example = "1")
    private String cityCode;
    @NotBlank
    @Schema(description = "Código del pais", example = "54")
    private String countryCode;

    public Phone toEntity(User user) {
        return Phone.builder()
                .number(number)
                .cityCode(cityCode)
                .countryCode(countryCode)
                .user(user)
                .build();
    }

}
