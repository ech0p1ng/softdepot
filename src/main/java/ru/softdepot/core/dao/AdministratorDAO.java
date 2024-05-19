package ru.softdepot.core.dao;

import org.springframework.stereotype.Component;
import ru.softdepot.core.models.Administrator;
import ru.softdepot.core.models.Program;
import ru.softdepot.core.models.Tag;

import java.sql.*;
import java.util.List;

@Component
public class AdministratorDAO implements DAO<Administrator> {
    private static Connection connection;

    TagDAO tagDAO = new TagDAO();
    ProgramDAO programDAO = new ProgramDAO();

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            connection = DriverManager.getConnection(DataBase.getUrl(), DataBase.getAdministratorRole(), DataBase.getAdministratorPassword());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int add(Administrator admin) throws Exception {
        if (!exists(admin.getEmail())) {
            try {
                PreparedStatement statement = connection.prepareStatement(
                        "INSERT INTO administrator (email,password,administrator_name) VALUES (?,?,?) RETURNING id"
                );
                statement.setString(1, admin.getEmail());
                statement.setString(2, admin.getPassword());
                statement.setString(3, admin.getName());
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    return resultSet.getInt("id");
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else {
            String msg = String.format("Administrator [name=%s or email=%s] already exists",
                    admin.getName(), admin.getEmail());
            throw new Exception(msg);
        }
        return -1;

    }

    @Override
    public void update(Administrator admin) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE administrator SET email=?, password=? WHERE id=?"
            );
            statement.setString(1, admin.getEmail());
            statement.setString(2, admin.getPassword());
            statement.setInt(3, admin.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM administrator WHERE id=?"
            );
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public Administrator getById(int id) throws Exception {
        Administrator admin = null;

        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM administrator WHERE id=?"
            );
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                admin = new Administrator(
                        id,
                        resultSet.getString("email"),
                        resultSet.getString("password"),
                        resultSet.getString("administrator_name")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (admin == null) {
            String msg  = String.format("Administrator with [id=%d] does not exist.", id);
            throw new Exception(msg);
        }

        return admin;
    }

    public Administrator getByEmailAndPassword(String email, String password) throws Exception {
        Administrator admin = null;

        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM administator WHERE login=? AND password=?"
            );
            statement.setString(1, email);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                admin = getById(resultSet.getInt("id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (admin == null) {
            String msg  = String.format("Administrator with [email=%s, password=%s] does not exist.", email, password);
            throw new Exception(msg);
        }

        return admin;
    }

    public boolean exists(String email) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM administrator WHERE email=?"
            );
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public int addTag(Tag tag) throws Exception {
        return tagDAO.add(tag);
    }

    public void deleteTag(Tag tag) throws Exception {
        tagDAO.delete(tag.getId());
    }

    public void updateTag(Tag tag) throws Exception {
        tagDAO.update(tag);
    }

    public boolean existsTag(Tag tag) throws Exception {
        return tagDAO.exists(tag.getName());
    }

    public Tag getTagById(int id) throws Exception {
        return tagDAO.getById(id);
    }

    public Tag getTagByName(String name) throws Exception {
        return tagDAO.getByName(name);
    }


    public void updateProgram(Program program) throws Exception {
        programDAO.update(program);
    }

    public void deleteProgram(Program program) throws Exception {
        programDAO.delete(program.getId());
    }

    public List<Program> getProgramByName(String programName) throws Exception {
        return programDAO.getByName(programName);
    }

    public Program getProgramById(int programId) throws Exception {
        return programDAO.getById(programId);
    }

    public Program getProgramByNameAndDeveloperId(String name, int developerId) {
        return programDAO.getByNameAndDeveloperId(name, developerId);
    }



}
