package com.example.labmanagement.controller;

import com.example.labmanagement.model.Reservation;
import com.example.labmanagement.service.ReservationService;
import com.example.labmanagement.repository.ReservationRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ReservationController {

    private final ReservationService reservationService;
    private final ReservationRepository reservationRepository;

    public ReservationController(ReservationService reservationService, ReservationRepository reservationRepository) {
        this.reservationService = reservationService;
        this.reservationRepository = reservationRepository;
    }

    @PostMapping("/student/reservations")
    public Reservation create(@RequestBody Reservation r) {
        return reservationService.createReservation(r);
    }

    @GetMapping("/student/reservations/my")
    public List<Reservation> getMyReservations(@RequestParam Integer userId) {
        return reservationRepository.findByUserId(userId);
    }

    @GetMapping("/teacher/reservations/pending")
    public List<Reservation> listPending() {
        return reservationRepository.findAll().stream()
                .filter(r -> r.getStatus().name().equals("PENDING"))
                .collect(java.util.stream.Collectors.toList());
    }

    @PostMapping("/teacher/reservations/{id}/approve")
    public Reservation approve(@PathVariable Integer id) {
        return reservationService.approveReservation(id);
    }

    @PostMapping("/teacher/reservations/{id}/reject")
    public Reservation reject(@PathVariable Integer id) {
        Reservation r = reservationRepository.findById(id).orElse(null);
        if (r != null) {
            r.setStatus(com.example.labmanagement.model.ReservationStatus.REJECTED);
            return reservationRepository.save(r);
        }
        return null;
    }

    @PostMapping("/student/reservations/{id}/cancel")
    public Reservation cancel(@PathVariable Integer id) {
        Reservation r = reservationRepository.findById(id).orElse(null);
        if (r != null) {
            r.setStatus(com.example.labmanagement.model.ReservationStatus.CANCELLED);
            return reservationRepository.save(r);
        }
        return null;
    }

    @GetMapping("/reservations/{id}")
    public Reservation getDetail(@PathVariable Integer id) {
        return reservationRepository.findById(id).orElse(null);
    }

    @GetMapping("/reservations")
    public List<Reservation> listAll() {
        return reservationRepository.findAll();
    }
}
