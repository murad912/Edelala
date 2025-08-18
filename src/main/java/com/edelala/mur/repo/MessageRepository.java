package com.edelala.mur.repo;

import com.edelala.mur.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    // Custom method to find all messages by a specific rent request ID, ordered by timestamp
    List<Message> findByRentRequestIdOrderByTimestampAsc(Long rentRequestId);
}

