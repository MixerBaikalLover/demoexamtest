package ru.mixer.demoexam.ui;

import org.omg.CORBA.MARSHAL;
import ru.mixer.demoexam.Application;
import ru.mixer.demoexam.entity.ClientEntity;
import ru.mixer.demoexam.manager.ClientEntityManager;
import ru.mixer.demoexam.ui.MainForm;
import ru.mixer.demoexam.util.BaseForm;
import ru.mixer.demoexam.util.DialogUtil;

import javax.swing.*;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;

public class ClientUpdateForm extends BaseForm {
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
    private JTextField idField;
    private JTextField registrationDateField;
    private JButton deleteButton;
    private ClientEntity client;

    public ClientUpdateForm(ClientEntity client){
        super(400, 400);
        this.client = client;
        setContentPane(mainPanel);
        initBoxes();
        initButtons();
        initFields();
        setVisible(true);
    }
    private void initBoxes(){
        genderComboBox.addItem("Мужской");
        genderComboBox.addItem("Женский");
    }
    private void initFields(){
        idField.setEditable(false);
        idField.setText(String.valueOf(client.getId()));
        firstNameField.setText(client.getFirstName());
        lastNameField.setText(client.getLastName());
        patronymicField.setText(client.getPatronymic());
        birthDateField.setText(Application.DATE_FORMAT.format(client.getBirthday()));
        registrationDateField.setText(Application.DATE_FORMAT.format(client.getRegistrationDate()));
        emailField.setText(client.getEmail());
        phoneField.setText(client.getPhone());
        genderComboBox.setSelectedIndex(client.getGender() == 'м' ? 0 : 1);
    }
    private void initButtons(){
        deleteButton.addActionListener(e -> {
            if(JOptionPane.showConfirmDialog(this, "Вы действительно хотите удалить данного клиента?", "Подтверждение", JOptionPane.YES_NO_OPTION)
             == JOptionPane.YES_OPTION){
                try {
                    ClientEntityManager.delete(client);
                    DialogUtil.showInfo(this, "Клиент успешно удалён");
                    dispose();
                    new MainForm();
                } catch (SQLException throwables) {
                    DialogUtil.showError(this, "Ошибка удаления данных: " +throwables.getMessage());
                }
            }

        });
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
                JOptionPane.showMessageDialog(this, "Дата рождения не введена или введена некорректно", "Ошибка", JOptionPane.WARNING_MESSAGE);
                return;
            }
            Date regDate = null;
            try {
                regDate = Application.DATE_FORMAT.parse(birthDateField.getText());
            } catch (ParseException parseException) {
                JOptionPane.showMessageDialog(this, "Дата регистрации не введена или введена некорректно", "Ошибка", JOptionPane.WARNING_MESSAGE);
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

            client.setFirstName(firstName);
            client.setLastName(lastName);
            client.setPatronymic(patronymic);
            client.setBirthday(birthDate);
            client.setRegistrationDate(regDate);
            client.setEmail(email);
            client.setPhone(phone);
            client.setGender(gender);

            try {
                ClientEntityManager.update(client);
            } catch (SQLException throwables) {
                DialogUtil.showError(this, "Ошибка сохранения данных " + throwables.getMessage());
                return;
            }
            DialogUtil.showInfo(this, "Клиент успешно изменен");
            dispose();
            new MainForm();
        });
    }
}
