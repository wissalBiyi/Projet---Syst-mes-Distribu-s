package net.biyi.ebankservice.services;

import net.biyi.ebankservice.entities.BankAccount;
import net.biyi.ebankservice.feign.CustomerRestClient;
import net.biyi.ebankservice.model.Customer;
import net.biyi.ebankservice.repository.BankAccountRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class EbankService {
    private BankAccountRepository accountRepository;
    private CustomerRestClient customerRestClient;

    public EbankService(BankAccountRepository accountRepository ,CustomerRestClient customerRestClient) {
        this.accountRepository = accountRepository;

        this.customerRestClient=customerRestClient;
    }

    public List<BankAccount> getAllBankAccounts() {

        return accountRepository.findAll();
    }

    public BankAccount getBankAccountById(String id) {
        BankAccount bankAccount = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        bankAccount.setCustomer(
                customerRestClient.getCustomerById(bankAccount.getCustomerId())
        );
        return bankAccount;
    }
    public BankAccount save(BankAccount bankAccount) {
        try {
            Customer customer = customerRestClient.getCustomerById(bankAccount.getCustomerId());
            bankAccount.setId(UUID.randomUUID().toString());
            bankAccount.setCreatedAt(new Date());
            return accountRepository.save(bankAccount);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

}
