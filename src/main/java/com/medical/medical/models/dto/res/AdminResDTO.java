package com.medical.medical.models.dto.res;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class AdminResDTO extends UserResDTO{
    private int idAdmin;
    private String password;

}
