package DAO;

import Model.Account;
import Util.ConnectionUtil;

import java.sql.*;

public class AccountDAO {
    
    public Account insertAccount(Account account){
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "INSERT INTO Account (username, password) VALUES (?, ?)";
            
            PreparedStatement prepStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            prepStatement.setString(1, account.getUsername());
            prepStatement.setString(2, account.getPassword());
            prepStatement.executeUpdate();

            ResultSet pKeyResultSet = prepStatement.getGeneratedKeys();
            if(pKeyResultSet.next()){
                int generated_user_id = (int) pKeyResultSet.getLong(1);
                return new Account(generated_user_id, account.getUsername(), account.getPassword());
            }
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Account getAccountById(int account_id){
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM Account WHERE account_id = ?";
            
            PreparedStatement prepStatement = connection.prepareStatement(sql);
            prepStatement.setInt(1, account_id);

            ResultSet rs = prepStatement.executeQuery();
            while(rs.next()){
                Account account = new Account(rs.getInt("acount_id"),
                    rs.getString("username"),
                    rs.getString("password"));
                
                return account;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Account getAccountByUsername(String username){
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM Account WHERE username = ?";
            
            PreparedStatement prepStatement = connection.prepareStatement(sql);
            prepStatement.setString(1, username);

            ResultSet rs = prepStatement.executeQuery();
            while(rs.next()){
                Account account = new Account(rs.getInt("acount_id"),
                    rs.getString("username"),
                    rs.getString("password"));
                
                return account;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    
}
