package Service;

import DAO.MessageDAO;
import Model.Message;

import java.util.List;

public class MessageService {
    public MessageDAO messageDAO;

    public MessageService(){
        messageDAO = new MessageDAO();
    }

    public MessageService(MessageDAO messageDAO){
        this.messageDAO = messageDAO;
    }

    public Message newMessage(Message message){
        String messageText = message.getMessage_text();
        
        if(messageText.equals("")){
            
            return null;
        }
        if(messageText.length() > 255){
            
            return null;
        }
        
        Message newMessage = messageDAO.insertMessage(message);
        
        return newMessage;
    }

    public List<Message> getAllMessages(){
        return messageDAO.getAllMessages();
    }

    public Message getMessage(int message_id){
        return messageDAO.getMessageById(message_id);
    }

    public Message deleteMessage(int message_id){
        
        Message checkMessage = messageDAO.getMessageById(message_id);
        
        if(checkMessage == null){
            System.out.println("in delete message service");
            return null;
        }
        
        return messageDAO.deleteMessageById(message_id);
    }

    public Message updateMessage(Message message){
        
        Message checkMessage = messageDAO.getMessageById(message.getMessage_id());
        
        if(checkMessage == null){
            
            return null;
        }
        
        String messageText = message.getMessage_text();
        if(messageText.equals("")){
            
            return null;
        }
        
        if(messageText.length() > 255){
            System.out.println("in message service");
            return null;
        }
        
        Message updatedMessage = messageDAO.updateMessage(message);

        return messageDAO.updateMessage(updatedMessage);
    }

    public List<Message> getAllMessagesByUser(int account_id){
        return messageDAO.getAllMessagesByAccountId(account_id);
    }

}
