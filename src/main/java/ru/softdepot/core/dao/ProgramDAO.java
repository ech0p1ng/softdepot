package ru.softdepot.core.dao;

import org.springframework.stereotype.Component;
import ru.softdepot.core.models.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class ProgramDAO implements DAO<Program> {
    private static DeveloperDAO developerDAO = new DeveloperDAO();
    private static TagDAO tagDAO = new TagDAO();
    private static DegreeOfBelongingDAO degreeOfBelongingDAO = new DegreeOfBelongingDAO();
    private static ReviewDAO reviewDAO = new ReviewDAO();

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
    public int add(Program program) {
        if (!exists(program.getName(), program.getDeveloperId())) {
            try {
                PreparedStatement statement = connection.prepareStatement(
                        "INSERT INTO program (program_name, price, description, logo_url, installer_windows_url," +
                                "installer_linux_url, installer_macos_url, screenshots_url, developer_id) " +
                                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING id"
                );

                statement.setString(1, program.getName());
                statement.setBigDecimal(2, program.getPrice());
                statement.setString(3, program.getDescription());
                statement.setString(4, program.getLogoUrl());
                statement.setString(5, program.getInstallerWindowsUrl());
                statement.setString(6, program.getInstallerLinuxUrl());
                statement.setString(7, program.getInstallerMacosUrl());
                statement.setInt(9, program.getDeveloperId());
                //массив varchar
                Array screenshotsUrl = connection.createArrayOf(
                        "VARCHAR", program.getScreenshotsUrl().toArray()
                );
                statement.setArray(8, screenshotsUrl);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    return resultSet.getInt("id");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else {
            String msg = String.format("Program [name=%s, developer_id=%d] already exists",
                    program.getName(), program.getDeveloperId());
        }
        return -1;
    }

    @Override
    public void update(Program program) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE program SET program_name=?, price=?, description=?, logo_url=?," +
                            "installer_windows_url=?, installer_linux_url=?, installer_macos_url=?," +
                            "screenshots_url=?, developer_id=? WHERE id=?"
            );

            statement.setString(1, program.getName());
            statement.setBigDecimal(2, program.getPrice());
            statement.setString(3, program.getDescription());
            statement.setString(4, program.getLogoUrl());
            statement.setString(5, program.getInstallerWindowsUrl());
            statement.setString(6, program.getInstallerLinuxUrl());
            statement.setString(7, program.getInstallerMacosUrl());
            statement.setInt(9, program.getDeveloperId());
            statement.setInt(10, program.getId());

            //массив varchar
            Array screenshotsUrl = connection.createArrayOf(
                    "VARCHAR", program.getScreenshotsUrl().toArray()
            );
            statement.setArray(8, screenshotsUrl);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM program WHERE id=?"
            );
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Program getById(int id) throws Exception {
        Program program = null;
        try {
            PreparedStatement statement =
                    connection.prepareStatement("SELECT * FROM program WHERE id = ?");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Developer developer = developerDAO.getById(
                        resultSet.getInt("developer_id")
                );

                //Преобразование java.sql.Array в List<URL>
                Array tempSqlArray = resultSet.getArray("screenshots_url");
                String[] screenshotsUrlStrArr = (String[])tempSqlArray.getArray();
                List<String> screenshotsUrlList = Arrays.stream(screenshotsUrlStrArr).toList();

                program = new Program(resultSet.getInt("id"));
                program.setName(resultSet.getString("program_name"));
                program.setPrice(resultSet.getBigDecimal("price"));
                program.setDescription(resultSet.getString("description"));
                program.setLogoUrl(resultSet.getString("logo_url"));
                program.setInstallerWindowsUrl(resultSet.getString("installer_windows_url"));
                program.setInstallerLinuxUrl(resultSet.getString("installer_linux_url"));
                program.setInstallerMacosUrl(resultSet.getString("installer_macos_url"));
                program.setDeveloperId(resultSet.getInt("developer_id"));
                program.setScreenshotsUrl(screenshotsUrlList);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (program == null) {
            String msg = String.format("Program [id=%d] does not exist", id);
            throw new Exception(msg);
        }

        return program;
    }

    //Добавление ключевого слова программе
    public int addTag(int programId, int tagId, int degreeOfBelongingValue) throws Exception {
        if (!hasTag(programId, tagId)) {
            DegreeOfBelonging degreeOfBelongingNew = new DegreeOfBelonging();

            degreeOfBelongingNew.setTagId(tagId);
            degreeOfBelongingNew.setDegreeOfBelongingValue(degreeOfBelongingValue);
            degreeOfBelongingNew.setProgramId(programId);

            return degreeOfBelongingDAO.add(degreeOfBelongingNew);
        } else {
            String msg = String.format("The program [id=%d] already has a degree of belonging  " +
                    "to this tag [id=%d]", programId, tagId);
            throw new Exception(msg);
        }
    }

    //Удаление ключевого слова у программы
    public void removeTag(int programId, int tagId) throws Exception {
        Tag tag = tagDAO.getById(tagId);
        DegreeOfBelonging degreeOfBelonging = degreeOfBelongingDAO.get(tagId, programId);
        if (degreeOfBelonging == null) {
            throw new Exception("The program does not have a degree of belonging to this tag");
        } else {
            degreeOfBelongingDAO.delete(degreeOfBelonging.getId());
        }
    }

    //обновление степени принадлежности к тегу
    public void updateTag(int programId, int tagId, int degreeOfBelongingValue) throws Exception {
        Tag tag = tagDAO.getById(tagId);
        DegreeOfBelonging degreeOfBelonging = degreeOfBelongingDAO.get(tagId, programId);
        if (degreeOfBelonging == null) {
            throw new Exception("The program does not have a degree of belonging to this tag");
        } else {
            DegreeOfBelonging degreeOfBelongingNew = new DegreeOfBelonging(degreeOfBelonging.getId());
            degreeOfBelongingNew.setDegreeOfBelongingValue(degreeOfBelongingValue);
            degreeOfBelongingDAO.update(degreeOfBelonging);
        }
    }

    public Program getByNameAndDeveloperId(String programName, int developerId) {
        Program program = null;

        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(
                    "SELECT * FROM program WHERE program_name=? AND developer_id=?"
            );
            statement.setString(1, programName);
            statement.setInt(2, developerId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                program = new Program(resultSet.getInt("id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return program;
    }

    public boolean hasTag(int programId, int tagId) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM degree_of_belonging WHERE " +
                            "program_id=? AND tag_id=?"
            );
            statement.setInt(1, programId);
            statement.setInt(2, tagId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean exists(String program_name, int developer_id) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM program WHERE program_name=? AND developer_id=?"
            );
            statement.setString(1, program_name);
            statement.setInt(2, developer_id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public int getId(Program program) throws Exception {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM program WHERE program_name=? AND developer_id=?"
            );
            statement.setString(1, program.getName());
            statement.setInt(2, program.getDeveloperId());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String msg = String.format("The program [name=%s, developer_id=%d] does not exist",
                program.getName(), program.getDeveloperId());
        throw new Exception(msg);
    }

    public List<Program> getAll() {
        List<Program> programs = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM program"
            );
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                //Преобразование java.sql.Array в List<URL>
                Array tempSqlArray = resultSet.getArray("screenshots_url");
                String[] screenshotsUrlStrArr = (String[])tempSqlArray.getArray();
                List<String> screenshotsUrlList = Arrays.stream(screenshotsUrlStrArr).toList();

                Program program = new Program(
                        resultSet.getInt("id"),
                        resultSet.getString("program_name"),
                        resultSet.getBigDecimal("price"),
                        resultSet.getString("description"),
                        resultSet.getString("logo_url"),
                        resultSet.getString("installer_windows_url"),
                        resultSet.getString("installer_linux_url"),
                        resultSet.getString("installer_macos_url"),
                        screenshotsUrlList,
                        resultSet.getInt("developer_id")

                );
                programs.add(program);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return programs;
    }

    public List<Program> getByName(String programName) {
        List<Program> programs = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM program WHERE program_name=?"
            );
            statement.setString(1, programName);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                //Преобразование java.sql.Array в List<URL>
                Array tempSqlArray = resultSet.getArray("screenshots_url");
                String[] screenshotsUrlStrArr = (String[])tempSqlArray.getArray();
                List<String> screenshotsUrlList = Arrays.stream(screenshotsUrlStrArr).toList();

                Program program = new Program(
                        resultSet.getInt("id"),
                        resultSet.getString("program_name"),
                        resultSet.getBigDecimal("price"),
                        resultSet.getString("description"),
                        resultSet.getString("logo_url"),
                        resultSet.getString("installer_windows_url"),
                        resultSet.getString("installer_linux_url"),
                        resultSet.getString("installer_macos_url"),
                        screenshotsUrlList,
                        resultSet.getInt("developer_id")

                );
                programs.add(program);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return programs;
    }

    public List<Review> getAllReviews(Program program) {
        return reviewDAO.getAllAboutProgram(program);
    }

    public float getAverageEstimation(Program program) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                "SELECT AVG(estimation) FROM review WHERE program_id=?"
            );
            statement.setInt(1, program.getId());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getFloat("avg");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
