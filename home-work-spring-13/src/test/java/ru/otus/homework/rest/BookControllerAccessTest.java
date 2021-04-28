package ru.otus.homework.rest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.homework.service.UserService;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Tests for access of book controller")
@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerAccessTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    //Checks rules fo USER role - allowed only all puts and get requests

    //Forbidden requests
    @WithMockUser(username = "user", authorities = {"USER"})
    @Test
    @DisplayName("Forbidden delete book requests for USER role ")
    void deleteBookyUser() throws Exception {
        mockMvc.perform(delete("/books/3").with(csrf())).andExpect(status().isForbidden());
    }

    @WithMockUser(username = "user", authorities = {"USER"})
    @Test
    @DisplayName("Forbidden post book requests for USER role ")
    void postBookByUser() throws Exception {
        String testJsonString = "{\"id\":1,\"name\":\"Test\",\"author\":\"Test\",\"genre\":\"Test\"}";
        mockMvc.perform(post("/books")
                .content(testJsonString).contentType("application/json; charset=UTF-8").with(csrf())
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isForbidden());

    }

    //allowed requests
    @WithMockUser(username = "user", authorities = {"USER"})
    @Test
    @DisplayName("Allowed put book requests for USER role ")
    void putBookByUser() throws Exception {
        String testJsonString = "{\"id\":1,\"name\":\"Test\",\"author\":\"Test\",\"genre\":\"Test\"}";
        mockMvc.perform(put("/books/1")
                .content(testJsonString).contentType("application/json; charset=UTF-8").with(csrf())
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @WithMockUser(username = "user", authorities = {"USER"})
    @Test
    @DisplayName("Allowed get books requests for USER role ")
    void getBooksByUser() throws Exception {
        mockMvc.perform(get("/books").with(csrf())).andExpect(status().isOk());
    }


    //Checks rules fo GUEST role

    //Forbidden requests - all post, put, delete
    @WithMockUser(username = "guest", authorities = {"GUEST"})
    @Test
    @DisplayName("Forbidden post requests for GUEST role ")
    void postBookByGuest() throws Exception {
        String testJsonString = "{\"id\":1,\"name\":\"Test\",\"author\":\"Test\",\"genre\":\"Test\"}";
        mockMvc.perform(post("/books")
                .content(testJsonString).contentType("application/json; charset=UTF-8").with(csrf())
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isForbidden());

    }

    @WithMockUser(username = "guest", authorities = {"GUEST"})
    @Test
    @DisplayName("Forbidden delete requests for GUEST role ")
    void deleteBookByGuest() throws Exception {
        mockMvc.perform(delete("/books/1").with(csrf())).andExpect(status().isForbidden());
    }

    @WithMockUser(username = "guest", authorities = {"GUEST"})
    @Test
    @DisplayName("Forbidden put book requests for GUEST role ")
    void putBookByGuest() throws Exception {
        String testJsonString = "{\"id\":1,\"name\":\"Test\",\"author\":\"Test\",\"genre\":\"Test\"}";
        mockMvc.perform(put("/books/1")
                .content(testJsonString).contentType("application/json; charset=UTF-8").with(csrf())
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isForbidden());
    }

    //allowed requests - get books
    @WithMockUser(username = "guest", authorities = {"GUEST"})
    @Test
    @DisplayName("Allowed get books requests for GUEST role ")
    void getBooksByGuest() throws Exception {
        mockMvc.perform(get("/books").with(csrf())).andExpect(status().isOk());
    }

    //all requests allowed for ADMIN role

    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    @Test
    @DisplayName("Allowed get books requests for ADMIN role ")
    void getByAdmin() throws Exception {
        mockMvc.perform(get("/books").with(csrf())).andExpect(status().isOk());
    }

    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    @Test
    @DisplayName("Allowed post book requests for ADMIN role ")
    void postBookByAdmin() throws Exception {
        String testJsonString = "{\"id\":10,\"name\":\"Test\",\"author\":\"Test\",\"genre\":\"Test\"}";
        mockMvc.perform(post("/books")
                .content(testJsonString).contentType("application/json; charset=UTF-8").with(csrf())
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().is2xxSuccessful());
    }

    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    @Test
    @DisplayName("Allowed post requests for ADMIN role ")
    void putBookByAdmin() throws Exception {
        String testJsonString = "{\"id\":1,\"name\":\"Test\",\"author\":\"Test\",\"genre\":\"Test\"}";
        mockMvc.perform(put("/books/1")
                .content(testJsonString).contentType("application/json; charset=UTF-8").with(csrf())
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().is2xxSuccessful());
    }

    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    @Test
    @DisplayName("Allowed delete book requests for ADMIN role ")
    void deleteBookByAdmin() throws Exception {
        mockMvc.perform(delete("/books/3").with(csrf())).andExpect(status().isOk());
    }
}
