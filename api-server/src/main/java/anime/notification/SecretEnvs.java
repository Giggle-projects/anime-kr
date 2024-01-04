package anime.notification;

import anime.exception.DataFileException;
import com.google.common.base.Charsets;
import com.google.common.io.Files;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class SecretEnvs {

    private static final Map<String, String> SECRET_ENVS = new HashMap<>();

    static {
        final File file = new File("./data-files/secret.env");
        try {
            Files.readLines(file, Charsets.UTF_8).stream()
                    .map(it -> it.split("="))
                    .forEach(it -> SECRET_ENVS.put(it[0], it[1]));
        } catch (Exception e) {
            throw new DataFileException("Failed to load data file", e);
        }
    }

    public static String getEnv(String key) {
        return SECRET_ENVS.get(key);
    }
}
