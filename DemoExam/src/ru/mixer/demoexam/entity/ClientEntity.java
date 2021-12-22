package ru.mixer.demoexam.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.Objects;
@Data
@AllArgsConstructor
public class ClientEntity {
    private int id;
    private String firstName;
    private String lastName;
    private String patronymic;
    private Date birthday;
    private Date registrationDate;
    private String email;
    private String phone;
    private char gender;

    public ClientEntity(String firstName, String lastName, String patronymic, Date birthday, Date registrationDate, String email, String phone, char gender) {
      this(-1, firstName, lastName, patronymic, birthday, registrationDate, email, phone, gender);
    }
}
