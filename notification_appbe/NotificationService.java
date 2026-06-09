import java.util.*;

public class NotificationService {

    public static int getPriority(String type) {

        if(type.equalsIgnoreCase("Placement"))
            return 3;

        if(type.equalsIgnoreCase("Result"))
            return 2;

        return 1;
    }

    public static List<Notification> getTopNotifications(
            List<Notification> notifications) {

        notifications.sort((a, b) -> {
            int p1 = getPriority(a.type);
            int p2 = getPriority(b.type);
            if (p1 != p2) {
                return p2 - p1;
            }
            return b.timestamp.compareTo(a.timestamp);
        });
        return new ArrayList<>(
                notifications.subList(0, Math.min(10, notifications.size()))
        );
    }
}