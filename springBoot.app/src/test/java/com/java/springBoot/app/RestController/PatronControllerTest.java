package com.java.springBoot.app.RestController;

import com.java.springBoot.app.Model.Patron;
import com.java.springBoot.app.Service.PatronService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class PatronControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PatronService patronService;

    @InjectMocks
    private PatronController patronController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(patronController).build();
    }

    @Test
    void testGetAllPatrons() throws Exception {
        Patron patron1 = new Patron(1L, "John Doe", "john@example.com", "1234567890");
        Patron patron2 = new Patron(2L, "Jane Doe", "jane@example.com", "0987654321");

        when(patronService.getAllPatrons()).thenReturn(Arrays.asList(patron1, patron2));

        mockMvc.perform(get("/api/patrons"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data[0].name").value("John Doe"))
                .andExpect(jsonPath("$.data[1].email").value("jane@example.com"));
    }

    @Test
    void testGetPatronById() throws Exception {
        Patron patron = new Patron(1L, "John Doe", "john@example.com", "1234567890");

        when(patronService.getPatron(1L)).thenReturn(patron);

        mockMvc.perform(get("/api/patrons/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value("John Doe"))
                .andExpect(jsonPath("$.data.email").value("john@example.com"));
    }

    @Test
    void testGetPatronById_NotFound() throws Exception {
        when(patronService.getPatron(1L)).thenReturn(null);

        mockMvc.perform(get("/api/patrons/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Patron not found"));
    }

    @Test
    void testAddPatron() throws Exception {
        Patron patron = new Patron(null, "John Doe", "john@example.com", "1234567890");
        Patron savedPatron = new Patron(1L, "John Doe", "john@example.com", "1234567890");

        when(patronService.addPatron(any(Patron.class))).thenReturn(savedPatron);

        mockMvc.perform(post("/api/patrons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"John Doe\",\"email\":\"john@example.com\",\"phoneNumber\":\"1234567890\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.name").value("John Doe"))
                .andExpect(jsonPath("$.data.id").value(1));
    }

    @Test
    void testUpdatePatron() throws Exception {
        Patron existingPatron = new Patron(1L, "John Doe", "john@example.com", "1234567890");
        Patron updatedPatron = new Patron(1L, "Johnny Doe", "johnny@example.com", "0987654321");

        when(patronService.getPatron(1L)).thenReturn(existingPatron);
        when(patronService.updatePatron(any(Patron.class))).thenReturn(updatedPatron);

        mockMvc.perform(put("/api/patrons/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Johnny Doe\",\"email\":\"johnny@example.com\",\"phoneNumber\":\"0987654321\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value("Johnny Doe"))
                .andExpect(jsonPath("$.data.email").value("johnny@example.com"));
    }

    @Test
    void testDeletePatron() throws Exception {
        when(patronService.getPatron(1L)).thenReturn(new Patron(1L, "John Doe", "john@example.com", "1234567890"));

        doNothing().when(patronService).deletePatron(1L);

        mockMvc.perform(delete("/api/patrons/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Success"));
    }




}
