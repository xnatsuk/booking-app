package com.example.bookingapp.controller;

import com.example.bookingapp.domain.Reservas;
import com.example.bookingapp.service.ReservasService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@ExtendWith(SpringExtension.class)
@AutoConfigureJsonTesters
@WebMvcTest(ReservasController.class)
public class ReservasControllerTest {

    ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ReservasService reservaService;
    @InjectMocks
    private ReservasController reservaController;
    @Autowired
    private JacksonTester<Reservas> jsonParser;

    @Test
    public void testCreateReserva() throws Exception {
        Reservas reservas = new Reservas(1, "teste", "2023-08-10", "2023-08-20", 4, "CONFIRMADA");

        given(reservaService.create(any(Reservas.class))).willReturn(reservas);

        MockHttpServletResponse response = mockMvc.perform(
                        post("/reservas").contentType(MediaType.APPLICATION_JSON).content(jsonParser.write(reservas).getJson()))
                .andDo(print())
                .andReturn().getResponse();

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
        then(response.getContentAsString()).isEqualTo(jsonParser.write(reservas).getJson());
    }

    @Test
    public void testDelete() throws Exception {
        Integer id = 2;
        Reservas reserva = new Reservas(id, "teste", "2023-08-10", "2023-08-20", 4, "CONFIRMADA");
        Reservas newObj = new Reservas(id, "teste", "2023-08-10", "2023-08-20", 4, "CANCELADA");

        given(reservaService.delete(id)).willReturn(newObj);

        MockHttpServletResponse response = mockMvc.perform(
                        delete("/reservas/2/cancelar").contentType(MediaType.APPLICATION_JSON).content(jsonParser.write(newObj).getJson()))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andReturn().getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(newObj.getStatus(), objectMapper.readValue(response.getContentAsString(), Reservas.class).getStatus());
        assertNotEquals(reserva, objectMapper.readValue(response.getContentAsString(), Reservas.class));

        verify(reservaService, times(1)).delete(id);
    }

    @Test
    public void testUpdate() throws Exception {
        Integer id = 2;
        Reservas reserva = new Reservas(id, "teste", "2023-08-10", "2023-08-20", 4, "CONFIRMADA");
        Reservas newObj = new Reservas(id, "teste", "2023-09-10", "2023-09-20", 4, "PENDENTE");

        given(reservaService.update(any(Reservas.class), eq(id))).willReturn(newObj);

        MockHttpServletResponse response = mockMvc.perform(
                        put("/reservas/2").contentType(MediaType.APPLICATION_JSON).content(jsonParser.write(newObj).getJson()))
                .andDo(print())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andReturn().getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(newObj.getStatus(), objectMapper.readValue(response.getContentAsString(), Reservas.class).getStatus());
        assertNotEquals(reserva, objectMapper.readValue(response.getContentAsString(), Reservas.class));

        verify(reservaService, times(1)).update(any(Reservas.class), eq(id));

    }

    @Test
    public void testFindById() throws Exception {
        Integer id = 1;
        Reservas reserva = new Reservas(id, "teste", "2023-08-10", "2023-08-20", 4, "CONFIRMADA");

        given(reservaService.findById(id)).willReturn(reserva);

        MockHttpServletResponse response = mockMvc.perform(
                        get("/reservas/1").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andReturn().getResponse();


        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertThat(objectMapper.readValue(response.getContentAsString(), Reservas.class)).usingRecursiveComparison().isEqualTo(reserva);

        verify(reservaService, times(1)).findById(id);
    }

    @Test
    public void testFindAll() throws Exception {
        Reservas reserva1 = new Reservas(1, "teste", "2023-08-10", "2023-08-20", 4, "CONFIRMADA");
        Reservas reserva2 = new Reservas(2, "ciclano", "2023-08-04", "2023-08-10", 3, "PENDENTE");
        List<Reservas> list = Arrays.asList(reserva1, reserva2);

        given(reservaService.findAll()).willReturn(list);

        MockHttpServletResponse response = mockMvc.perform(
                        get("/reservas").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andReturn().getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(list.size(), objectMapper.readValue(response.getContentAsString(), new TypeReference<List<Reservas>>() {
        }).size());
        assertThat(objectMapper.readValue(response.getContentAsString(), new TypeReference<List<Reservas>>() {
        }))
                .usingRecursiveComparison()
                .isEqualTo(list);

        verify(reservaService, times(1)).findAll();
    }
}
