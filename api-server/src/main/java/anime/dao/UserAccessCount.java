package anime.dao;

import lombok.Getter;

@Getter
public class UserAccessCount {

    public static int userAccessCount = 0;

    public static void increase() {
        userAccessCount++;
    }
}
