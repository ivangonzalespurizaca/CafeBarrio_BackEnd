package com.cafeteria.api.service.impl;

import com.cafeteria.api.dto.CategoriaDTO;
import com.cafeteria.api.mapper.CategoriaMapper;
import com.cafeteria.api.repository.CategoriaRepository;
import com.cafeteria.api.service.CategoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriaServiceImpl implements CategoriaService {
    private final CategoriaRepository categoriaRepository;
    private final CategoriaMapper categoriaMapper;

    @Override
    public List<CategoriaDTO> listarTodas() {
        return categoriaRepository.findAll().stream()
                .map(categoriaMapper::toDto)
                .toList();
    }
}
