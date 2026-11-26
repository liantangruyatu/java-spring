package com.nuc.secondhand.controller;

import com.nuc.secondhand.dto.ApiResponse;
import com.nuc.secondhand.dto.MessageDTO;
import com.nuc.secondhand.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/messages")
@CrossOrigin(origins = {"http://localhost:8080", "http://localhost:3000", "http://localhost:5173"})
public class MessageController {
    @Autowired
    private MessageService messageService;

    @PostMapping
    public ApiResponse<MessageDTO> sendMessage(@RequestBody MessageDTO messageDTO) {
        try {
            MessageDTO sentMessage = messageService.sendMessage(messageDTO);
            return ApiResponse.success("消息发送成功", sentMessage);
        } catch (Exception e) {
            return ApiResponse.error(400, e.getMessage());
        }
    }

    @GetMapping("/{messageId}")
    public ApiResponse<MessageDTO> getMessageById(@PathVariable Integer messageId) {
        try {
            MessageDTO messageDTO = messageService.getMessageById(messageId);
            return ApiResponse.success(messageDTO);
        } catch (Exception e) {
            return ApiResponse.error(404, e.getMessage());
        }
    }

    @GetMapping("/receiver/{receiverId}")
    public ApiResponse<Page<MessageDTO>> getReceiverMessages(
            @PathVariable Integer receiverId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<MessageDTO> messages = messageService.getReceiverMessages(receiverId, pageable);
            return ApiResponse.success(messages);
        } catch (Exception e) {
            return ApiResponse.error(500, e.getMessage());
        }
    }

    @GetMapping("/receiver/{receiverId}/unread")
    public ApiResponse<Page<MessageDTO>> getUnreadMessages(
            @PathVariable Integer receiverId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<MessageDTO> messages = messageService.getUnreadMessages(receiverId, pageable);
            return ApiResponse.success(messages);
        } catch (Exception e) {
            return ApiResponse.error(500, e.getMessage());
        }
    }

    @GetMapping("/conversation/{senderId}/{receiverId}")
    public ApiResponse<Page<MessageDTO>> getConversation(
            @PathVariable Integer senderId,
            @PathVariable Integer receiverId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<MessageDTO> messages = messageService.getConversation(senderId, receiverId, pageable);
            return ApiResponse.success(messages);
        } catch (Exception e) {
            return ApiResponse.error(500, e.getMessage());
        }
    }

    @PutMapping("/{messageId}/read")
    public ApiResponse<MessageDTO> markAsRead(@PathVariable Integer messageId) {
        try {
            MessageDTO messageDTO = messageService.markAsRead(messageId);
            return ApiResponse.success("消息标记为已读", messageDTO);
        } catch (Exception e) {
            return ApiResponse.error(400, e.getMessage());
        }
    }

    @DeleteMapping("/{messageId}")
    public ApiResponse<String> deleteMessage(@PathVariable Integer messageId) {
        try {
            messageService.deleteMessage(messageId);
            return ApiResponse.success("消息删除成功");
        } catch (Exception e) {
            return ApiResponse.error(400, e.getMessage());
        }
    }
}
