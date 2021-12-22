package ru.mixer.demoexam.ui;

import ru.mixer.demoexam.Application;
import ru.mixer.demoexam.entity.ClientEntity;
import ru.mixer.demoexam.manager.ClientEntityManager;
import ru.mixer.demoexam.util.BaseForm;
import ru.mixer.demoexam.util.DialogUtil;

import javax.swing.*;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;

public class ClientCreateForm extends BaseForm {
    private JPanel mainPanel;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField patronymicField;
    private JTextField birthDateField;
    private JTextField emailField;
    private JTextField phoneField;
    private JComboBox<String> genderComboBox;
    private JButton saveButton;
    private JButton backButton;

    public ClientCreateForm(){
        super(400, 300);
        setContentPane(mainPanel);
        initBoxes();
        initButtons();
        setVisible(true);
    }
    private void initBoxes(){
        genderComboBox.addItem("Мужской");
        genderComboBox.addItem("Женский");
    }
    private void initButtons(){
        backButton.addActionListener(e -> {
            dispose();
            new MainForm();
        });
        saveButton.addActionListener(e -> {
            String firstName = firstNameField.getText();
            if(firstName.isEmpty() || firstName.length() > 50){
                JOptionPane.showMessageDialog(this, "Имя не введено или слишком длинное", "Ошибка", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String lastName = lastNameField.getText();
            if(lastName.isEmpty() || lastName.length() > 50){
                JOptionPane.showMessageDialog(this, "Фамилия не введена или слишком длинная", "Ошибка", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String patronymic = patronymicField.getText();
            if(patronymic.isEmpty() || patronymic.length() > 50){
                JOptionPane.showMessageDialog(this, "Отчество не введено или слишком длинное", "Ошибка", JOptionPane.WARNING_MESSAGE);
                return;
            }
            Date birthDate = null;
            try {
               birthDate = Application.DATE_FORMAT.parse(birthDateField.getText());
            } catch (ParseException parseException) {
                JOptionPane.showMessageDialog(this, "Дата не введена или введена некорректно", "Ошибка", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String email = emailField.getText();
            if(email.isEmpty() || email.length() > 255){
                JOptionPane.showMessageDialog(this, "Почта не введена или слишком длинная", "Ошибка", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String phone = phoneField.getText();
            if(phone.isEmpty() || phone.length() > 20){
                JOptionPane.showMessageDialog(this, "Номер телефона не введен или слишком длинный", "Ошибка", JOptionPane.WARNING_MESSAGE);
                return;
            }
            char gender = genderComboBox.getSelectedItem().toString().toLowerCase().charAt(0);
            ClientEntity client = new ClientEntity(firstName, lastName, patronymic, birthDate, new Date(), email, phone, gender);
            try {
                ClientEntityManager.insert(client);
            } catch (SQLException throwables) {
                DialogUtil.showError(this, "Ошибка сохранения данных " + throwables.getMessage());
                return;
            }
            DialogUtil.showInfo(this, client.getFirstName() + " " + client.getLastName() + " Успешно добавлен");
            dispose();
            new MainForm();
        });
    }
}
