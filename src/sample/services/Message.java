package sample.services;

public class Message {
    private String UserName;
    private String MessageText;

    public Message(String UserName, String MessageText){
        this.UserName = UserName;
        this.MessageText = MessageText;
    }

    public Message(){
    }

    public String getUserName() { return UserName; }
    public void setUserName(String userName){ UserName = userName;}

    public String getMessageText(){return MessageText; }
    public void setMessageText(String Messagetext){ MessageText = Messagetext;}
}
