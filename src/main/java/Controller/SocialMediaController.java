package Controller;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.List;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */

    AccountService accountService;
    MessageService messageService;

    public SocialMediaController() {
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }

    public Javalin startAPI() {
        Javalin app = Javalin.create();
        /* API end points
         * new user registration
         * process login
         * new messages
         * retrieve all messages
         * retrieve a message by id
         * delete a message by id
         * update a message by id
         * retrieve all messages by a user
         */
        app.get("example-endpoint", this::exampleHandler);
        app.post("/register", this :: registerHandler);
        app.post("/login", this :: loginHandler);
        app.post("/messages", this :: newMessageHandler);
        app.get("/messages", this :: getAllMessagesHandler);
        app.get("/messages/{message_id}", this :: getMessageByIdHandler);
        app.delete("/messages/{message_id}", this :: deleteMessageByIdHandler);
        app.patch("/messages/{message_id}", this :: updateMessageByIdHandler);
        app.get("/accounts/{account_id}/messages", this :: getAllMessagesByAccountIdHandler);

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }

    private void registerHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account newAccount = accountService.register(account);
        if(newAccount != null){
            ctx.json(mapper.writeValueAsString(newAccount));
        } else {
            ctx.status(400);
        }
    }

    private void loginHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        System.out.println(account.getUsername());
        Account loggedInAccount = accountService.login(account);
        if(loggedInAccount != null){
            ctx.json(mapper.writeValueAsString(loggedInAccount));
        } else {
            ctx.status(401);
        }
    }

    private void newMessageHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        
        Message message = mapper.readValue(ctx.body(), Message.class);
        Account checkAccount = accountService.getAccountById(message.getPosted_by());
        if(checkAccount == null){
            ctx.status(400);
            
            return;
        }
        Message createdMessage = messageService.newMessage(message);
        
        if(createdMessage != null){
            
            ctx.json(mapper.writeValueAsString(createdMessage));
        } else {
            ctx.status(400);
        }
    }

    private void getAllMessagesHandler(Context ctx) {
        List<Message> messages = messageService.getAllMessages();
        ctx.json(messages);

    }

    private void getMessageByIdHandler(Context ctx) throws JsonProcessingException{
        int messageId = Integer.parseInt(ctx.pathParam("message_id"));
        Message message = messageService.getMessage(messageId);
        if(message == null){
            ctx.status(200);
            return;
        }
        ctx.json(message);
    }

    private void deleteMessageByIdHandler(Context ctx) throws JsonProcessingException{
        int messageId = Integer.parseInt(ctx.pathParam("message_id"));
        // System.out.println(messageId);
        Message message = messageService.deleteMessage(messageId);
        System.out.println("in controller");
        System.out.println(message);
        if(message == null){
            ctx.status(200);
            return;
        }
        ctx.json(message);
    }

    private void updateMessageByIdHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        int messageId = Integer.parseInt(ctx.pathParam("message_id"));
        Message message = mapper.readValue(ctx.body(), Message.class);
        Message checkMessage = messageService.getMessage(messageId);
        if(checkMessage != null){
            message.setMessage_id(checkMessage.getMessage_id());
            message.setPosted_by(checkMessage.getPosted_by());
            message.setTime_posted_epoch(checkMessage.getTime_posted_epoch());
        }
        Message updatedMessage = messageService.updateMessage(message);
        if(updatedMessage != null){
            ctx.json(mapper.writeValueAsString(updatedMessage));
        } else {
            ctx.status(400);
        }
    }

    public void getAllMessagesByAccountIdHandler(Context ctx) throws JsonProcessingException{
        int accountId = Integer.parseInt(ctx.pathParam("account_id"));
        System.out.println(accountId);
        List<Message> messages = messageService.getAllMessagesByUser(accountId);
        if(messages == null){
            ctx.status(200);
            return;
        }
        ctx.json(messages);
    }


}