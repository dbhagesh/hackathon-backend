package com.example.platform.models;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RoomRequestModel {
    private String type;
    private Date expiryTime;
}
