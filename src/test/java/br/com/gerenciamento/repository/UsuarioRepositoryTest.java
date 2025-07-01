package br.com.gerenciamento.repository;

import br.com.gerenciamento.model.Usuario;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest

public class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    public void deveSalvarUsuario() {
        Usuario usuario = new Usuario();
        usuario.setUser("admin");
        usuario.setSenha("123456");
        usuario.setEmail("admin@email.com");
        Usuario salvo = usuarioRepository.save(usuario);
        Assert.assertNotNull(salvo.getId());
    }

    @Test
    public void deveBuscarUsuarioPorEmail() {
        Usuario usuario = new Usuario();
        usuario.setUser("joao");
        usuario.setSenha("senha123");
        usuario.setEmail("joao@email.com");
        usuarioRepository.save(usuario);

        Usuario encontrado = usuarioRepository.findByEmail("joao@email.com");
        Assert.assertNotNull(encontrado);
        Assert.assertEquals("joao", encontrado.getUser());
    }

    @Test
    public void deveBuscarUsuarioPorLogin() {
        Usuario usuario = new Usuario();
        usuario.setUser("maria");
        usuario.setSenha("senha456");
        usuario.setEmail("maria@email.com");
        usuarioRepository.save(usuario);

        Usuario encontrado = usuarioRepository.buscarLogin("maria", "senha456");
        Assert.assertNotNull(encontrado);
        Assert.assertEquals("maria", encontrado.getUser());
    }

    @Test
    public void naoDeveEncontrarUsuarioComLoginIncorreto() {
        Usuario usuario = new Usuario();
        usuario.setUser("carlos");
        usuario.setSenha("senha789");
        usuario.setEmail("carlos@email.com");
        usuarioRepository.save(usuario);

        Usuario encontrado = usuarioRepository.buscarLogin("carlos", "senhaErrada");
        Assert.assertNull(encontrado);
    }
}
