import java.util.*;

public class Scheduler {

    public static int getMaxImpact(
            List<Vehicle> vehicles,int hours) {

        int n = vehicles.size();

        int[][] dp =new int[n + 1][hours + 1];

        for(int i = 1; i <= n; i++) {

            Vehicle v=vehicles.get(i - 1);

            for(int h = 0;h <= hours;h++) {

                if(v.duration <= h) {

                    dp[i][h] =
                            Math.max(
                                    dp[i - 1][h],dp[i - 1][h - v.duration]+ v.impact);
                }
                else {
                    dp[i][h] =dp[i - 1][h];
                }
            }
        }

        return dp[n][hours];
    }
}
