package mongodb.bank.controllers;

import java.time.LocalDate;
import java.util.List;
import mongodb.bank.dao.AccountRepository;
import mongodb.bank.models.BankAccount;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/accounts")
public class AccountController {

  private final AccountRepository accountRepository;

  AccountController(AccountRepository accountRepository) {
    this.accountRepository = accountRepository;
  }

  // ── Liste ─────────────────────────────────────────────────────────────────

  @GetMapping
  public String list(@RequestParam(required = false) String search, Model model) {
    List<BankAccount> accounts;
    if (search != null && !search.isBlank()) {
      String term = search.toLowerCase();
      accounts = accountRepository
        .findAll()
        .stream()
        .filter(
          a ->
            a.getAccountNumber().toLowerCase().contains(term) ||
            (a.getClientId() != null && a.getClientId().toLowerCase().contains(term))
        )
        .toList();
    } else {
      accounts = accountRepository.findAll();
    }
    model.addAttribute("accounts", accounts);
    model.addAttribute("search", search);
    return "accounts/accounts";
  }

  // ── Nouveau compte ────────────────────────────────────────────────────────

  @GetMapping("/new")
  public String newForm(Model model) {
    model.addAttribute("account", new BankAccount());
    return "accounts/new-account";
  }

  @PostMapping("/new")
  public String create(BankAccount account, @RequestParam(required = false) String openingDate, RedirectAttributes ra) {
    if (openingDate != null && !openingDate.isBlank()) {
      account.setOpeningDate(LocalDate.parse(openingDate).atStartOfDay());
    }
    accountRepository.save(account);
    ra.addFlashAttribute("successMessage", "Compte ouvert avec succès.");
    return "redirect:/accounts";
  }

  // ── Modifier ──────────────────────────────────────────────────────────────

  @GetMapping("/edit/{id}")
  public String editForm(@PathVariable String id, Model model) {
    BankAccount account = accountRepository.findByAccountNumber(id);
    model.addAttribute("account", account);
    return "accounts/modify-account";
  }

  @PostMapping("/edit/{id}")
  public String update(@PathVariable String id, BankAccount account, RedirectAttributes ra) {
    account.setAccountNumber(id);
    BankAccount existing = accountRepository.findByAccountNumber(id);
    account.setOpeningDate(existing.getOpeningDate());
    accountRepository.save(account);
    ra.addFlashAttribute("successMessage", "Compte modifié avec succès.");
    return "redirect:/accounts";
  }

  // ── Supprimer ─────────────────────────────────────────────────────────────

  @GetMapping("/delete/{id}")
  public String deleteForm(@PathVariable String id, Model model) {
    BankAccount account = accountRepository.findByAccountNumber(id);
    model.addAttribute("account", account);
    return "accounts/delete-account";
  }

  @PostMapping("/delete/{id}")
  public String delete(@PathVariable String id, RedirectAttributes ra) {
    accountRepository.deleteByAccountNumber(id);
    ra.addFlashAttribute("successMessage", "Compte supprimé avec succès.");
    return "redirect:/accounts";
  }
}
