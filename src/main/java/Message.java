import java.io.Serializable;
import java.util.ArrayList;

public class Message implements Serializable {
    private String sender;
    private String type;
    private String text;
    private ArrayList<String> list;

    public Message(String type, String messageText) {
        this.type = type;
        this.text = messageText;
    }

    public Message(String sender, String type, String text) {
        this.sender = sender;
        this.type = type;
        this.text = text;
    }

    public Message(String sender, String type, String text, ArrayList<String> list) {
        this.sender = sender;
        this.type = type;
        this.text = text;
        this.list = list;
    }

    public ArrayList<String> getList() {
        return list;
    }

    public void setList(ArrayList<String> list) {
        this.list = list;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "Message{" +
                "sender='" + sender + '\'' +
                ", type='" + type + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
