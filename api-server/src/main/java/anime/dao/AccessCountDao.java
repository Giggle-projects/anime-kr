package anime.dao;

import anime.exception.DataFileException;
import com.google.common.io.FileWriteMode;
import com.google.common.io.Files;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.charset.StandardCharsets;

@Component
public class AccessCountDao {

    private final File file;

    public AccessCountDao(@Value("${access.count.file.path}") String dataFilePath) {
        file = new File(dataFilePath);
    }

    public void writeDatafile(String date, int dailyTotalAccessCount) {
        try {
            var charSink = Files.asCharSink(file, StandardCharsets.UTF_8, FileWriteMode.APPEND);
            var content = "|" + date + "|" + dailyTotalAccessCount + "|";
            charSink.write(content + System.lineSeparator());
        } catch (Exception e) {
            throw new DataFileException("Failed to write daily request count", e);
        }
    }
}
