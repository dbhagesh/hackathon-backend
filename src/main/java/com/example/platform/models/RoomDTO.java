package com.example.platform.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RoomDTO {
    private UUID id;
    private String name;
    private String level;
    private Date timer;
    private List<UUID> questions;
    private List<UUID> users;
//    private List<MessageModel> messages = new ArrayList<>();
}
