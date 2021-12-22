package ru.mixer.demoexam;

import ru.mixer.demoexam.ui.ClientCreateForm;
import ru.mixer.demoexam.ui.ClientTableForm;
import ru.mixer.demoexam.ui.MainForm;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

public class Application {
    public static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // new MainForm();
        new ClientTableForm();

    }


    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/demoexam", "root", "165268");
    }

}
