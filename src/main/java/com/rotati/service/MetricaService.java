package com.rotati.service;

import com.rotati.dto.DashboardMetricas;
import com.rotati.model.AreaTi;
import com.rotati.model.Resultado;
import com.rotati.repository.ResultadoRepository;
import com.rotati.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MetricaService {

    private final UsuarioRepository usuarioRepository;
    private final ResultadoRepository resultadoRepository;

    public MetricaService(UsuarioRepository usuarioRepository, ResultadoRepository resultadoRepository) {
        this.usuarioRepository = usuarioRepository;
        this.resultadoRepository = resultadoRepository;
    }

    public DashboardMetricas gerarDashboard() {
        List<Resultado> resultados = resultadoRepository.findAll();

        Map<String, Long> distribuicao = resultados.stream()
                .collect(Collectors.groupingBy(Resultado::getAreaSlug, Collectors.counting()));

        Map<String, Long> distribuicaoComTitulos = new LinkedHashMap<>();
        for (AreaTi area : AreaTi.values()) {
            distribuicaoComTitulos.put(area.getTitulo(), distribuicao.getOrDefault(area.getSlug(), 0L));
        }

        double mediaSatisfacao = resultados.stream()
                .filter(resultado -> resultado.getSatisfacao() != null)
                .mapToInt(Resultado::getSatisfacao)
                .average()
                .orElse(0.0);

        return new DashboardMetricas(
                usuarioRepository.count(),
                resultadoRepository.count(),
                mediaSatisfacao,
                distribuicaoComTitulos
        );
    }
}
