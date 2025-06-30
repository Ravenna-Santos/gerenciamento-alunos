package br.com.gerenciamento.service;

import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.enums.Turno;
import br.com.gerenciamento.model.Aluno;
import jakarta.validation.ConstraintViolationException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.*;
import org.junit.experimental.theories.suppliers.TestedOn;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AlunoServiceTest {

    @Autowired
    private ServiceAluno serviceAluno;

    @Test
    public void getById() {
        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setNome("Vinicius");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("123456");
        this.serviceAluno.save(aluno);

        Aluno alunoRetorno = this.serviceAluno.getById(1L);
        Assert.assertTrue(alunoRetorno.getNome().equals("Vinicius"));
    }

    @Test
    public void salvarSemNome() {
        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("123456");
        Assert.assertThrows(ConstraintViolationException.class, () -> {
            this.serviceAluno.save(aluno);
        });
    }

    @Test
    public void salvarAlunoComTodosCampos() {
        Aluno aluno = new Aluno();
        aluno.setNome("Maria");
        aluno.setTurno(Turno.MATUTINO);
        aluno.setCurso(Curso.INFORMATICA);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("654321");
        this.serviceAluno.save(aluno);
        Aluno saved = this.serviceAluno.getById(aluno.getId());
        Assert.assertNotNull(saved);
        Assert.assertEquals("Maria", saved.getNome());
    }

    @Test
    public void salvarSemMatricula() {
        Aluno aluno = new Aluno();
        aluno.setNome("João");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        // Não seta a matrícula
        Assert.assertThrows(ConstraintViolationException.class, () -> {
            this.serviceAluno.save(aluno);
        });
    }

    @Test
    public void alterarAluno() {
        Aluno aluno = new Aluno();
        aluno.setNome("Carlos");
        aluno.setTurno(Turno.MATUTINO);
        aluno.setCurso(Curso.INFORMATICA);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("111222");
        this.serviceAluno.save(aluno);

        aluno.setNome("Carlos Silva");
        aluno.setStatus(Status.INATIVO);
        this.serviceAluno.save(aluno);


        Aluno atualizado = this.serviceAluno.getById(aluno.getId());
        Assert.assertEquals("Carlos Silva", atualizado.getNome());
        Assert.assertEquals(Status.INATIVO, atualizado.getStatus());
        }
    

    @Test
    public void salvarNomeMuitoCurto() {
        Aluno aluno = new Aluno();
        aluno.setNome("z"); // Nome muito curto
        aluno.setTurno(Turno.MATUTINO);
        aluno.setCurso(Curso.INFORMATICA);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("333444");
        Assert.assertThrows(ConstraintViolationException.class, () -> {
            this.serviceAluno.save(aluno);
        });
    }
}