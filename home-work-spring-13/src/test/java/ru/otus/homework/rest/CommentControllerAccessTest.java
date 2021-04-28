package ru.otus.homework.rest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.homework.service.UserService;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Tests for access of comments controller")
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class CommentControllerAccessTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    //Checks rules fo USER role - allowed only all puts and get requests

    //Forbidden requests
    @WithMockUser(username = "user", authorities = {"USER"})
    @Test
    @DisplayName("Forbidden delete comment requests for USER role ")
    void deleteCommentsByUser() throws Exception {
        mockMvc.perform(delete("/comments/2").with(csrf())).andExpect(status().isForbidden());
    }


    @WithMockUser(username = "user", authorities = {"USER"})
    @Test
    @DisplayName("Forbidden post comments requests for USER role ")
    void postCommentByUser() throws Exception {
        String testJsonString = "{\"id\":1,\"name\":\"Test\",\"author\":\"Test\",\"genre\":\"Test\"}";
        mockMvc.perform(post("/books/1/comments")
                .content(testJsonString).contentType("application/json; charset=UTF-8").with(csrf())
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isForbidden());
    }


    //allowed requests

    @WithMockUser(username = "user", authorities = {"USER"})
    @Test
    @DisplayName("Allowed put comment requests for USER role ")
    void putCommentsByUser() throws Exception {
        String testJsonString = "{\"id\":1,\"bookId\":1,\"text\":\"Test\",\"bookName\":\"Test\"}";
        mockMvc.perform(put("/comments/1").content(testJsonString)
                .contentType("application/json; charset=UTF-8").with(csrf())
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @WithMockUser(username = "user", authorities = {"USER"})
    @Test
    @DisplayName("Allowed get comments requests for USER role ")
    void getCommentsByUser() throws Exception {
        mockMvc.perform(get("/books/1/comments").with(csrf())).andExpect(status().isOk());
    }

    //Checks rules fo GUEST role

    //Forbidden requests - all post, put, delete and get comments
    @WithMockUser(username = "guest", authorities = {"GUEST"})
    @Test
    @DisplayName("Forbidden post comments requests for GUEST role ")
    void postCommentByGuest() throws Exception {
        String testJsonString = "{\"id\":1,\"name\":\"Test\",\"author\":\"Test\",\"genre\":\"Test\"}";
        mockMvc.perform(post("/books/1/comments")
                .content(testJsonString).contentType("application/json; charset=UTF-8").with(csrf())
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isForbidden());
    }

    @WithMockUser(username = "guest", authorities = {"GUEST"})
    @Test
    @DisplayName("Forbidden delete comment requests for GUEST role ")
    void deleteCommentByGuest() throws Exception {
        mockMvc.perform(delete("/comments/2").with(csrf())).andExpect(status().isForbidden());
    }

    @WithMockUser(username = "guest", authorities = {"GUEST"})
    @Test
    @DisplayName("Forbidden put comment requests for GUEST role ")
    void putCommentByGuest() throws Exception {
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


    //all requests allowed for admin
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    @Test
    @DisplayName("Allowed get comments requests for ADMIN role ")
    void getByAdmin() throws Exception {
        mockMvc.perform(get("/books/1/comments").with(csrf())).andExpect(status().isOk());
    }


    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    @Test
    @DisplayName("Allowed post comment requests for ADMIN role ")
    void postCommentsByAdmin() throws Exception {
        String testJsonString = "{\"id\":5,\"bookId\":1,\"text\":\"Test\",\"bookName\":\"Test\"}";
        mockMvc.perform(post("/books/1/comments")
                .content(testJsonString).contentType("application/json; charset=UTF-8").with(csrf())
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().is2xxSuccessful());
    }


    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    @Test
    @DisplayName("Allowed put comment requests for ADMIN role ")
    void putCommentByAdmin() throws Exception {
        String testJsonString = "{\"id\":1,\"bookId\":1,\"text\":\"Test\",\"bookName\":\"Test\"}";
        mockMvc.perform(put("/comments/1")
                .content(testJsonString).contentType("application/json; charset=UTF-8").with(csrf())
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().is2xxSuccessful());
    }

    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    @Test
    @DisplayName("Allowed delete requests for ADMIN role ")
    void deleteCommentByAdmin() throws Exception {
        mockMvc.perform(delete("/comments/3").with(csrf())).andExpect(status().isOk());
    }
}
