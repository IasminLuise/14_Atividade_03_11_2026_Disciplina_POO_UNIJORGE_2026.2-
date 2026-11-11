package com.academico.controller;

import com.academico.model.Aluno;
import com.academico.model.Disciplina;
import com.academico.repository.AlunoRepository;
import com.academico.repository.DisciplinaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@RestController
@RequestMapping("/alunos")
public class AlunoController {
    private final AlunoRepository alunoRepository;
    private final DisciplinaRepository disciplinaRepository;

    public AlunoController(AlunoRepository alunoRepository, DisciplinaRepository disciplinaRepository) {
        this.alunoRepository = alunoRepository;
        this.disciplinaRepository = disciplinaRepository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Aluno cadastrar(@RequestBody Aluno aluno) {
        aluno.setDisciplinas(new java.util.HashSet<>());
        return alunoRepository.save(aluno);
    }

    @GetMapping
    public List<Aluno> listar() {
        return alunoRepository.findAll();
    }

    @GetMapping("/{id}")
    public Aluno buscar(@PathVariable Long id) {
        return alunoRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aluno não encontrado"));
    }

    @PutMapping("/{id}")
    public Aluno atualizar(@PathVariable Long id, @RequestBody Aluno dados) {
        Aluno aluno = buscar(id);
        aluno.setNome(dados.getNome());
        aluno.setEmail(dados.getEmail());
        return alunoRepository.save(aluno);
    }

    @PostMapping("/{id}/disciplinas/{disciplinaId}")
    public Aluno matricular(@PathVariable Long id, @PathVariable Long disciplinaId) {
        Aluno aluno = buscar(id);
        Disciplina disciplina = disciplinaRepository.findById(disciplinaId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Disciplina não encontrada"));
        aluno.getDisciplinas().add(disciplina);
        return alunoRepository.save(aluno);
    }

    @DeleteMapping("/{id}/disciplinas/{disciplinaId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelarMatricula(@PathVariable Long id, @PathVariable Long disciplinaId) {
        Aluno aluno = buscar(id);
        Disciplina disciplina = disciplinaRepository.findById(disciplinaId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Disciplina não encontrada"));
        aluno.getDisciplinas().remove(disciplina);
        alunoRepository.save(aluno);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        alunoRepository.delete(buscar(id));
    }
}
