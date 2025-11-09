// src/main/java/com/mottu/mototracker/dto/MotoUpdateRequest.java
package com.mottu.mototracker.auth.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MotoUpdateRequest {
    public String placa;
    public String modelo;
}
