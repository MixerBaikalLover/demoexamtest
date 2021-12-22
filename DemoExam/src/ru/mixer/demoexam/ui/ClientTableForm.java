package ru.mixer.demoexam.ui;

import ru.mixer.demoexam.entity.ClientEntity;
import ru.mixer.demoexam.manager.ClientEntityManager;
import ru.mixer.demoexam.util.BaseForm;
import ru.mixer.demoexam.util.CustomTableModel;

import javax.swing.*;
import java.sql.SQLException;

public class ClientTableForm extends BaseForm {
    private JPanel mainPanel;
    private JTable table1;
    private JButton button1;
    private CustomTableModel<ClientEntity> model;

    public ClientTableForm(){
        super(800,600);
        setContentPane(mainPanel);
        initTables();
        initButtons();
        setVisible(true);
    }

    private void initTables(){
        try {
            model = new CustomTableModel<>(
                    ClientEntity.class,
                    ClientEntityManager.selectAll()
            );
            table1.setModel(model);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void initButtons(){

    }
}
