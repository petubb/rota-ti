package com.rotati;

import com.rotati.dto.CadastroForm;
import com.rotati.dto.QuizSubmission;
import com.rotati.model.Conta;
import com.rotati.model.Resultado;
import com.rotati.model.TokenRecuperacaoSenha;
import com.rotati.model.Usuario;
import com.rotati.repository.ContaRepository;
import com.rotati.repository.ResultadoRepository;
import com.rotati.repository.TokenRecuperacaoSenhaRepository;
import com.rotati.repository.UsuarioRepository;
import com.rotati.security.ContaPrincipal;
import com.rotati.service.ContaService;
import com.rotati.service.ResultadoContaService;
import com.rotati.service.ResultadoNaoEncontradoException;
import com.rotati.service.QuizService;
import com.rotati.service.RecuperacaoSenhaService;
import com.rotati.service.TokenRecuperacaoException;
import com.rotati.service.TokenSeguroService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.UUID;
import java.util.HashMap;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@Transactional
class AutenticacaoSegurancaTests {

    private static final String SENHA_FORTE = "RotaSegura123!";

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ContaService contaService;

    @Autowired
    private ContaRepository contaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ResultadoRepository resultadoRepository;

    @Autowired
    private ResultadoContaService resultadoContaService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private QuizService quizService;

    @Autowired
    private RecuperacaoSenhaService recuperacaoSenhaService;

    @Autowired
    private TokenRecuperacaoSenhaRepository tokenRecuperacaoRepository;

    @Autowired
    private TokenSeguroService tokenSeguroService;

    @BeforeEach
    void configurarMockMvc() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    void quizPermanecePublicoEEnviaCabecalhosDeSeguranca() throws Exception {
        mockMvc.perform(get("/quiz"))
                .andExpect(status().isOk())
                .andExpect(header().string("X-Content-Type-Options", "nosniff"))
                .andExpect(header().string("X-Frame-Options", "DENY"))
                .andExpect(header().string("Content-Security-Policy",
                        org.hamcrest.Matchers.containsString("frame-ancestors 'none'")));
    }

