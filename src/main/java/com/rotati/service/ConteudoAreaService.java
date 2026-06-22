package com.rotati.service;

import com.rotati.dto.ConteudoAreaView;
import com.rotati.dto.PersonalidadeView;
import com.rotati.dto.VideoRecomendado;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ConteudoAreaService {

    private final Map<String, ConteudoAreaView> conteudos = Map.ofEntries(
            Map.entry("desenvolvimento-software", new ConteudoAreaView(
                    List.of(video(
                            "Introdu\u00e7\u00e3o a Algoritmos - Curso de Algoritmos #01",
                            "Curso em V\u00eddeo",
                            "8mei6uVttho"
                    )),
                    List.of(
                            pessoa(
                                    "Grace Hopper",
                                    "Pioneira da programa\u00e7\u00e3o",
                                    "Criou um dos primeiros compiladores e ajudou a aproximar as linguagens de programa\u00e7\u00e3o da linguagem humana.",
                                    "https://www.britannica.com/biography/Grace-Hopper",
                                    "GH"
                            ),
                            pessoa(
                                    "James Gosling",
                                    "Criador da linguagem Java",
                                    "Liderou a cria\u00e7\u00e3o do Java, linguagem que continua presente em sistemas, aplicativos e servi\u00e7os no mundo todo.",
                                    "https://en.wikipedia.org/wiki/James_Gosling",
                                    "JG"
                            )
                    )
            )),
            Map.entry("dados-bi", new ConteudoAreaView(
                    List.of(video(
                            "Como come\u00e7ar do zero uma carreira de dados hoje",
                            "Alura",
                            "-61pNJVWOxI"
                    )),
                    List.of(
                            pessoa(
                                    "Florence Nightingale",
                                    "Pioneira da visualiza\u00e7\u00e3o de dados",
                                    "Usou estat\u00edstica e gr\u00e1ficos para tornar problemas de sa\u00fade vis\u00edveis e apoiar decis\u00f5es p\u00fablicas.",
                                    "https://www.britannica.com/biography/Florence-Nightingale",
                                    "FN"
                            ),
                            pessoa(
                                    "Hans Rosling",
                                    "M\u00e9dico e comunicador de dados",
                                    "Ficou conhecido por transformar grandes conjuntos de dados globais em explica\u00e7\u00f5es claras e acess\u00edveis.",
                                    "https://www.gapminder.org/about-gapminder/",
                                    "HR"
                            )
                    )
            )),
            Map.entry("seguranca-cibernetica", new ConteudoAreaView(
                    List.of(video(
                            "Como \u00e9 o mercado de Seguran\u00e7a da Informa\u00e7\u00e3o e Defesa Cibern\u00e9tica?",
                            "Alura",
                            "XOzgxoHR6gc"
                    )),
                    List.of(
                            pessoa(
                                    "Katie Moussouris",
                                    "Especialista em seguran\u00e7a cibern\u00e9tica",
                                    "Refer\u00eancia em divulga\u00e7\u00e3o coordenada de vulnerabilidades e programas de recompensas por falhas de seguran\u00e7a.",
                                    "https://www.lutasecurity.com/about",
                                    "KM"
                            ),
                            pessoa(
                                    "Mikko Hypp\u00f6nen",
                                    "Pesquisador de amea\u00e7as digitais",
                                    "Pesquisa malwares, privacidade e a evolu\u00e7\u00e3o das amea\u00e7as online, levando o tema para o grande p\u00fablico.",
                                    "https://mikko.com/",
                                    "MH"
                            )
                    )
            )),
            Map.entry("infraestrutura-redes", new ConteudoAreaView(
                    List.of(video(
                            "Como vai ser o Curso de Redes? - Curso Redes #01",
                            "Curso em V\u00eddeo",
                            "QkMbqL8QD9w"
                    )),
                    List.of(
                            pessoa(
                                    "Radia Perlman",
                                    "Engenheira de redes",
                                    "Criou tecnologias fundamentais para o funcionamento confi\u00e1vel de redes e ficou conhecida como uma das m\u00e3es da internet.",
                                    "https://www.internethalloffame.org/inductee/radia-perlman/",
                                    "RP"
                            ),
                            pessoa(
                                    "Vint Cerf",
                                    "Pioneiro da internet",
                                    "Participou da cria\u00e7\u00e3o dos protocolos TCP/IP, base para computadores e redes se comunicarem pela internet.",
                                    "https://www.internethalloffame.org/inductee/vint-cerf/",
                                    "VC"
                            )
                    )
            )),
            Map.entry("ux-ui-design", new ConteudoAreaView(
                    List.of(video(
                            "Como come\u00e7ar na carreira de UX Design: 6 dicas pr\u00e1ticas",
                            "Alura",
                            "b1YTvFMBFso"
                    )),
                    List.of(
                            pessoa(
                                    "Don Norman",
                                    "Pesquisador de design centrado nas pessoas",
                                    "Popularizou ideias essenciais de experi\u00eancia do usu\u00e1rio e defende produtos compreens\u00edveis, \u00fateis e humanos.",
                                    "https://www.nngroup.com/people/don-norman/",
                                    "DN"
                            ),
                            pessoa(
                                    "Susan Kare",
                                    "Designer de interfaces e \u00edcones",
                                    "Criou \u00edcones marcantes das primeiras interfaces gr\u00e1ficas e ajudou a tornar computadores mais amig\u00e1veis.",
                                    "https://kare.com/",
                                    "SK"
                            )
                    )
            )),
            Map.entry("game-design", new ConteudoAreaView(
                    List.of(video(
                            "Como trabalhar com jogos no Brasil",
                            "Andr\u00e9 Young",
                            "YegFGkXyMhQ"
                    )),
                    List.of(
                            pessoa(
                                    "Shigeru Miyamoto",
                                    "Designer e produtor de jogos",
                                    "Criou personagens e experi\u00eancias que transformaram o design de jogos e influenciaram gera\u00e7\u00f5es de profissionais.",
                                    "https://en.wikipedia.org/wiki/Shigeru_Miyamoto",
                                    "SM"
                            ),
                            pessoa(
                                    "Hideo Kojima",
                                    "Diretor e designer de jogos",
                                    "\u00c9 reconhecido por combinar narrativa, dire\u00e7\u00e3o cinematogr\u00e1fica e mec\u00e2nicas experimentais em seus projetos.",
                                    "https://www.kojimaproductions.jp/en/company",
                                    "HK"
                            )
                    )
            )),
            Map.entry("inteligencia-artificial", new ConteudoAreaView(
                    List.of(video(
                            "Guia de Carreira: Intelig\u00eancia Artificial",
                            "Alura",
                            "5QzXPjqQt7E"
                    )),
                    List.of(
                            pessoa(
                                    "Fei-Fei Li",
                                    "Pesquisadora de intelig\u00eancia artificial",
                                    "Refer\u00eancia em vis\u00e3o computacional e defensora de uma intelig\u00eancia artificial centrada nas pessoas.",
                                    "https://profiles.stanford.edu/fei-fei-li",
                                    "FL"
                            ),
                            pessoa(
                                    "Andrew Ng",
                                    "Pesquisador e educador em IA",
                                    "Tornou o aprendizado de m\u00e1quina mais acess\u00edvel por meio de cursos e projetos educacionais de alcance global.",
                                    "https://www.andrewng.org/",
                                    "AN"
                            )
                    )
            )),
            Map.entry("gestao-ti", new ConteudoAreaView(
                    List.of(video(
                            "O papel do Gestor de TI",
                            "Papo Cloud",
                            "MPJuHNftYiE"
                    )),
                    List.of(
                            pessoa(
                                    "Marty Cagan",
                                    "Especialista em gest\u00e3o de produto",
                                    "Compartilha pr\u00e1ticas para equipes de tecnologia descobrirem e entregarem produtos digitais com valor real.",
                                    "https://www.svpg.com/team/marty-cagan/",
                                    "MC"
                            ),
                            pessoa(
                                    "Satya Nadella",
                                    "Executivo de tecnologia",
                                    "Tornou-se refer\u00eancia em lideran\u00e7a, transforma\u00e7\u00e3o cultural e estrat\u00e9gia em uma grande empresa de tecnologia.",
                                    "https://news.microsoft.com/exec/satya-nadella/",
                                    "SN"
                            )
                    )
            ))
    );

    private final List<PersonalidadeView> destaquesRondonia = List.of(
            new PersonalidadeView(
                    "Itamar Alves",
                    "Analista de Seguran\u00e7a da Informa\u00e7\u00e3o e DevSecOps",
                    "Profissional da TI Networks com forma\u00e7\u00e3o no IFRO e atua\u00e7\u00e3o voltada \u00e0 seguran\u00e7a de sistemas.",
                    "https://br.linkedin.com/in/itamaralves",
                    "Ver LinkedIn",
                    "/images/pessoas/itamar-alves.png",
                    "IA",
                    "Rond\u00f4nia"
            ),
            new PersonalidadeView(
                    "Keynes Fernandes",
                    "Fundador da Dotum Software House",
                    "Programador e empreendedor de software, atua na Dotum desde 2008 e estudou Sistemas de Informa\u00e7\u00e3o na UNESC.",
                    "https://dotum.com.br/",
                    "Conhecer a Dotum",
                    "/images/pessoas/keynes-fernandes.png",
                    "KF",
                    "Rond\u00f4nia"
            )
    );

    public ConteudoAreaView buscarPorSlug(String slug) {
        ConteudoAreaView conteudo = conteudos.get(slug);
        if (conteudo == null) {
            throw new IllegalArgumentException("Conteudo nao encontrado para a area: " + slug);
        }
        return conteudo;
    }

    public List<PersonalidadeView> listarDestaquesRondonia() {
        return destaquesRondonia;
    }

    private static VideoRecomendado video(String titulo, String canal, String videoId) {
        return new VideoRecomendado(titulo, canal, videoId);
    }

    private static PersonalidadeView pessoa(
            String nome,
            String atuacao,
            String descricao,
            String url,
            String iniciais
    ) {
        return new PersonalidadeView(nome, atuacao, descricao, url, "Conhecer trajet\u00f3ria", null, iniciais, null);
    }
}
