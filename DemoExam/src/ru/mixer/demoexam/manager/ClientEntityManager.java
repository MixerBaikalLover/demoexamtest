package ru.mixer.demoexam.manager;

import ru.mixer.demoexam.entity.ClientEntity;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static ru.mixer.demoexam.Application.getConnection;

public class ClientEntityManager {

    public static void insert(ClientEntity client) throws SQLException {
      try(Connection c = getConnection()){
        String sql = "insert into Client(FirstName, LastName, Patronymic, Birthday, RegistrationDate, Email, Phone, GenderCode) values(?,?,?,?,?,?,?,?)";
          PreparedStatement ps = c.prepareStatement(sql , PreparedStatement.RETURN_GENERATED_KEYS);
          ps.setString(1, client.getFirstName());
          ps.setString(2, client.getLastName());
          ps.setString(3, client.getPatronymic());
          ps.setTimestamp(4, new Timestamp(client.getBirthday().getTime()));
          ps.setTimestamp(5, new Timestamp(client.getRegistrationDate().getTime()));
          ps.setString(6, client.getEmail());
          ps.setString(7, client.getPhone());
          ps.setString(8, String.valueOf(client.getGender()));

          ps.executeUpdate();

          ResultSet keys = ps.getGeneratedKeys();
          if(keys.next()){
              client.setId(keys.getInt(1));
              return;
          }
          throw new SQLException("Entity not added");
      }
    }
    public static ClientEntity selectById(int id) throws SQLException {
        try(Connection c = getConnection()){
            String sql = "select * from Client where id = ?";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                return new ClientEntity(rs.getInt("ID"),
                        rs.getString("FirstName"),
                        rs.getString("LastName"),
                        rs.getString("Patronymic"),
                        rs.getTimestamp("Birthday"),
                        rs.getTimestamp("RegistrationDate"),
                        rs.getString("Email"),
                        rs.getString("Phone"),
                        rs.getString("GenderCode").charAt(0));
            }
            return null;
        }
    }
    public static List<ClientEntity> selectAll() throws SQLException {
        try(Connection c = getConnection()){
            String sql = "select * from Client";
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery(sql);
            List<ClientEntity> entities = new ArrayList<>();
            while(rs.next()){
                entities.add(new ClientEntity(rs.getInt("ID"),
                        rs.getString("FirstName"),
                        rs.getString("LastName"),
                        rs.getString("Patronymic"),
                        rs.getTimestamp("Birthday"),
                        rs.getTimestamp("RegistrationDate"),
                        rs.getString("Email"),
                        rs.getString("Phone"),
                        rs.getString("GenderCode").charAt(0)));
            }
            return entities;
        }
    }
    public static void update(ClientEntity client) throws SQLException{
        try(Connection c = getConnection()){
            String sql = "update Client set FirstName = ?, LastName = ?, Patronymic = ?, Birthday = ?, RegistrationDate = ?, Email = ?, Phone = ?, GenderCode = ? where ID = ?";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, client.getFirstName());
            ps.setString(2, client.getLastName());
            ps.setString(3, client.getPatronymic());
            ps.setTimestamp(4, new Timestamp(client.getBirthday().getTime()));
            ps.setTimestamp(5, new Timestamp(client.getRegistrationDate().getTime()));
            ps.setString(6, client.getEmail());
            ps.setString(7, client.getPhone());
            ps.setString(8, String.valueOf(client.getGender()));
            ps.setInt(9, client.getId());

            ps.executeUpdate();
        }
    }
    public static void delete(int id) throws SQLException{
        try(Connection c = getConnection()){
            String sql = "delete from CLient where ID = ?";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
    public static void delete(ClientEntity client) throws SQLException{
      delete(client.getId());
    }
}
