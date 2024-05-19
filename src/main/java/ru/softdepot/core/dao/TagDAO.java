package ru.softdepot.core.dao;

import org.springframework.stereotype.Component;
import ru.softdepot.core.models.Tag;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class TagDAO implements DAO<Tag>{
    private static Connection connection;

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            connection = DriverManager.getConnection(DataBase.getUrl(), DataBase.getUser(), DataBase.getPassword());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int add(Tag tag) throws Exception {
        if (!exists(tag.getName())) {
            try {
                PreparedStatement statement = connection.prepareStatement(
                        "INSERT INTO tag (tag_name) VALUES (?)" +
                                "RETURNING id"
                );
                statement.setString(1, tag.getName());
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    return resultSet.getInt("id");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else {
            String msg = String.format("Tag [name=%s] already exists", tag.getName());

            throw new Exception(msg);
        }
        return -1;
    }

    @Override
    public void update(Tag tag) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE tag SET tag_name=? WHERE id=?"
            );
            statement.setString(1, tag.getName());
            statement.setInt(2, tag.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM tag WHERE id=?"
            );
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public Tag getById(int id) {
        Tag tag = null;
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM tag WHERE id=?"
            );
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                tag = new Tag(id, resultSet.getString("tag_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tag;
    }

    public Tag getByName(String name) {
        Tag tag = null;
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM tag WHERE tag_name=?"
            );
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                tag = getById(resultSet.getInt("id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tag;
    }

    public List<Tag> getAll() {
        List<Tag> tags = new ArrayList<Tag>();

        try {
            PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM tag"
            );
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                tags.add(new Tag(resultSet.getInt("id"), resultSet.getString("tag_name")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tags;
    }

    public boolean exists(String tagName) {
        boolean exists = false;
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM tag WHERE tag_name=?"
            );
            statement.setString(1, tagName);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                exists = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exists;
    }
}
