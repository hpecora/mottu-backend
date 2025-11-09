package com.mottu.mototracker.auth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CreateLocalizacaoRequest {
    private Double latitude;
    private Double longitude;
    private String observacao;
    private Long motoId;   // <<< nome exato igual ao JSON: "motoId"
}
