package com.example.service_evenement;
import com.example.service_evenement.controller.EventController;
import com.example.service_evenement.model.Event;
import com.example.service_evenement.service.EventService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(EventController.class)
public class EventControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private EventService eventService;

    @InjectMocks
    private EventController eventController;

    private Event event;

    @BeforeEach
    void setUp() {
        event = new Event("1", "Event 1", null, "Category 1", 10, 100);
    }

    @Test
    void testCreateEvent() throws Exception {
        when(eventService.createEvent(any(Event.class))).thenReturn(event);

        mockMvc.perform(post("/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Event 1\",\"category\":\"Category 1\",\"participantCount\":10,\"maxParticipants\":100}"))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Event 1"));
    }

    @Test
    void testGetEvents() throws Exception {
        when(eventService.getEvents(null, null)).thenReturn(Mockito.mock(Page.class));

        mockMvc.perform(get("/events"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testGetEventById() throws Exception {
        when(eventService.getEventById("1")).thenReturn(java.util.Optional.of(event));

        mockMvc.perform(get("/events/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Event 1"));
    }

    @Test
    void testUpdateEvent() throws Exception {
        when(eventService.updateEvent("1", event)).thenReturn(event);

        mockMvc.perform(put("/events/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Event 1\",\"category\":\"Category 1\",\"participantCount\":10,\"maxParticipants\":100}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Event 1"));
    }

    @Test
    void testDeleteEvent() throws Exception {
        Mockito.doNothing().when(eventService).deleteEvent("1");

        mockMvc.perform(delete("/events/1"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void testUpdateParticipantCount() throws Exception {
        Mockito.doNothing().when(eventService).updateParticipantCount("1", 20);

        mockMvc.perform(put("/events/1/participantCount")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("20"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}

