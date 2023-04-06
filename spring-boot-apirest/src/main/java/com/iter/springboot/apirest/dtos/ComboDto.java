package com.iter.springboot.apirest.dtos;

import lombok.*;

import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ComboDto implements Serializable {

    private Integer code;
    private String valor;

}
