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

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DatabaseUtilsTest {

    ConnectionSource testConnection;
    Dao<ToDoItem, String> toDoDao;

    @BeforeEach
    void setupDB() throws SQLException {
        testConnection = new JdbcConnectionSource("jdbc:sqlite:test.db");
        TableUtils.dropTable(testConnection, ToDoItem.class, true);
        TableUtils.createTableIfNotExists(testConnection, ToDoItem.class);
        toDoDao = DaoManager.createDao(testConnection, ToDoItem.class);
    }

    @Test
    void testCreate() {
        try {
            DatabaseUtils databaseUtils = new DatabaseUtils();
            ToDoItem todoItem1 = new ToDoItem("Reminder for grilled cheese", "Klemm", new TimeStamp(2020,4,4));
            String databaseRespone = databaseUtils.addItemToDatabase(todoItem1);
            assertEquals("Success", databaseRespone);
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Test
    void clearDatabase(){
        try{
            DatabaseUtils databaseUtils = new DatabaseUtils();
            databaseUtils.clearDatabase();
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    @AfterEach
    void deleteDBFile() throws IOException {
        testConnection.close();
        File dbFile = new File(Paths.get(".").normalize().toAbsolutePath() + "\\test.db");
        dbFile.delete();
    }


}
