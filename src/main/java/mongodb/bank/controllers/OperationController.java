package mongodb.bank.controllers;

import java.time.LocalDate;
import java.util.List;
import mongodb.bank.dao.AccountRepository;
import mongodb.bank.dao.OperationRepository;
import mongodb.bank.models.BankAccount;
import mongodb.bank.models.Operation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/operations")
public class OperationController {

    private final OperationRepository operationRepository;
    private final AccountRepository accountRepository;

    OperationController(OperationRepository operationRepository, AccountRepository accountRepository) {
        this.operationRepository = operationRepository;
        this.accountRepository = accountRepository;
    }

    @GetMapping
    public String list(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String type,
            Model model) {

        List<Operation> operations = operationRepository.findAll();

        if (search != null && !search.isBlank()) {
            String term = search.toLowerCase();
            operations = operations.stream()
                    .filter(o ->
                            (o.getAccountId() != null && o.getAccountId().toLowerCase().contains(term)) ||
                            (o.getAmount() != null && o.getAmount().toLowerCase().contains(term)) ||
                            (o.getDate() != null && o.getDate().toLowerCase().contains(term)))
                    .toList();
        }

        if (type != null && !type.isBlank()) {
            operations = operations.stream()
                    .filter(o -> type.equalsIgnoreCase(o.getType()))
                    .toList();
        }

        model.addAttribute("operations", operations);
        model.addAttribute("search", search);
        model.addAttribute("type", type);
        return "operations/operations";
    }

    @GetMapping("/deposit")
    public String depositForm(@RequestParam(required = false) String accountId, Model model) {
        model.addAttribute("operation", new Operation("DEPOT", null, LocalDate.now().toString(), accountId));
        if (accountId != null) {
            BankAccount account = accountRepository.findByAccountNumber(accountId);
            model.addAttribute("currentAccount", account);
        }
        return "operations/deposit";
    }

    @PostMapping("/deposit")
    public String deposit(
            @ModelAttribute Operation operation,
            @RequestParam(required = false) String date,
            RedirectAttributes ra) {
        if (date != null && !date.isBlank()) {
            operation.setDate(date);
        }
        operationRepository.save(operation);
        ra.addFlashAttribute("successMessage", "Depot effectue avec succes.");
        return "redirect:/operations";
    }

    @GetMapping("/withdraw")
    public String withdrawForm(@RequestParam(required = false) String accountId, Model model) {
        model.addAttribute("operation", new Operation("RETRAIT", null, LocalDate.now().toString(), accountId));
        if (accountId != null) {
            BankAccount account = accountRepository.findByAccountNumber(accountId);
            model.addAttribute("currentAccount", account);
        }
        return "operations/withdrawal";
    }

    @PostMapping("/withdraw")
    public String withdraw(
            @ModelAttribute Operation operation,
            @RequestParam(required = false) String date,
            RedirectAttributes ra) {
        if (date != null && !date.isBlank()) {
            operation.setDate(date);
        }
        operationRepository.save(operation);
        ra.addFlashAttribute("successMessage", "Retrait effectue avec succes.");
        return "redirect:/operations";
    }

    @GetMapping("/transfer")
    public String transferForm(Model model) {
        model.addAttribute("operation", new Operation("VIREMENT", null, LocalDate.now().toString(), null));
        return "operations/transfert";
    }

    @PostMapping("/transfer")
    public String transfer(
            @ModelAttribute Operation operation,
            @RequestParam(required = false) String targetAccountId,
            @RequestParam(required = false) String date,
            RedirectAttributes ra) {
        if (date != null && !date.isBlank()) {
            operation.setDate(date);
        }
        operationRepository.save(operation);
        if (targetAccountId != null && !targetAccountId.isBlank()) {
            operationRepository.save(new Operation("VIREMENT", operation.getAmount(), operation.getDate(), targetAccountId));
        }
        ra.addFlashAttribute("successMessage", "Virement effectue avec succes.");
        return "redirect:/operations";
    }
}
