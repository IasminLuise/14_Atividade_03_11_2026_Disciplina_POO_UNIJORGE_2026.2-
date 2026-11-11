package com.academico.controller;

import com.academico.model.Curso;
import com.academico.model.Disciplina;
import com.academico.repository.CursoRepository;
import com.academico.repository.DisciplinaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@RestController
@RequestMapping("/disciplinas")
public class DisciplinaController {
    private final DisciplinaRepository disciplinaRepository;
    private final CursoRepository cursoRepository;

    public DisciplinaController(DisciplinaRepository disciplinaRepository, CursoRepository cursoRepository) {
        this.disciplinaRepository = disciplinaRepository;
        this.cursoRepository = cursoRepository;
    }

    public record DisciplinaRequest(String nome, Integer cargaHoraria, Long cursoId) {}

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Disciplina cadastrar(@RequestBody DisciplinaRequest dados) {
        Curso curso = cursoRepository.findById(dados.cursoId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Curso não encontrado"));
        Disciplina disciplina = new Disciplina();
        disciplina.setNome(dados.nome());
        disciplina.setCargaHoraria(dados.cargaHoraria());
        disciplina.setCurso(curso);
        return disciplinaRepository.save(disciplina);
    }

    @GetMapping
    public List<Disciplina> listar() {
        return disciplinaRepository.findAll();
    }

    @GetMapping("/{id}")
    public Disciplina buscar(@PathVariable Long id) {
        return disciplinaRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Disciplina não encontrada"));
    }

    @PutMapping("/{id}")
    public Disciplina atualizar(@PathVariable Long id, @RequestBody DisciplinaRequest dados) {
        Disciplina disciplina = buscar(id);
        Curso curso = cursoRepository.findById(dados.cursoId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Curso não encontrado"));
        disciplina.setNome(dados.nome());
        disciplina.setCargaHoraria(dados.cargaHoraria());
        disciplina.setCurso(curso);
        return disciplinaRepository.save(disciplina);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        disciplinaRepository.delete(buscar(id));
    }
}
