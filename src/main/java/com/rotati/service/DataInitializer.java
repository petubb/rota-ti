package com.rotati.service;

import com.rotati.model.AreaTi;
import com.rotati.model.Pergunta;
import com.rotati.repository.PerguntaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final PerguntaRepository perguntaRepository;

    public DataInitializer(PerguntaRepository perguntaRepository) {
        this.perguntaRepository = perguntaRepository;
    }

    @Override
    public void run(String... args) {
        if (perguntaRepository.count() > 0) {
            return;
        }

        perguntaRepository.saveAll(List.of(
                new Pergunta("Voce gosta de criar sites, aplicativos ou sistemas?", "criatividade", AreaTi.DESENVOLVIMENTO.getSlug()),
                new Pergunta("Voce se sente bem resolvendo problemas logicos passo a passo?", "logica", AreaTi.DESENVOLVIMENTO.getSlug()),
                new Pergunta("Voce gosta de organizar dados e transformar informacoes em relatorios?", "analise", AreaTi.DADOS.getSlug()),
                new Pergunta("Voce percebe padroes em numeros, tabelas ou graficos com facilidade?", "analise", AreaTi.DADOS.getSlug()),
                new Pergunta("Voce se interessa por investigar falhas, golpes digitais ou vulnerabilidades?", "investigacao", AreaTi.SEGURANCA.getSlug()),
                new Pergunta("Voce tem atencao aos detalhes quando algo parece fora do normal?", "detalhe", AreaTi.SEGURANCA.getSlug()),
                new Pergunta("Voce gosta de configurar computadores, redes ou equipamentos?", "operacao", AreaTi.INFRAESTRUTURA.getSlug()),
                new Pergunta("Voce prefere manter sistemas funcionando e organizados?", "organizacao", AreaTi.INFRAESTRUTURA.getSlug()),
                new Pergunta("Voce gosta de pensar em telas bonitas, simples e faceis de usar?", "design", AreaTi.UX_UI.getSlug()),
                new Pergunta("Voce costuma se colocar no lugar das pessoas para melhorar uma experiencia?", "empatia", AreaTi.UX_UI.getSlug()),
                new Pergunta("Voce gosta de imaginar mundos, personagens, fases ou regras de jogos?", "narrativa", AreaTi.GAME_DESIGN.getSlug()),
                new Pergunta("Voce tem curiosidade sobre inteligencia artificial, automacao e modelos que aprendem com dados?", "curiosidade", AreaTi.IA.getSlug()),
                new Pergunta("Voce gosta de liderar, organizar tarefas e ajudar pessoas a trabalharem melhor em equipe?", "lideranca", AreaTi.GESTAO.getSlug())
        ));
    }
}
