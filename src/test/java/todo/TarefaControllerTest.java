package todo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import todo.controller.TarefaController;
import todo.model.Status;
import todo.model.Tarefa;
import todo.service.TarefaService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TarefaController.class)
class TarefaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TarefaService tarefaService;

    @Test
    void deveListarTarefas() throws Exception {
        Tarefa tarefa = criarTarefa();

        when(tarefaService.listar())
                .thenReturn(List.of(tarefa));

        mockMvc.perform(get("/tarefas"))
                .andExpect(status().isOk());
    }

    @Test
    void deveCriarTarefa() throws Exception {
        Tarefa tarefa = criarTarefa();

        when(tarefaService.criar(any(Tarefa.class)))
                .thenReturn(tarefa);

        mockMvc.perform(post("/tarefas")
                .contentType("application/json")
                .content("""
                    {
                        "nome": "Estudar Spring Boot",
                        "descricao": "Finalizar o CRUD",
                        "status": "PENDENTE",
                        "observacoes": "Projeto da faculdade"
                    }
                    """))
                .andExpect(status().isOk());

        verify(tarefaService, times(1))
                .criar(any(Tarefa.class));
    }

    @Test
    void deveBuscarTarefaPorId() throws Exception {
        Tarefa tarefa = criarTarefa();

        when(tarefaService.buscarPorId(1L))
                .thenReturn(tarefa);

        mockMvc.perform(get("/tarefas/1"))
                .andExpect(status().isOk());

        verify(tarefaService, times(1))
                .buscarPorId(1L);
    }

    @Test
    void deveRetornar404QuandoTarefaNaoExiste() throws Exception {
        when(tarefaService.buscarPorId(99L))
                .thenReturn(null);

        mockMvc.perform(get("/tarefas/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deveAtualizarTarefa() throws Exception {
        Tarefa tarefa = criarTarefa();
        tarefa.setNome("Tarefa atualizada");

        when(tarefaService.atualizar(eq(1L), any(Tarefa.class)))
                .thenReturn(tarefa);

        mockMvc.perform(put("/tarefas/1")
                .contentType("application/json")
                .content("""
                    {
                        "nome": "Tarefa atualizada",
                        "descricao": "Descrição atualizada",
                        "status": "EM_ANDAMENTO",
                        "observacoes": "Atualizado"
                    }
                    """))
                .andExpect(status().isOk());

        verify(tarefaService, times(1))
                .atualizar(eq(1L), any(Tarefa.class));
    }

    @Test
    void deveExcluirTarefa() throws Exception {
        when(tarefaService.excluir(1L))
                .thenReturn(true);

        mockMvc.perform(delete("/tarefas/1"))
                .andExpect(status().isNoContent());

        verify(tarefaService, times(1))
                .excluir(1L);
    }

    @Test
    void deveRetornar404AoExcluirTarefaInexistente() throws Exception {
        when(tarefaService.excluir(99L))
                .thenReturn(false);

        mockMvc.perform(delete("/tarefas/99"))
                .andExpect(status().isNotFound());
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