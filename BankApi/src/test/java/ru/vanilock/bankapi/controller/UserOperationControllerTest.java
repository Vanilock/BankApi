package ru.vanilock.bankapi.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
class UserOperationControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    UserOperationController userOperationController;

    @Test
    void getBalance() throws Exception {
        mockMvc.perform(
                get("/api/getBalance?id=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.value").value(1));
    }

    @Test
    void takeMoney() throws Exception {
        mockMvc.perform(
                get("/api/takeMoney?id=1&amount=10000"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.value").value(1));

    }

    @Test
    void putMoney() throws Exception {
        mockMvc.perform(
                get("/api/putMoney?id=2&amount=300"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.value").value(1));
    }

    @Test
    void getOperationList() throws Exception {
        mockMvc.perform(
                get("/api/getOperationList?id=1"))
                .andExpect(status().isOk());
    }

    @Test
    void transferMoney() throws Exception {
        mockMvc.perform(
                get("/api/transferMoney?senderId=2&recipientId=1&amount=2000"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.value").value(1));
    }
}