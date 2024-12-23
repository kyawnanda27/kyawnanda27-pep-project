package Service;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {
    private AccountDAO accountDAO;

    public AccountService(){
        accountDAO = new AccountDAO();
    }

    public AccountService(AccountDAO accountDAO){
        this.accountDAO = accountDAO;
    }

    public Account register(Account account){
        Account checkAccount = accountDAO.getAccountByUsername(account.getUsername());
        if(checkAccount != null){
            return null;
        }
        String username = account.getUsername();
        String password = account.getPassword();
        if(username.equals("") || password.length() < 4){
            return null;
        }
        Account newAccount = accountDAO.insertAccount(account);
        return newAccount;
    }

    public Account login(Account account){
        Account checkAccount = accountDAO.getAccountByUsername(account.getUsername());
        if(checkAccount == null){
            
            return null;
        }
        if(!(checkAccount.getPassword().equals(account.getPassword()))){
            
            return null;
        }
        
        return checkAccount;
    }

    public Account getAccountById(int account_id){
        return accountDAO.getAccountById(account_id);
    }
    
  
}
