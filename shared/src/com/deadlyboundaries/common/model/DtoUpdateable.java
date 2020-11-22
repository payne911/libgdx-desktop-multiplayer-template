package com.deadlyboundaries.common.model;

import com.deadlyboundaries.common.network.register.dto.Dto;

public interface DtoUpdateable extends Dto {

    void updateFromDto(Dto dto);
}
