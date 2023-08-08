package com.example.bookingapp.repository;

import com.example.bookingapp.domain.Reservas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservasRepository extends JpaRepository<Reservas, Integer> {
}
