package anime.accessCount;

public class AccessCount {

    public static int accessCount = 0;

    public static void increase() {
        accessCount++;
    }

    public static int getAccessCount() {
        return accessCount;
    }
}
