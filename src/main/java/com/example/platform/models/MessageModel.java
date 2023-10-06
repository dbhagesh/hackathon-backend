package com.example.platform.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "message")
public class MessageModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String message;
    private Date messageTime;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "roomId_Fk")
    @JsonBackReference
    private RoomModel roomModel;
}
