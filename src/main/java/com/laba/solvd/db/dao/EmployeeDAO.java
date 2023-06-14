package com.laba.solvd.db.dao;

import com.laba.solvd.db.dao.Interfaces.IDao;
import com.laba.solvd.db.model.Employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO implements IDao<Employee, Long> {
    private Connection connection;
    String sqlAll = "Select * FROM contacts;\n" +
            "Select * FROM  departments ;\n" +
            "Select * FROM employees_departments;\n" +
            "Select * FROM employees;\n" +
            "Select * FROM employees_tasks ;\n" +
            "Select * FROM employees_skills ;\n" +
            "Select * FROM projects;\n" +
            "Select * FROM credentials;\n" +
            "Select * FROM tasks;\n" +
            "Select * FROM skills;\n" +
            "Select * FROM training_programs;\n" +
            "Select * FROM employees_trainings;";

    public EmployeeDAO() {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        this.connection = connectionPool.getConnection();
    }

    @Override
    public void create(Employee entity) {
        try {
            String sql = "INSERT INTO Employees (id, name, position) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, entity.getId());
            statement.setString(2, entity.getName());
            statement.setString(3, entity.getPosition());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void update(Employee entity) {
        try {
            String sql = "UPDATE Employees SET name = ?, position = ? WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, entity.getName());
            statement.setString(2, entity.getPosition());
            statement.setLong(3, entity.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Long id) {
        try {
            String sql = "DELETE FROM Employees WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Employee> getAll() {
        List<Employee> employeeList = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(sqlAll);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Employee employee = new Employee();
                employee.setId(resultSet.getLong("id"));
                employee.setName(resultSet.getString("name"));
                employee.setPosition(resultSet.getString("position"));
                employeeList.add(employee);
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employeeList;
    }

    public void close() {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        connectionPool.releaseConnection(connection);
        connection = null;
    }
}



