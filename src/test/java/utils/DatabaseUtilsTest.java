package utils;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import domain.TimeStamp;
import domain.ToDoItem;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.sql.Time;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DatabaseUtilsTest {

    ConnectionSource testConnection;
    Dao<ToDoItem, String> bookDao;

    @BeforeEach
    void setupDB() throws SQLException {
        testConnection = new JdbcConnectionSource("jdbc:sqlite:test.db");
        TableUtils.dropTable(testConnection, ToDoItem.class, true);
        TableUtils.createTableIfNotExists(testConnection, ToDoItem.class);
        bookDao = DaoManager.createDao(testConnection, ToDoItem.class);
    }

    @Test
    void testCreate() throws SQLException {
        bookDao.create(new ToDoItem("Reminder for crepes", "Klemm", new TimeStamp(12,07,23)));
        assertEquals(1, bookDao.countOf());
    }

    @AfterEach
    void deleteDBFile() throws IOException {
        testConnection.close();
        File dbFile = new File(Paths.get(".").normalize().toAbsolutePath() + "\\test.db");
        dbFile.delete();
    }


}
