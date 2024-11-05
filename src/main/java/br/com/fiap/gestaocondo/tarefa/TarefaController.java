package br.com.fiap.gestaocondo.tarefa;

import br.com.fiap.gestaocondo.user.User;
import jakarta.validation.Valid;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class TarefaController {

    private final TarefaService tarefaService;
    private final RabbitTemplate rabbitTemplate;

    // Construtor para injeção do serviço de tarefa e RabbitTemplate
    public TarefaController(TarefaService tarefaService, RabbitTemplate rabbitTemplate) {
        this.tarefaService = tarefaService;
        this.rabbitTemplate = rabbitTemplate;
    }

    @GetMapping
    public String index(Model model) {
        // Pega as tarefas pendentes
        var tasks = tarefaService.findPending();
        model.addAttribute("tasks", tasks);

        return "index";
    }

    @GetMapping("/form")
    public String form(Model model) {
        model.addAttribute("tarefa", new Tarefa());
        return "form"; // Nome do template de formulário deve ser consistente
    }

    @PostMapping("/task")
    public String create(@Valid Tarefa task, BindingResult result, RedirectAttributes redirect) {
        System.out.println("Chegou no método create");

        // Verifica se há erros de validação
        if (result.hasErrors()) {
            System.out.println("Erro de validação encontrado nos campos:");
            result.getFieldErrors().forEach(error -> System.out.println(error.getField() + ": " + error.getDefaultMessage()));
            return "form"; // Mantém o nome do template consistente
        }

        // Salva a tarefa usando o serviço
        tarefaService.create(task);

        // Envia uma mensagem para a fila RabbitMQ para notificar sobre a nova tarefa
        String message = "Uma nova tarefa foi criada: " + task.getTitulo();
        rabbitTemplate.convertAndSend("email-queue", message);

        // Adiciona uma mensagem de sucesso para exibição após redirecionamento
        redirect.addFlashAttribute("message", "Tarefa cadastrada com sucesso!");

        // Redireciona para a página principal ou para a listagem de tarefas
        return "redirect:/";
    }

    @PutMapping("/task/inc/{id}")
    public String incTask(@PathVariable Long id, RedirectAttributes redirect) {
        tarefaService.incTarefa(id, 10); // Incrementa o status em 10 unidades
        redirect.addFlashAttribute("message", "Status da tarefa incrementado com sucesso!");
        return "redirect:/";
    }

    @DeleteMapping("/task/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirect) {
        tarefaService.delete(id);
        redirect.addFlashAttribute("message", "Tarefa apagada com sucesso");
        return "redirect:/";
    }

    @PutMapping("/task/catch/{id}")
    public String catchTask(@PathVariable Long id, @AuthenticationPrincipal OAuth2User principal) {
        tarefaService.catchTarefa(id, (User) principal);
        return "redirect:/";
    }

    @PutMapping("/task/release/{id}")
    public String releaseTask(@PathVariable Long id, @AuthenticationPrincipal OAuth2User principal) {
        tarefaService.releaseTarefa(id, (User) principal);
        return "redirect:/";
    }

    @PutMapping("/task/dec/{id}")
    public String decTask(@PathVariable Long id, RedirectAttributes redirect) {
        tarefaService.decTarefa(id); // Chamando o serviço para decrementar o status
        redirect.addFlashAttribute("message", "Status da tarefa decrementado com sucesso!");
        return "redirect:/";
    }
}
