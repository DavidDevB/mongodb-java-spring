package mongodb.bank.controllers;

import java.util.List;
import mongodb.bank.dao.ClientRepository;
import mongodb.bank.models.Client;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/clients")
public class ClientController {

  private final ClientRepository clientRepository;

  ClientController(ClientRepository clientRepository) {
    this.clientRepository = clientRepository;
  }

  // ── Liste ─────────────────────────────────────────────────────────────────

  @GetMapping
  public String list(@RequestParam(required = false) String search, Model model) {
    List<Client> clients;
    if (search != null && !search.isBlank()) {
      clients = clientRepository
          .findByLastnameContainingIgnoreCaseOrFirstnameContainingIgnoreCaseOrAdressContainingIgnoreCase(
              search, search, search);
    } else {
      clients = clientRepository.findAll();
    }
    model.addAttribute("clients", clients);
    model.addAttribute("search", search);
    return "clients/clients";
  }

  // ── Nouveau client ────────────────────────────────────────────────────────

  @GetMapping("/new")
  public String newForm(Model model) {
    model.addAttribute("client", new Client());
    return "clients/new-client";
  }

  @PostMapping("/new")
  public String create(Client client, RedirectAttributes ra) {
    clientRepository.save(client);
    ra.addFlashAttribute("successMessage", "Client créé avec succès.");
    return "redirect:/clients";
  }

  // ── Modifier ──────────────────────────────────────────────────────────────

  @GetMapping("/edit/{id}")
  public String editForm(@PathVariable String id, Model model) {
    Client client = clientRepository.findById(id).orElseThrow();
    model.addAttribute("client", client);
    model.addAttribute("clientId", id);
    return "clients/modify-client";
  }

  @PostMapping("/edit/{id}")
  public String update(@PathVariable String id, Client client, RedirectAttributes ra) {
    client.setId(id);
    clientRepository.save(client);
    ra.addFlashAttribute("successMessage", "Client modifié avec succès.");
    return "redirect:/clients";
  }

  // ── Supprimer ─────────────────────────────────────────────────────────────

  @GetMapping("/delete/{id}")
  public String deleteForm(@PathVariable String id, Model model) {
    Client client = clientRepository.findById(id).orElseThrow();
    model.addAttribute("client", client);
    model.addAttribute("clientId", id);
    return "clients/delete-client";
  }

  @PostMapping("/delete/{id}")
  public String delete(@PathVariable String id, RedirectAttributes ra) {
    clientRepository.deleteById(id);
    ra.addFlashAttribute("successMessage", "Client supprimé avec succès.");
    return "redirect:/clients";
  }
}
