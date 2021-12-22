package ru.mixer.demoexam.ui;

import ru.mixer.demoexam.entity.ClientEntity;
import ru.mixer.demoexam.manager.ClientEntityManager;
import ru.mixer.demoexam.util.BaseForm;
import ru.mixer.demoexam.util.DialogUtil;

import javax.swing.*;
import java.sql.SQLException;

public class MainForm extends BaseForm {
    private JPanel mainPanel;
    private JButton listButton;
    private JButton editButton;
    private JButton addButton;
    public MainForm(){
        super(600,400);
        setContentPane(mainPanel);
        initButtons();
        setVisible(true);
    }
    private void initButtons(){
        listButton.addActionListener(e -> {
            dispose();
            new ClientTextListForm();
        });
        addButton.addActionListener(e -> {
            dispose();
           new ClientCreateForm();
        });
        editButton.addActionListener(e -> {

            String s = JOptionPane.showInputDialog(this, "Введите id", "Ввод" , JOptionPane.INFORMATION_MESSAGE);
            if(s==null) return;
            int id = -1;
            try{
                id = Integer.parseInt(s);
            }catch (Exception ex){
                DialogUtil.showError(this, "Некорректно введен или не введен id");
                return;
            }
            ClientEntity client = null;
            try {
                client = ClientEntityManager.selectById(id);
            } catch (SQLException throwables) {
                DialogUtil.showError(this, "Ошибка получения данных: " + throwables.getMessage());
                return;
            }
            if (client == null){
                DialogUtil.showError(this, "Клиента с таким Id не существует");
                return;
            }
            dispose();
            new ClientUpdateForm(client);
        });
    }
}
