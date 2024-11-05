package br.com.fiap.gestaocondo.tarefa;
import br.com.fiap.gestaocondo.user.User;
import br.com.fiap.gestaocondo.user.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TarefaService {

    private final TarefaRepository tarefaRepository;
    private final UserService userService;

    public TarefaService(TarefaRepository tarefaRepository, UserService userService) {
        this.tarefaRepository = tarefaRepository;
        this.userService = userService;
    }

    public List<Tarefa> findAll(){
        return tarefaRepository.findAll();
    }

    public void create(Tarefa tarefa) {
        tarefaRepository.save(tarefa);
    }

    public void delete(Long id) {
        tarefaRepository.deleteById(id);
    }

    public void catchTarefa(Long id, User user) {
        var tarefa = getTarefa(id);
        tarefa.setUser(user);
        tarefaRepository.save(tarefa);
    }

    public void releaseTarefa(Long id, User user) {
        var tarefa = getTarefa(id);
        tarefa.setUser(null);
        tarefaRepository.save(tarefa);
    }

    public void decTarefa(Long id) {
        Tarefa tarefa = tarefaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tarefa não encontrada"));

        // Decrementa o status em 10 unidades, mas garantindo que ele não seja menor que zero
        int novoStatus = tarefa.getStatus() - 10;
        tarefa.setStatus(Math.max(novoStatus, 0));

        tarefaRepository.save(tarefa);
    }

    public void incTarefa(Long id, int incrementValue) {
        Tarefa tarefa = tarefaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tarefa não encontrada"));

        // Incrementa o status em até 100
        tarefa.setStatus(Math.min(tarefa.getStatus() + incrementValue, 100));

        tarefaRepository.save(tarefa);
    }


    private Tarefa getTarefa(Long id) {
        var tarefa = tarefaRepository.findById(id).orElseThrow( () -> new RuntimeException("Tarefa não encontrada"));
        return tarefa;
    }

    public List<Tarefa> findPending() {
        return tarefaRepository.findByStatusLessThan(100);
    }
}
