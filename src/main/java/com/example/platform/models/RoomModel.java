package com.example.platform.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "room")
public class RoomModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String name;
    private String level;
    private Date timer;

    @ElementCollection
    private List<UUID> questions;

    @ElementCollection
    private List<UUID> users;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "roomModel")
    private List<MessageModel> messages = new ArrayList<>();

}
