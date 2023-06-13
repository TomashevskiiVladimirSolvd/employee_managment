package com.laba.solvd.db.dao;

import com.laba.solvd.db.dao.Interfaces.IDAOCredential;
import com.laba.solvd.db.model.Credential;
import com.laba.solvd.db.dao.Interfaces.IDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CredentialDAO implements IDAOCredential {
    private Connection connection;

    public CredentialDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Credential credential) {
        try {
            String sql = "INSERT INTO Credentials (id, login, password) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, credential.getId());
            statement.setString(2, credential.getLogin());
            statement.setString(3, credential.getPassword());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Credential read(Long id) {
        Credential credential = null;
        try {
            String sql = "SELECT * FROM Credentials WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                credential = new Credential();
                credential.setId(resultSet.getLong("id"));
                credential.setLogin(resultSet.getString("login"));
                credential.setPassword(resultSet.getString("password"));
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return credential;
    }

    @Override
    public void update(Credential credential) {
        try {
            String sql = "UPDATE Credentials SET login = ?, password = ? WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, credential.getLogin());
            statement.setString(2, credential.getPassword());
            statement.setLong(3, credential.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
