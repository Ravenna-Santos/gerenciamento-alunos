package br.com.gerenciamento.controller;

import br.com.gerenciamento.model.Aluno;
import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.enums.Turno;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import br.com.gerenciamento.repository.AlunoRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AlunoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AlunoRepository alunoRepository;

    @Before
    public void beforeEach() {
        alunoRepository.deleteAll();
    }

    @After
    public void afterEach() {
        alunoRepository.deleteAll();
    }

    @Test
    public void deveCadastrarAluno() throws Exception {
        mockMvc.perform(post("/InsertAlunos")
                .param("nome", "Aluno Create")
                .param("matricula", "10001")
                .param("curso", "INFORMATICA")
                .param("status", "ATIVO")
                .param("turno", "MATUTINO"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/alunos-adicionados"));
    }
    @Test
    public void deveExibirPaginaFiltroAlunos() throws Exception {
        mockMvc.perform(get("/filtro-alunos"))
                .andExpect(status().isOk())
                .andExpect(view().name("Aluno/filtroAlunos"));
    }

    @Test
    public void deveAtualizarAluno() throws Exception {
        Aluno aluno = new Aluno();
        aluno.setNome("Aluno Update");
        aluno.setMatricula("10002");
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setTurno(Turno.NOTURNO);
        Aluno salvo = alunoRepository.save(aluno);

        mockMvc.perform(post("/editar")
                .param("id", salvo.getId().toString())
                .param("nome", "Aluno Atualizado")
                .param("matricula", "10002")
                .param("curso", "ADMINISTRACAO")
                .param("status", "ATIVO")
                .param("turno", "NOTURNO"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/alunos-adicionados"));
    }

    @Test
    public void deveDeletarAluno() throws Exception {
        Aluno aluno = new Aluno();
        aluno.setNome("Aluno Delete");
        aluno.setMatricula("10003");
        aluno.setCurso(Curso.DIREITO);
        aluno.setStatus(Status.ATIVO);
        aluno.setTurno(Turno.MATUTINO);
        Aluno salvo = alunoRepository.save(aluno);

        mockMvc.perform(get("/remover/" + salvo.getId()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/alunos-adicionados"));
    }
}
