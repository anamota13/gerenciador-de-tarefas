package todo.service;

import org.springframework.stereotype.Service;
import todo.model.Tarefa;
import todo.repository.TarefaRepository;

import java.util.List;

@Service
public class TarefaService {

    private final TarefaRepository tarefaRepository;

    public TarefaService(TarefaRepository tarefaRepository) {
        this.tarefaRepository = tarefaRepository;
    }

    public Tarefa criar(Tarefa tarefa) {
        return tarefaRepository.save(tarefa);
    }

    public List<Tarefa> listar() {
        return tarefaRepository.findAll();
    }

    public Tarefa buscarPorId(Long id) {
        return tarefaRepository.findById(id).orElse(null);
    }

    public Tarefa atualizar(Long id, Tarefa novaTarefa) {
        Tarefa tarefa = tarefaRepository.findById(id).orElse(null);

        if (tarefa == null) {
            return null;
        }

        tarefa.setNome(novaTarefa.getNome());
        tarefa.setDescricao(novaTarefa.getDescricao());
        tarefa.setStatus(novaTarefa.getStatus());
        tarefa.setObservacoes(novaTarefa.getObservacoes());

        return tarefaRepository.save(tarefa);
    }

    public boolean excluir(Long id) {
        if (!tarefaRepository.existsById(id)) {
            return false;
        }

        tarefaRepository.deleteById(id);
        return true;
    }
}