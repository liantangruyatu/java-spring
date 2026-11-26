package com.nuc.secondhand.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "transaction")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer transactionId;

    @Column(nullable = false)
    private Integer orderId;

    @Column(nullable = false)
    private Integer buyerId;

    @Column(nullable = false)
    private Integer sellerId;

    @Column(nullable = false)
    private Double transactionAmount;

    @Column(nullable = false)
    private String transactionType;

    @Column(nullable = false)
    private String transactionStatus = "进行中";

    @Column(nullable = false)
    private LocalDateTime transactionTime = LocalDateTime.now();

    private LocalDateTime expectedCompletionTime;

    private LocalDateTime actualCompletionTime;

    private String remarks;
}
