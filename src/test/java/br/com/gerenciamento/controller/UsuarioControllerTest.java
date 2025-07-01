package br.com.gerenciamento.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void deveExibirPaginaLogin() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("login/login"));
    }

    @Test
    public void deveExibirPaginaCadastro() throws Exception {
        mockMvc.perform(get("/cadastro"))
                .andExpect(status().isOk())
                .andExpect(view().name("login/cadastro"));
    }

    @Test
    public void deveRedirecionarAoSalvarUsuario() throws Exception {
        mockMvc.perform(post("/salvarUsuario")
                .param("user", "usuarioTeste")
                .param("senha", "senha123")
                .param("email", "teste@email.com"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    public void deveFazerLogoutERedirecionarParaLogin() throws Exception {
    mockMvc.perform(post("/logout"))
            .andExpect(status().isOk())
            .andExpect(view().name("login/login"));
}
}
