package todo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import todo.model.Status;
import todo.model.Tarefa;
import todo.repository.TarefaRepository;
import todo.service.TarefaService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TarefaServiceTest {

    @Mock
    private TarefaRepository tarefaRepository;

    @InjectMocks
    private TarefaService tarefaService;

    @Test
    void deveCriarUmaTarefa() {
        Tarefa tarefa = criarTarefa();

        when(tarefaRepository.save(tarefa)).thenReturn(tarefa);

        Tarefa resultado = tarefaService.criar(tarefa);

        assertNotNull(resultado);
        assertEquals("Estudar Spring Boot", resultado.getNome());
        assertEquals(Status.PENDENTE, resultado.getStatus());

        verify(tarefaRepository, times(1)).save(tarefa);
    }

    @Test
    void deveListarTarefas() {
        Tarefa tarefa = criarTarefa();

        when(tarefaRepository.findAll()).thenReturn(List.of(tarefa));

        List<Tarefa> resultado = tarefaService.listar();

        assertEquals(1, resultado.size());
        assertEquals("Estudar Spring Boot", resultado.get(0).getNome());

        verify(tarefaRepository, times(1)).findAll();
    }

    @Test
    void deveBuscarTarefaPorId() {
        Tarefa tarefa = criarTarefa();

        when(tarefaRepository.findById(1L))
                .thenReturn(Optional.of(tarefa));

        Tarefa resultado = tarefaService.buscarPorId(1L);

        assertNotNull(resultado);
        assertEquals("Estudar Spring Boot", resultado.getNome());

        verify(tarefaRepository, times(1)).findById(1L);
    }

    @Test
    void deveAtualizarUmaTarefa() {
        Tarefa tarefaExistente = criarTarefa();

        Tarefa novaTarefa = new Tarefa();
        novaTarefa.setNome("Estudar Java");
        novaTarefa.setDescricao("Estudar JPA e Spring");
        novaTarefa.setStatus(Status.EM_ANDAMENTO);
        novaTarefa.setObservacoes("Atualizado");

        when(tarefaRepository.findById(1L))
                .thenReturn(Optional.of(tarefaExistente));

        when(tarefaRepository.save(tarefaExistente))
                .thenReturn(tarefaExistente);

        Tarefa resultado = tarefaService.atualizar(1L, novaTarefa);

        assertNotNull(resultado);
        assertEquals("Estudar Java", resultado.getNome());
        assertEquals(Status.EM_ANDAMENTO, resultado.getStatus());
        assertEquals("Atualizado", resultado.getObservacoes());

        verify(tarefaRepository, times(1)).findById(1L);
        verify(tarefaRepository, times(1)).save(tarefaExistente);
    }

    @Test
    void deveExcluirUmaTarefa() {
        when(tarefaRepository.existsById(1L))
                .thenReturn(true);

        boolean resultado = tarefaService.excluir(1L);

        assertTrue(resultado);

        verify(tarefaRepository, times(1)).existsById(1L);
        verify(tarefaRepository, times(1)).deleteById(1L);
    }

    @Test
    void deveRetornarNullQuandoTarefaNaoExiste() {
        when(tarefaRepository.findById(99L))
                .thenReturn(Optional.empty());

        Tarefa resultado = tarefaService.buscarPorId(99L);

        assertNull(resultado);

        verify(tarefaRepository, times(1)).findById(99L);
    }

    @Test
    void naoDeveAtualizarTarefaInexistente() {
        Tarefa novaTarefa = criarTarefa();

        when(tarefaRepository.findById(99L))
                .thenReturn(Optional.empty());

        Tarefa resultado = tarefaService.atualizar(99L, novaTarefa);

        assertNull(resultado);

        verify(tarefaRepository, times(1)).findById(99L);
        verify(tarefaRepository, never()).save(any(Tarefa.class));
    }

    @Test
    void naoDeveExcluirTarefaInexistente() {
        when(tarefaRepository.existsById(99L))
                .thenReturn(false);

        boolean resultado = tarefaService.excluir(99L);

        assertFalse(resultado);

        verify(tarefaRepository, times(1)).existsById(99L);
        verify(tarefaRepository, never()).deleteById(anyLong());
    }

    private Tarefa criarTarefa() {
        Tarefa tarefa = new Tarefa();

        tarefa.setNome("Estudar Spring Boot");
        tarefa.setDescricao("Finalizar o CRUD");
        tarefa.setStatus(Status.PENDENTE);
        tarefa.setObservacoes("Projeto da faculdade");

        return tarefa;
    }
}