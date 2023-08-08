package com.example.bookingapp.controller;

import com.example.bookingapp.domain.Reservas;
import com.example.bookingapp.service.ReservasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/reservas")
public class ReservasController {

    @Autowired
    private ReservasService reservasService;

    @PostMapping
    public ResponseEntity<Reservas> create(@RequestBody Reservas reserva) {
        Reservas obj = reservasService.create(reserva);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();

        return ResponseEntity.created(uri).body(obj);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reservas> findById(@PathVariable Integer id) {
        Reservas reserva = reservasService.findById(id);

        return ResponseEntity.ok().body(reserva);
    }

    @GetMapping
    public ResponseEntity<List<Reservas>> findAll() {
        List<Reservas> reservas = reservasService.findAll();

        return ResponseEntity.ok().body(reservas);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Reservas> update(@RequestBody Reservas reserva, @PathVariable Integer id) {
        Reservas newObj = reservasService.update(reserva, id);

        return ResponseEntity.ok().body(newObj);
    }

    @DeleteMapping("/{id}/cancelar")
    public ResponseEntity<Reservas> delete(@PathVariable Integer id) {
        Reservas deleted = reservasService.delete(id);

        return ResponseEntity.ok().body(deleted);
    }
}
