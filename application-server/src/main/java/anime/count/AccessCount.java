package anime.count;

import java.util.concurrent.atomic.AtomicInteger;

public class AccessCount {

    private static final AtomicInteger CACHED = new AtomicInteger(0);

    public static void increase() {
        CACHED.incrementAndGet();
    }

    public static int flush() {
        return CACHED.getAndSet(0);
    }
}
