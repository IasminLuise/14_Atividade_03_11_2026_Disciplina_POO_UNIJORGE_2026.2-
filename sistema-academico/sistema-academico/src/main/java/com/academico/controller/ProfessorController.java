package com.academico.controller;

import com.academico.model.Professor;
import com.academico.repository.ProfessorRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@RestController
@RequestMapping("/professores")
public class ProfessorController {
    private final ProfessorRepository repository;

    public ProfessorController(ProfessorRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Professor cadastrar(@RequestBody Professor professor) {
        return repository.save(professor);
    }

    @GetMapping
    public List<Professor> listar() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Professor buscar(@PathVariable Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Professor não encontrado"));
    }

    @PutMapping("/{id}")
    public Professor atualizar(@PathVariable Long id, @RequestBody Professor dados) {
        Professor professor = buscar(id);
        professor.setNome(dados.getNome());
        professor.setEmail(dados.getEmail());
        professor.setPerfil(dados.getPerfil());
        return repository.save(professor);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        repository.delete(buscar(id));
    }
}
