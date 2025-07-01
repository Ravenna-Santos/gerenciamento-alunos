package br.com.gerenciamento.repository;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;

import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.enums.Turno;
import br.com.gerenciamento.model.Aluno;

import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class AlunoRepositoryTest {

    @Autowired
    AlunoRepository repositoryAluno;

    @Test
    public void deveSalvarAluno() {
        Aluno aluno = new Aluno();
        aluno.setNome("Maria");
        aluno.setTurno(Turno.MATUTINO);
        aluno.setCurso(Curso.INFORMATICA);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("654321");
        Aluno alunoSalvo = this.repositoryAluno.save(aluno);
        Assert.assertNotNull(alunoSalvo.getId());
        Optional<Aluno> alunoBuscado = this.repositoryAluno.findById(alunoSalvo.getId());
        Assert.assertTrue(alunoBuscado.isPresent());
        Assert.assertEquals("Maria", alunoBuscado.get().getNome());
    }

    @Test
    public void deveBuscarAlunosPorNomeIgnorandoCase() {
        Aluno aluno = new Aluno();
        aluno.setNome("João da Silva");
        aluno.setTurno(Turno.MATUTINO);
        aluno.setCurso(Curso.INFORMATICA);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("123456");
        this.repositoryAluno.save(aluno);

        List<Aluno> resultado = this.repositoryAluno.findByNomeContainingIgnoreCase("joão");
        Assert.assertFalse(resultado.isEmpty());
        Assert.assertEquals("João da Silva", resultado.get(0).getNome());
    }

    @Test
    public void deveBuscarAlunosAtivos() {
        Aluno aluno = new Aluno();
        aluno.setNome("Carlos");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.INFORMATICA);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("789101");
        this.repositoryAluno.save(aluno);

        List<Aluno> ativos = this.repositoryAluno.findByStatusAtivo();
        Assert.assertFalse(ativos.isEmpty());
        Assert.assertTrue(ativos.stream().anyMatch(a -> "Carlos".equals(a.getNome())));
    }

    @Test
    public void deveBuscarAlunosInativos() {
        Aluno aluno = new Aluno();
        aluno.setNome("Ana Lúcia");
        aluno.setTurno(Turno.MATUTINO);
        aluno.setCurso(Curso.INFORMATICA);
        aluno.setStatus(Status.INATIVO);
        aluno.setMatricula("111213");
        this.repositoryAluno.save(aluno);

        List<Aluno> inativos = this.repositoryAluno.findByStatusInativo();
        Assert.assertFalse(inativos.isEmpty());
        Assert.assertTrue(inativos.stream().anyMatch(a -> "Ana Lúcia".equals(a.getNome())));
    }
}

