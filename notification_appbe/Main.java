import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;
import java.util.regex.*;

public class Main {

    private static final String TOKEN =
            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJNYXBDbGFpbXMiOnsiYXVkIjoiaHR0cDovLzIwLjI0NC41Ni4xNDQvZXZhbHVhdGlvbi1zZXJ2aWNlIiwiZW1haWwiOiJhcm9yYWlzaGFuMjEwNUBnbWFpbC5jb20iLCJleHAiOjE3ODA5OTI4NzEsImlhdCI6MTc4MDk5MTk3MSwiaXNzIjoiQWZmb3JkIE1lZGljYWwgVGVjaG5vbG9naWVzIFByaXZhdGUgTGltaXRlZCIsImp0aSI6IjNiMzJlYWRkLWUzYTEtNDY0ZC04Y2IzLTdiMzJkYzgxMTY1ZCIsImxvY2FsZSI6ImVuLUlOIiwibmFtZSI6ImlzaGFuIGFyb3JhIiwic3ViIjoiNGYxMjU2ZTctMjdjYy00MDM0LWFlNGYtMTU5ZmQyMGI1ZjkxIn0sImVtYWlsIjoiYXJvcmFpc2hhbjIxMDVAZ21haWwuY29tIiwibmFtZSI6ImlzaGFuIGFyb3JhIiwicm9sbE5vIjoiMjMwMDMyMTUzMDA5MSIsImFjY2Vzc0NvZGUiOiJjWHVxaHQiLCJjbGllbnRJRCI6IjRmMTI1NmU3LTI3Y2MtNDAzNC1hZTRmLTE1OWZkMjBiNWY5MSIsImNsaWVudFNlY3JldCI6InpQVWNUUE5tR0ZYRlRWeGIifQ.WM0vcPqTZOtOrXRobgwoj2BwTPmoMqI5CbbutT7mT9U";

    public static void main(String[] args) {

        try {

            System.out.println("Fetching notifications...");

            HttpRequest request =
                    HttpRequest.newBuilder()
                            .uri(
                                    URI.create(
                                            "http://4.224.186.213/evaluation-service/notifications"
                                    )
                            )
                            .header(
                                    "Authorization",
                                    "Bearer " + TOKEN
                            )
                            .GET()
                            .build();

            HttpClient client =
                    HttpClient.newHttpClient();

            HttpResponse<String> response =
                    client.send(
                            request,
                            HttpResponse.BodyHandlers.ofString()
                    );

            int status = response.statusCode();
            String json = response.body();

            if (status != 200) {
                System.err.println("Request failed with status " + status);
                System.err.println(json);
                return;
            }

            System.out.println("Raw response: " + json);

            List<Notification> notifications = parseNotifications(json);

            System.out.println("Notifications Found: " + notifications.size());

            List<Notification> top10 = NotificationService.getTopNotifications(notifications);

            System.out.println();
            System.out.println("Top 10 Priority Notifications");
            System.out.println("--------------------------------");

            for (Notification n : top10) {
                System.out.println(n);
            }

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static List<Notification> parseNotifications(String json) {
        List<Notification> notifications = new ArrayList<>();
        Pattern objectPattern = Pattern.compile("\\{([^}]+)\\}");
        Matcher objectMatcher = objectPattern.matcher(json);

        while (objectMatcher.find()) {
            String objectText = objectMatcher.group(1);
            String id = extractFieldValue(objectText, "ID");
            String type = extractFieldValue(objectText, "Type");
            String message = extractFieldValue(objectText, "Message");
            String timestamp = extractFieldValue(objectText, "Timestamp");

            if (!id.isEmpty() && !type.isEmpty() && !message.isEmpty() && !timestamp.isEmpty()) {
                notifications.add(new Notification(id, type, message, timestamp));
            }
        }

        return notifications;
    }

    private static String extractFieldValue(String objectText, String fieldName) {
        Pattern fieldPattern = Pattern.compile("\"" + Pattern.quote(fieldName) + "\"\\s*:\\s*\"([^\"]*)\"", Pattern.CASE_INSENSITIVE);
        Matcher matcher = fieldPattern.matcher(objectText);
        return matcher.find() ? matcher.group(1) : "";
    }
}