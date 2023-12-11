package br.com.academify2.resource;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.academify2.controller.AlunoController;
import br.com.academify2.model.Aluno;
import br.com.academify2.repository.AlunoRepository;

@RestController
@RequestMapping(value = "/api/aluno")
public class AlunoResource {

    @Autowired
    private AlunoRepository alunoRepository;

    private ResponseEntity<Aluno> responseEntity;

    @GetMapping("/listar")
    public List<Aluno> listar() {
        return alunoRepository.findAll();
    }

    @GetMapping("get/{id}")
    public Aluno get(@PathVariable(value = "id") long id) {
        return alunoRepository.findById(id);
    }

    @PostMapping("/incluir")
    public ResponseEntity<?> incluir(@RequestBody Aluno aluno) {
        if (validarAluno(aluno)) {
            aluno.setDataHoraCadastro(new Date()); // Define a dataHoraCadastro antes de salvar
            aluno = alunoRepository.save(aluno);
            return new ResponseEntity<>(aluno, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Nome do aluno é inválido", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private boolean validarAluno(Aluno aluno) {
        return aluno != null && aluno.getNome() != null && !aluno.getNome().isEmpty();
    }
    @PutMapping("/editar")
    public ResponseEntity<Aluno> editar(@RequestBody Aluno aluno) {
        AlunoController alunoController = new AlunoController();
        if (alunoController.validarAluno(aluno)) {
            aluno = alunoRepository.save(aluno);
            return new ResponseEntity(aluno, HttpStatus.OK);
        } else {
            return new ResponseEntity("Nome do aluno é inválido", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/remover")
    public Aluno remover(@RequestBody Aluno aluno) {
        alunoRepository.delete(aluno);
        return aluno;
    }

    @GetMapping("/getTotal")
    public int getTotal() {
        return alunoRepository.findAll().size();
    }



}
