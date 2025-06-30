package br.com.gerenciamento.service;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.gerenciamento.exception.EmailExistsException;
import br.com.gerenciamento.model.Usuario;
import jakarta.validation.ConstraintViolationException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsuarioServiceTest {
        @Autowired
        private ServiceUsuario usuarioService;

    @Test
    public void salvarUsuario() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setUser("Joãozito");
        usuario.setSenha("1234");
        usuario.setEmail("joao@email.com");
        usuarioService.salvarUsuario(usuario);

        Usuario salvo = usuarioService.loginUser(usuario.getUser(), usuario.getSenha());
        Assert.assertNotNull(salvo);
        Assert.assertEquals("Joãozito", salvo.getUser());
        Assert.assertEquals("joao@email.com", salvo.getEmail());
    }


    @Test
    public void naoDevePermitirEmailDuplicado() throws Exception {
        Usuario usuario1 = new Usuario();
        usuario1.setUser("Usuario1");
        usuario1.setSenha("senha123");
        usuario1.setEmail("email@teste.com");
        usuarioService.salvarUsuario(usuario1);

        Usuario usuario2 = new Usuario();
        usuario2.setUser("Usuario2");
        usuario2.setSenha("senha456");
        usuario2.setEmail("email@teste.com"); // mesmo e-mail

        Assert.assertThrows(EmailExistsException.class, () -> {
            usuarioService.salvarUsuario(usuario2);
        });
}



    @Test
    public void naoDeveLogarComSenhaIncorreta() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setUser("TesteUser");
        usuario.setSenha("senhaCorreta");
        usuario.setEmail("teste@email.com");
        usuarioService.salvarUsuario(usuario);

        Usuario resultado = usuarioService.loginUser(usuario.getUser(), "senhaErrada");
        Assert.assertNull(resultado); 
}

@Test
public void deveLogarComSenhaCorreta() throws Exception {
    Usuario usuario = new Usuario();
    usuario.setUser("UsuarioTeste");
    usuario.setSenha("senha123");
    usuario.setEmail("usuario@email.com");
    usuarioService.salvarUsuario(usuario);

    Usuario resultado = usuarioService.loginUser(usuario.getUser(), usuario.getSenha());
    Assert.assertEquals("UsuarioTeste", resultado.getUser());
    Assert.assertEquals("usuario@email.com", resultado.getEmail());
}


}
