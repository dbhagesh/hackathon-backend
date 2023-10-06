package com.example.platform.models;

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
    private ArrayList<UUID> questions;
    private ArrayList<UUID> users;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "roomModel")
    private List<MessageModel> messages = new ArrayList<>();

}
