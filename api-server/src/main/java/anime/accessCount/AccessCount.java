package anime.accessCount;

import java.util.concurrent.atomic.AtomicInteger;

public class AccessCount {

    private static final AtomicInteger CACHED = new AtomicInteger(0);

    public static void increase() {
        CACHED.incrementAndGet();
    }

    public static int flush() {
        var valueNow = CACHED.get();
        CACHED.set(0);
        return valueNow;
    }
}
