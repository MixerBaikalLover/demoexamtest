package ru.mixer.demoexam.ui;

import ru.mixer.demoexam.entity.ClientEntity;
import ru.mixer.demoexam.manager.ClientEntityManager;
import ru.mixer.demoexam.util.BaseForm;
import ru.mixer.demoexam.util.DialogUtil;

import javax.swing.*;
import java.sql.SQLException;
import java.util.List;

public class ClientTextListForm extends BaseForm {
    private JPanel mainPanel;
    private JTextArea textArea;
    private JButton backButton;

    ClientTextListForm(){
        super(800,600);
        setContentPane(mainPanel);
        initButtons();
        initTextAreas();
        setVisible(true);
    }
    private void initButtons(){
        backButton.addActionListener(e -> {
            dispose();
            new MainForm();
        });
    }
    private void initTextAreas(){
        try {
            List<ClientEntity> list = ClientEntityManager.selectAll();
            String s = "";
            for (ClientEntity c : list){
                s += c;
                s += "\n";
            }
            textArea.setText(s);
        } catch (SQLException throwables) {
            DialogUtil.showError(this, "Ошибка подключения к базе данных " + throwables.getMessage());
        }
    }
}
