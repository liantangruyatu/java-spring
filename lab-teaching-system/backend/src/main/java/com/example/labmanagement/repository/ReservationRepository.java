package com.example.labmanagement.repository;

import com.example.labmanagement.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
	@Query("SELECT r FROM Reservation r WHERE r.labId = :labId AND r.status = 'APPROVED' AND NOT (r.endTime <= :start OR r.startTime >= :end)")
    List<Reservation> findApprovedConflicts(@Param("labId") Integer labId, @Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
    List<Reservation> findByUserId(Integer userId);
	List<Reservation> findByStatus(com.example.labmanagement.model.ReservationStatus status);
}
