import java.net.http.HttpClient;

public class TestHttp {
    public static void main(String args[]){
        HttpClient client =HttpClient.newHttpClient();

        System.out.println("HTTP Client Available");
    }
}
