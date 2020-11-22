package com.deadlyboundaries.common.network.register.dto;

import com.deadlyboundaries.common.network.register.Timestamped;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public final class Msg implements Timestamped, Dto {

    private String msg;
    private long timestamp;

    public Msg(String msg) {
        this.msg = msg;
        this.timestamp = -1;
    }
}