    @Test
    void areaDaContaExigeLoginEPostSemCsrfERecusado() throws Exception {
        mockMvc.perform(get("/minha-conta/resultados"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/entrar"));

        mockMvc.perform(post("/cadastro")
                        .param("nome", "Pessoa Teste")
                        .param("email", emailUnico())
                        .param("senha", SENHA_FORTE)
                        .param("confirmarSenha", SENHA_FORTE))
                .andExpect(status().isForbidden());
    }

    @Test
    void cadastroArmazenaHashBcryptENuncaASenhaOriginal() throws Exception {
        String email = emailUnico();

        mockMvc.perform(post("/cadastro")
                        .with(csrf())
                        .param("nome", "Pessoa Teste")
                        .param("email", email)
                        .param("senha", SENHA_FORTE)
                        .param("confirmarSenha", SENHA_FORTE))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/minha-conta/resultados"));

        Conta conta = contaRepository.findByEmailIgnoreCase(email).orElseThrow();
        assertThat(conta.getSenhaHash())
                .isNotEqualTo(SENHA_FORTE)
                .matches("^\\$2[aby]\\$12\\$.*");
        assertThat(passwordEncoder.matches(SENHA_FORTE, conta.getSenhaHash())).isTrue();
    }

    @Test
    void senhaFracaNaoCriaConta() throws Exception {
        String email = emailUnico();

        mockMvc.perform(post("/cadastro")
                        .with(csrf())
                        .param("nome", "Pessoa Teste")
                        .param("email", email)
                        .param("senha", "senhafraca123")
                        .param("confirmarSenha", "senhafraca123"))
                .andExpect(status().isOk())
                .andExpect(view().name("auth/cadastro"));

        assertThat(contaRepository.existsByEmailIgnoreCase(email)).isFalse();
    }

    @Test
    void contaComumNaoConsegueAbrirDashboard() throws Exception {
        Conta conta = contaService.criarConta(novoCadastro(emailUnico()));

        mockMvc.perform(get("/dashboard").with(user(new ContaPrincipal(conta))))
                .andExpect(status().isForbidden());
    }

    @Test
    void cincoFalhasBloqueiamMesmoQuandoASenhaSeguinteEstaCorreta() throws Exception {
        String email = emailUnico();
        contaService.criarConta(novoCadastro(email));

        for (int tentativa = 0; tentativa < ContaService.LIMITE_TENTATIVAS; tentativa++) {
            contaService.registrarFalhaLogin(email);
        }

        Conta bloqueada = contaRepository.findByEmailIgnoreCase(email).orElseThrow();
        assertThat(bloqueada.estaBloqueada()).isTrue();
        assertThat(bloqueada.getTentativasFalhas()).isEqualTo(ContaService.LIMITE_TENTATIVAS);

        mockMvc.perform(post("/entrar")
                        .with(csrf())
                        .param("email", email)
                        .param("senha", SENHA_FORTE))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/entrar?erro"));
    }

    @Test
    void resultadoAnonimoPertenceASessaoEContaSalvaPertenceAoDono() {
        Usuario usuario = usuarioRepository.save(new Usuario(18, "Escola Teste"));
        Resultado resultado = resultadoRepository.save(new Resultado(usuario, "desenvolvimento", 82.0));
        MockHttpSession sessaoCriadora = new MockHttpSession();
        MockHttpSession outraSessao = new MockHttpSession();

        resultadoContaService.registrarResultadoGerado(resultado, null, sessaoCriadora);
        resultadoContaService.exigirAcesso(resultado.getId(), null, sessaoCriadora);
        assertThatThrownBy(() -> resultadoContaService.exigirAcesso(resultado.getId(), null, outraSessao))
                .isInstanceOf(ResultadoNaoEncontradoException.class);

        Conta dono = contaService.criarConta(novoCadastro(emailUnico()));
        Conta intruso = contaService.criarConta(novoCadastro(emailUnico()));
        resultadoContaService.salvarResultado(resultado.getId(), new ContaPrincipal(dono), sessaoCriadora);

        resultadoContaService.exigirAcesso(resultado.getId(), new ContaPrincipal(dono), sessaoCriadora);
        assertThatThrownBy(() -> resultadoContaService.exigirAcesso(
                resultado.getId(), new ContaPrincipal(intruso), sessaoCriadora
        )).isInstanceOf(ResultadoNaoEncontradoException.class);
    }

    @Test
    void paginaDoResultadoSoRenderizaNaSessaoCriadora() throws Exception {
        QuizSubmission submission = new QuizSubmission();
        submission.setIdade(18);
        submission.setEscola("Escola Teste");
        submission.setRespostas(new HashMap<>());
        quizService.listarPerguntas().forEach(pergunta -> submission.getRespostas().put(pergunta.getId(), 0));

        Resultado resultado = quizService.processar(submission);
        MockHttpSession sessaoCriadora = new MockHttpSession();
        resultadoContaService.registrarResultadoGerado(resultado, null, sessaoCriadora);

        mockMvc.perform(get("/resultado/{id}", resultado.getId()).session(sessaoCriadora))
                .andExpect(status().isOk())
                .andExpect(view().name("resultado"));

        mockMvc.perform(get("/resultado/{id}", resultado.getId()).session(new MockHttpSession()))
                .andExpect(status().isNotFound());

        mockMvc.perform(post("/resultado/{id}/satisfacao", resultado.getId())
                        .session(sessaoCriadora)
                        .with(csrf())
                        .param("satisfacao", "9"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void recuperacaoNaoRevelaSeEmailExisteELimitaReenvio() throws Exception {
        String emailExistente = emailUnico();
        Conta conta = contaService.criarConta(novoCadastro(emailExistente));

        mockMvc.perform(post("/esqueci-senha").param("email", emailExistente))
                .andExpect(status().isForbidden());

        mockMvc.perform(post("/esqueci-senha").with(csrf()).param("email", emailUnico()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/esqueci-senha"));

        mockMvc.perform(post("/esqueci-senha").with(csrf()).param("email", emailExistente))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/esqueci-senha"));
        recuperacaoSenhaService.solicitar(emailExistente);

        long tokensDaConta = tokenRecuperacaoRepository.findAll().stream()
                .filter(token -> token.getConta().getId().equals(conta.getId()))
                .count();
        assertThat(tokensDaConta).isEqualTo(1);
    }

    @Test
    void tokenValidoTrocaSenhaUmaVezEInvalidaSessaoAntiga() throws Exception {
        Conta conta = contaService.criarConta(novoCadastro(emailUnico()));
        ContaPrincipal principalAntigo = new ContaPrincipal(conta);
        String token = "token-seguro-para-teste";
        TokenRecuperacaoSenha recuperacao = tokenRecuperacaoRepository.save(new TokenRecuperacaoSenha(
                conta,
                tokenSeguroService.hash(token),
                LocalDateTime.now().plusMinutes(15)
        ));

        mockMvc.perform(get("/recuperar-senha").param("token", token))
                .andExpect(status().isOk())
                .andExpect(view().name("auth/redefinir-senha"));

        mockMvc.perform(post("/recuperar-senha")
                        .with(csrf())
                        .param("token", token)
                        .param("senha", "NovaRotaSegura456!")
                        .param("confirmarSenha", "NovaRotaSegura456!"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/entrar?senha-alterada"));

        Conta atualizada = contaRepository.findById(conta.getId()).orElseThrow();
        assertThat(passwordEncoder.matches(SENHA_FORTE, atualizada.getSenhaHash())).isFalse();
        assertThat(passwordEncoder.matches("NovaRotaSegura456!", atualizada.getSenhaHash())).isTrue();
        assertThat(atualizada.getVersaoCredenciais()).isEqualTo(1);
        assertThat(recuperacao.getUsadoEm()).isNotNull();
        assertThatThrownBy(() -> recuperacaoSenhaService.redefinir(
                token, "OutraRotaSegura789!", "OutraRotaSegura789!"
        )).isInstanceOf(TokenRecuperacaoException.class);

        mockMvc.perform(get("/minha-conta/resultados").with(user(principalAntigo)))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/entrar"));
    }

    @Test
    void tokenExpiradoNaoAbreFormularioNemTrocaSenha() throws Exception {
        Conta conta = contaService.criarConta(novoCadastro(emailUnico()));
        String token = "token-expirado-para-teste";
        tokenRecuperacaoRepository.save(new TokenRecuperacaoSenha(
                conta,
                tokenSeguroService.hash(token),
                LocalDateTime.now().minusMinutes(1)
        ));

        mockMvc.perform(get("/recuperar-senha").param("token", token))
                .andExpect(status().isOk())
                .andExpect(view().name("auth/token-invalido"))
                .andExpect(header().string("Cache-Control", org.hamcrest.Matchers.containsString("no-store")));

        assertThatThrownBy(() -> recuperacaoSenhaService.redefinir(
                token, "NovaRotaSegura456!", "NovaRotaSegura456!"
        )).isInstanceOf(TokenRecuperacaoException.class);
        assertThat(passwordEncoder.matches(SENHA_FORTE, conta.getSenhaHash())).isTrue();
    }

    private CadastroForm novoCadastro(String email) {
        CadastroForm form = new CadastroForm();
        form.setNome("Pessoa Teste");
        form.setEmail(email);
        form.setSenha(SENHA_FORTE);
        form.setConfirmarSenha(SENHA_FORTE);
        return form;
    }

    private String emailUnico() {
        return "teste-" + UUID.randomUUID() + "@rotati.local";
    }
}
