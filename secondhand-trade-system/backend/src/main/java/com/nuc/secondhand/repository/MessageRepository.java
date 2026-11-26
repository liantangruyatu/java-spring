package com.nuc.secondhand.repository;

import com.nuc.secondhand.entity.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {
    Page<Message> findByReceiverId(Integer receiverId, Pageable pageable);
    Page<Message> findByReceiverIdAndIsRead(Integer receiverId, Integer isRead, Pageable pageable);
    Page<Message> findBySenderIdAndReceiverId(Integer senderId, Integer receiverId, Pageable pageable);
}
