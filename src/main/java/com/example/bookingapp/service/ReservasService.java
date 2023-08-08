package com.example.bookingapp.service;

import com.example.bookingapp.domain.Reservas;
import com.example.bookingapp.repository.ReservasRepository;
import com.example.bookingapp.service.exceptions.ObjectNotFoundException;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReservasService {

    @Autowired
    private ReservasRepository reservasRepository;

    public Reservas create(@NonNull Reservas reserva) {
        reserva.setId(null);
        reserva.setStatus("CONFIRMADA");

        return reservasRepository.save(reserva);
    }

    public Reservas findById(Integer id) {
        Optional<Reservas> reserva = reservasRepository.findById(id);

        return reserva.orElseThrow(() -> new ObjectNotFoundException("Object not found! ID: " + id));
    }

    public List<Reservas> findAll() {
        List<Reservas> list = reservasRepository.findAll();

        if (list.isEmpty()) {
            throw new ObjectNotFoundException("Database is empty.");
        }

        return reservasRepository.findAll();
    }

    public Reservas delete(Integer id) {
        Reservas obj = reservasRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Object not found! ID: " + id));

        obj.setStatus("CANCELADA");

        return reservasRepository.save(obj);
    }

    public Reservas update(Reservas reserva, Integer id) {
        Reservas newObj = reservasRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Object not found! ID: " + id));

        newObj.setNomeHospede(reserva.getNomeHospede());
        newObj.setDataInicio(reserva.getDataInicio());
        newObj.setDataFim(reserva.getDataFim());
        newObj.setQuantidadePessoas(reserva.getQuantidadePessoas());
        newObj.setStatus(reserva.getStatus());

        return reservasRepository.save(newObj);
    }
}
