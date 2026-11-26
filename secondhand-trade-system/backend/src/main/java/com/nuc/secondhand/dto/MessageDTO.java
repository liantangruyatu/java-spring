package com.nuc.secondhand.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageDTO {
    private Integer messageId;
    private Integer senderId;
    private String senderName;
    private Integer receiverId;
    private String receiverName;
    private Integer productId;
    private String productName;
    private String messageType;
    private String messageContent;
    private String sendTime;
    private Integer isRead;
    private String readTime;
}
