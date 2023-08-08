package com.example.bookingapp.service;

import com.example.bookingapp.domain.Reservas;
import com.example.bookingapp.repository.ReservasRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReservasServiceTest {

    @Mock
    private ReservasRepository reservasRepository;
    @InjectMocks
    private ReservasService reservasService;


    @Test
    public void testCreate() {
        Reservas reserva = new Reservas();

        given(reservasRepository.save(any(Reservas.class))).willReturn(reserva);

        Reservas result = reservasService.create(reserva);

        assertNotNull(result);
        assertNotNull(reserva);
        assertEquals(reserva, result);

        verify(reservasRepository, times(1)).save(any(Reservas.class));
    }

    @Test
    public void testFindById() {
        Integer id = 1;
        Reservas reserva = new Reservas();
        reserva.setId(id);

        given(reservasRepository.findById(id)).willReturn(Optional.of(reserva));

        Reservas found = reservasService.findById(id);

        assertNotNull(found);
        assertNotNull(reserva);
        assertEquals(reserva, found);

        verify(reservasRepository, times(1)).findById(id);
    }

    @Test
    public void testFindAll() {
        Reservas reserva1 = new Reservas();
        Reservas reserva2 = new Reservas();

        given(reservasRepository.findAll()).willReturn(Arrays.asList(reserva1, reserva2));

        List<Reservas> result = reservasService.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());

        verify(reservasRepository, times(2)).findAll();
    }

    @Test
    public void testDelete() {
        Integer id = 2;
        Reservas reserva = new Reservas(id, "teste", "2023-08-10", "2023-08-20", 4, "CONFIRMADA");
        Reservas deletedObj = new Reservas(id, "teste", "2023-08-10", "2023-08-20", 4, "CANCELADA");

        given(reservasRepository.findById(id)).willReturn(Optional.of(reserva));
        given(reservasRepository.save(any(Reservas.class))).willReturn(deletedObj);

        Reservas result = reservasService.delete(id);

        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals(deletedObj.getStatus(), result.getStatus());

        verify(reservasRepository, times(1)).findById(id);
        verify(reservasRepository, times(1)).save(any(Reservas.class));
    }

    @Test
    public void testUpdate() {
        Integer id = 2;
        Reservas reserva = new Reservas(id, "fulano", "2023-08-10", "2023-08-20", 4, "CONFIRMADA");
        Reservas newObj = new Reservas(id, "fulano", "2023-08-10", "2023-08-20", 4, "PENDENTE");

        given(reservasRepository.findById(id)).willReturn(Optional.of(reserva));
        given(reservasRepository.save(any(Reservas.class))).willReturn(newObj);

        Reservas result = reservasService.update(newObj, id);

        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals(newObj.getStatus(), result.getStatus());

        verify(reservasRepository, times(1)).findById(id);
        verify(reservasRepository, times(1)).save(any(Reservas.class));
    }
}
