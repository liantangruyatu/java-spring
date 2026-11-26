package com.example.labmanagement.service;

import com.example.labmanagement.model.Reservation;
import com.example.labmanagement.model.ReservationStatus;
import com.example.labmanagement.repository.ReservationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Transactional
    public Reservation createReservation(Reservation r) {
        // 简单冲突检测：检查指定实验室是否已有已批准的冲突预约
        List<Reservation> conflicts = reservationRepository.findApprovedConflicts(r.getLabId(), r.getStartTime(), r.getEndTime());
        if (conflicts != null && !conflicts.isEmpty()) {
            r.setStatus(ReservationStatus.REJECTED);
            return reservationRepository.save(r);
        }
        r.setStatus(ReservationStatus.PENDING);
        return reservationRepository.save(r);
    }

    @Transactional
    public Reservation approveReservation(Integer id) {
        Reservation r = reservationRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Reservation not found"));
        // 在批准前再次检查冲突
        List<Reservation> conflicts = reservationRepository.findApprovedConflicts(r.getLabId(), r.getStartTime(), r.getEndTime());
        if (conflicts != null && !conflicts.isEmpty()) {
            r.setStatus(ReservationStatus.REJECTED);
        } else {
            r.setStatus(ReservationStatus.APPROVED);
        }
        return reservationRepository.save(r);
    }
}
