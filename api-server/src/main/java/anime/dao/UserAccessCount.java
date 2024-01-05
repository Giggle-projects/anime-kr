package anime.dao;

public class UserAccessCount {

    public static int userAccessCount = 0;

    public static void increase() {
        userAccessCount++;
    }

    public static int getUserAccessCount() {
        return userAccessCount;
    }
}
