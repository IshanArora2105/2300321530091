public class Notification {

    String id;
    String type;
    String message;
    String timestamp;

    public Notification(String id,String type,String message,String timestamp) {

        this.id = id;
        this.type = type;
        this.message = message;
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return type + " | " +message + " | " +timestamp;
    }
}