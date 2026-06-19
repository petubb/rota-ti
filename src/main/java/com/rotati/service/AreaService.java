package com.rotati.service;

import com.rotati.model.AreaTi;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class AreaService {

    public List<AreaTi> listarAreas() {
        return Arrays.asList(AreaTi.values());
    }

    public AreaTi buscarPorSlug(String slug) {
        return AreaTi.fromSlug(slug)
                .orElseThrow(() -> new NoSuchElementException("Area nao encontrada: " + slug));
    }
}
