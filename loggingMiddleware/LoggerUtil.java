
public class LoggerUtil {
     public static void log(
            String stack,
            String level,
            String packageName,
            String message
    ) 
    {
        System.out.println(
                "[" +level+ "] "
                +"[" +stack+ "] "
                +"[" +packageName+ "] "
                +message
        );
    }
}
