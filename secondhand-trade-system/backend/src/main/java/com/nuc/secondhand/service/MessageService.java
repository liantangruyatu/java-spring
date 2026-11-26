package com.nuc.secondhand.service;

import com.nuc.secondhand.dto.MessageDTO;
import com.nuc.secondhand.entity.Message;
import com.nuc.secondhand.entity.Product;
import com.nuc.secondhand.entity.User;
import com.nuc.secondhand.repository.MessageRepository;
import com.nuc.secondhand.repository.ProductRepository;
import com.nuc.secondhand.repository.UserRepository;
import com.nuc.secondhand.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    public MessageDTO sendMessage(MessageDTO messageDTO) {
        Optional<User> senderOptional = userRepository.findById(messageDTO.getSenderId());
        Optional<User> receiverOptional = userRepository.findById(messageDTO.getReceiverId());

        if (!senderOptional.isPresent() || !receiverOptional.isPresent()) {
            throw new RuntimeException("发送者或接收者不存在");
        }

        Message message = Message.builder()
                .senderId(messageDTO.getSenderId())
                .receiverId(messageDTO.getReceiverId())
                .productId(messageDTO.getProductId())
                .messageType(messageDTO.getMessageType())
                .messageContent(messageDTO.getMessageContent())
                .sendTime(LocalDateTime.now())
                .isRead(0)
                .build();

        Message savedMessage = messageRepository.save(message);
        return convertToDTO(savedMessage);
    }

    public MessageDTO getMessageById(Integer messageId) {
        Optional<Message> messageOptional = messageRepository.findById(messageId);
        if (!messageOptional.isPresent()) {
            throw new RuntimeException("消息不存在");
        }
        return convertToDTO(messageOptional.get());
    }

    public Page<MessageDTO> getReceiverMessages(Integer receiverId, Pageable pageable) {
        Page<Message> messages = messageRepository.findByReceiverId(receiverId, pageable);
        return messages.map(this::convertToDTO);
    }

    public Page<MessageDTO> getUnreadMessages(Integer receiverId, Pageable pageable) {
        Page<Message> messages = messageRepository.findByReceiverIdAndIsRead(receiverId, 0, pageable);
        return messages.map(this::convertToDTO);
    }

    public Page<MessageDTO> getConversation(Integer senderId, Integer receiverId, Pageable pageable) {
        Page<Message> messages = messageRepository.findBySenderIdAndReceiverId(senderId, receiverId, pageable);
        return messages.map(this::convertToDTO);
    }

    public MessageDTO markAsRead(Integer messageId) {
        Optional<Message> messageOptional = messageRepository.findById(messageId);
        if (!messageOptional.isPresent()) {
            throw new RuntimeException("消息不存在");
        }

        Message message = messageOptional.get();
        message.setIsRead(1);
        message.setReadTime(LocalDateTime.now());
        Message updatedMessage = messageRepository.save(message);

        return convertToDTO(updatedMessage);
    }

    public void deleteMessage(Integer messageId) {
        messageRepository.deleteById(messageId);
    }

    private MessageDTO convertToDTO(Message message) {
        User sender = userRepository.findById(message.getSenderId()).orElse(null);
        User receiver = userRepository.findById(message.getReceiverId()).orElse(null);
        Product product = message.getProductId() != null ? productRepository.findById(message.getProductId()).orElse(null) : null;

        return MessageDTO.builder()
                .messageId(message.getMessageId())
                .senderId(message.getSenderId())
                .senderName(sender != null ? sender.getRealName() : "")
                .receiverId(message.getReceiverId())
                .receiverName(receiver != null ? receiver.getRealName() : "")
                .productId(message.getProductId())
                .productName(product != null ? product.getProductName() : "")
                .messageType(message.getMessageType())
                .messageContent(message.getMessageContent())
                .sendTime(DateUtil.formatDateTime(message.getSendTime()))
                .isRead(message.getIsRead())
                .readTime(DateUtil.formatDateTime(message.getReadTime()))
                .build();
    }
}
