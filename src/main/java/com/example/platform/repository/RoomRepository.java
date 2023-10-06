package com.example.platform.repository;

import com.example.platform.models.RoomModel;
import com.example.platform.models.UsersModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RoomRepository
        extends JpaRepository<RoomModel, UUID> {
}