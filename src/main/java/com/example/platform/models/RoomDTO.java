package com.example.platform.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class RoomDTO {
    private UUID id;
    private String name;
    private String level;
    private Date timer;
    private List<QuestionModel> questions;
    private List<UsersModel> users;
}
