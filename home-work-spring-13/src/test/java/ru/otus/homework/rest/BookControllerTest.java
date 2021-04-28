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

@DisplayName("Tests for access for book and comments controller")
@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    //Checks rules fo USER role - allowed only all puts and get requests

    //Forbidden requests
    @WithMockUser(username = "user", authorities = {"USER"})
    @ParameterizedTest
    @ValueSource(strings = {"/books/3", "/comments/2"})
    @DisplayName("Forbidden delete requests for USER role ")
    void deleteBookOrCommentsByUser(String value) throws Exception {
        mockMvc.perform(delete(value).with(csrf())).andExpect(status().isForbidden());
    }

    @WithMockUser(username = "user", authorities = {"USER"})
    @Test
    @DisplayName("Forbidden post requests for USER role ")
    void postBookByUser() throws Exception {
        String testJsonString = "{\"id\":1,\"name\":\"Test\",\"author\":\"Test\",\"genre\":\"Test\"}";
        mockMvc.perform(post("/books")
                .content(testJsonString).contentType("application/json; charset=UTF-8").with(csrf())
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isForbidden());

    }

    @WithMockUser(username = "user", authorities = {"USER"})
    @ParameterizedTest
    @ValueSource(strings = {"/books", "/books/1/comments"})
    @DisplayName("Forbidden post requests for USER role ")
    void postommentByUser() throws Exception {
        String testJsonString = "{\"id\":1,\"name\":\"Test\",\"author\":\"Test\",\"genre\":\"Test\"}";
        mockMvc.perform(post("/books/1/comments")
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
    @DisplayName("Allowed put book requests for USER role ")
    void putCommentsByUser() throws Exception {
        String testJsonString = "{\"id\":1,\"bookId\":1,\"text\":\"Test\",\"bookName\":\"Test\"}";
        mockMvc.perform(put("/comments/1").content(testJsonString)
                .contentType("application/json; charset=UTF-8").with(csrf())
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @WithMockUser(username = "user", authorities = {"USER"})
    @ParameterizedTest
    @ValueSource(strings = {"/books", "/books/1/comments"})
    @DisplayName("Allowed get requests for USER role ")
    void getBookOrCommentsByUser(String value) throws Exception {
        mockMvc.perform(get(value).with(csrf())).andExpect(status().isOk());
    }

    //Checks rules fo GUEST role

    //Forbidden requests - all post, put, delete and get comments
    @WithMockUser(username = "guest", authorities = {"GUEST"})
    @Test
    @DisplayName("Forbidden post requests for USER role ")
    void postBookByGuest() throws Exception {
        String testJsonString = "{\"id\":1,\"name\":\"Test\",\"author\":\"Test\",\"genre\":\"Test\"}";
        mockMvc.perform(post("/books")
                .content(testJsonString).contentType("application/json; charset=UTF-8").with(csrf())
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isForbidden());

    }

    @WithMockUser(username = "guest", authorities = {"GUEST"})
    @ParameterizedTest
    @ValueSource(strings = {"/books", "/books/1/comments"})
    @DisplayName("Forbidden post requests for USER role ")
    void postommentByGuest() throws Exception {
        String testJsonString = "{\"id\":1,\"name\":\"Test\",\"author\":\"Test\",\"genre\":\"Test\"}";
        mockMvc.perform(post("/books/1/comments")
                .content(testJsonString).contentType("application/json; charset=UTF-8").with(csrf())
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isForbidden());
    }



    @WithMockUser(username = "guest", authorities = {"GUEST"})
    @ParameterizedTest
    @ValueSource(strings = {"/books/1", "/comments/2"})
    @DisplayName("Forbidden delete requests for GUEST role ")
    void deleteBookOrCommentsByGuest(String value) throws Exception {
        mockMvc.perform(delete(value).with(csrf())).andExpect(status().isForbidden());
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

    @WithMockUser(username = "guest", authorities = {"GUEST"})
    @Test
    @DisplayName("Forbidden put book requests for GUEST role ")
    void putCommentsByGuest() throws Exception {
        String testJsonString = "{\"id\":1,\"bookId\":1,\"text\":\"Test\",\"bookName\":\"Test\"}";
        mockMvc.perform(put("/comments/1").content(testJsonString)
                .contentType("application/json; charset=UTF-8").with(csrf())
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isForbidden());
    }

    @WithMockUser(username = "guest", authorities = {"GUEST"})
    @Test
    @DisplayName("Forbidden get requests for GUEST role ")
    void getCommentsByGuest() throws Exception {
        mockMvc.perform(get("/books/1/comments").with(csrf())).andExpect(status().isForbidden());
    }


    //allowed requests - get books
    @WithMockUser(username = "guest", authorities = {"GUEST"})
    @Test
    @DisplayName("Allowed get requests for USER role ")
    void getBooksByGuest() throws Exception {
        mockMvc.perform(get("/books").with(csrf())).andExpect(status().isOk());
    }

    //all requests allowed for admin
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    @ParameterizedTest
    @ValueSource(strings = {"/books/1/comments", "/books"})
    @DisplayName("Allowed get requests for ADMIN role ")
    void getByAdmin(String value) throws Exception {
        mockMvc.perform(get(value).with(csrf())).andExpect(status().isOk());
    }

    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    @Test
    @DisplayName("Allowed post requests for ADMIN role ")
    void postBookByAdmin() throws Exception {
        String testJsonString = "{\"id\":10,\"name\":\"Test\",\"author\":\"Test\",\"genre\":\"Test\"}";
        mockMvc.perform(post("/books")
                .content(testJsonString).contentType("application/json; charset=UTF-8").with(csrf())
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().is2xxSuccessful());
    }

    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    @Test
    @DisplayName("Allowed post requests for ADMIN role ")
    void postCommentsByAdmin() throws Exception {
        String testJsonString = "{\"id\":5,\"bookId\":1,\"text\":\"Test\",\"bookName\":\"Test\"}";
        mockMvc.perform(post("/books/1/comments")
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
    @DisplayName("Allowed post requests for ADMIN role ")
    void putCommentByAdmin() throws Exception {
        String testJsonString = "{\"id\":1,\"bookId\":1,\"text\":\"Test\",\"bookName\":\"Test\"}";
        mockMvc.perform(put("/comments/1")
                .content(testJsonString).contentType("application/json; charset=UTF-8").with(csrf())
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().is2xxSuccessful());
    }

    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    @ParameterizedTest
    @ValueSource(strings = {"/comments/3", "/books/3"})
    @DisplayName("Allowed delete requests for ADMIN role ")
    void deleteBookOrCommentsByAdmin(String value) throws Exception {
        mockMvc.perform(delete(value).with(csrf())).andExpect(status().isOk());
    }
}
