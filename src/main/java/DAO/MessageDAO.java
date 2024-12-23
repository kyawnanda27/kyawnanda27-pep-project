package DAO;

import Model.Message;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MessageDAO {
    public Message insertMessage(Message message){
        Connection connection = ConnectionUtil.getConnection();
        
        try {
            String sql = "INSERT INTO Message(posted_by, message_text, time_posted_epoch) VALUES (?, ?, ?)";
            PreparedStatement prepStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            prepStatement.setInt(1, message.getPosted_by());
            prepStatement.setString(2, message.getMessage_text());
            prepStatement.setLong(3, message.getTime_posted_epoch());
            prepStatement.executeUpdate();
            
            ResultSet pKeyResultSet = prepStatement.getGeneratedKeys();
            if(pKeyResultSet.next()){
                
                int generated_message_id = (int) pKeyResultSet.getLong(1);

                return new Message(generated_message_id, message.getPosted_by(), message.getMessage_text(), message.getTime_posted_epoch());

            }


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Message> getAllMessages(){
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Message";
            PreparedStatement prepStatement = connection.prepareStatement(sql);
            ResultSet rs = prepStatement.executeQuery();
            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"),
                rs.getInt("posted_by"),
                rs.getString("message_text"),
                rs.getLong("time_posted_epoch")
                );
                messages.add(message);
            }
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }
        return messages;
    }

    public Message getMessageById(int message_id){
        Connection connection = ConnectionUtil.getConnection();
        try {
            
            String sql = "SELECT * FROM Message WHERE message_id = ?";
            

            PreparedStatement prepStatement = connection.prepareStatement(sql);
            
            prepStatement.setInt(1, message_id);

            ResultSet rs = prepStatement.executeQuery();
            
            while(rs.next()){
                
                Message message = new Message(rs.getInt("message_id"),
                rs.getInt("posted_by"),
                rs.getString("message_text"),
                rs.getLong("time_posted_epoch")
                );
                
                return message;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println("in get message by id");
        return null;
    }

    public Message deleteMessageById(int message_id){
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sqlSelect = "SELECT * FROM Message WHERE message_id = ?";
            String sqlDelete = "DELETE FROM Message WHERE message_id = ?";

            PreparedStatement prepStatementSelect = connection.prepareStatement(sqlSelect);
            prepStatementSelect.setInt(1, message_id);

            PreparedStatement prepStatement = connection.prepareStatement(sqlDelete);
            prepStatement.setInt(1, message_id);

            ResultSet rs = prepStatementSelect.executeQuery();
            prepStatement.executeUpdate();
            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"),
                rs.getInt("posted_by"),
                rs.getString("message_text"),
                rs.getLong("time_posted_epoch")
                );
                return message;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Message updateMessage(Message message){
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "UPDATE Message SET message_text = ? WHERE message_id = ?";


            PreparedStatement prepStatement = connection.prepareStatement(sql);
            prepStatement.setString(1, message.getMessage_text());
            prepStatement.setInt(2, message.getMessage_id());

            prepStatement.executeUpdate();
            return message;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Message> getAllMessagesByAccountId(int account_id){
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Message WHERE posted_by = ?";

            PreparedStatement prepStatement = connection.prepareStatement(sql);
            prepStatement.setInt(1, account_id);

            ResultSet rs = prepStatement.executeQuery();
            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"),
                rs.getInt("posted_by"),
                rs.getString("message_text"),
                rs.getLong("time_posted_epoch")
                );
                messages.add(message);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return messages;
    }

}
