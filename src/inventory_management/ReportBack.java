package inventory_management;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.sql.*;

public class ReportBack {

    private int id;
    private String reportTitle;
    private String problem;
    private String reportDate;
    private int userId;
    private int deviceId;

    public ReportBack(int id, String reportTitle, String problem, String reportDate, int userId, int deviceId) {
        this.id = id;
        this.reportTitle = reportTitle;
        this.problem = problem;
        this.reportDate = reportDate;
        this.userId = userId;
        this.deviceId = deviceId;
    }

    public ReportBack() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReportTitle() {
        return reportTitle;
    }

    public void setReportTitle(String reportTitle) {
        this.reportTitle = reportTitle;
    }

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public String getReportDate() {
        return reportDate;
    }

    public void setReportDate(String reportDate) {
        this.reportDate = reportDate;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getDeviceId() {
        return deviceId;
    }

    public void setDevice(int deviceId) {
        this.deviceId = deviceId;
    }

    public void addReport(String username, String deviceName, String reportTitle, String problem) {
        String url = "jdbc:sqlite:inventory_management.db";
        String sqlDeviceId = "SELECT Device_id FROM devices WHERE Device_Name = ?";
        String sqlUserId = "SELECT id FROM users WHERE Username = ?";
        String sqlInsert = "INSERT INTO Reports (users_id, Device_id, Report_title, Report_Date, Problem) VALUES (?, ?, ?, CURRENT_TIMESTAMP, ?)";

        try (Connection con = DriverManager.getConnection(url); PreparedStatement pstmt1 = con.prepareStatement(sqlDeviceId); PreparedStatement pstmt2 = con.prepareStatement(sqlUserId); PreparedStatement pstmtInsert = con.prepareStatement(sqlInsert)) {

            // Fetch Device_id
            pstmt1.setString(1, deviceName);
            ResultSet rsDevice = pstmt1.executeQuery();
            if (!rsDevice.next()) {
                System.out.println("Device not found.");
                return;
            }
            int deviceId = rsDevice.getInt("Device_id");

            // Fetch User_id
            pstmt2.setString(1, username);
            ResultSet rsUser = pstmt2.executeQuery();
            if (!rsUser.next()) {
                System.out.println("User not found.");
                return;
            }
            int userId = rsUser.getInt("id");

            // Insert report
            pstmtInsert.setInt(1, userId);
            pstmtInsert.setInt(2, deviceId);
            pstmtInsert.setString(3, reportTitle);
            pstmtInsert.setString(4, problem);

            int rowsAffected = pstmtInsert.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Report added successfully!");
            } else {
                System.out.println("Failed to add report.");
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    void RemoveReports(int ID) {
        String url = "jdbc:sqlite:inventory_management.db";
        String sql = "DELETE FROM Reports where ID = ? ";

        try (Connection con = DriverManager.getConnection(url); PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setInt(1, ID);
            pstmt.executeUpdate();

            System.out.println("Reports Deleted successfully!");
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private String URL = "jdbc:sqlite:inventory_management.db";
        public void ReportDetailes() {
        String sql = "SELECT " +
                     "users.FullName, " +
                     "Reports.Problem, " +
                     "Devices.Model, " +
                     "Devices.Device_Name " +
                     "FROM Reports " +
                     "INNER JOIN users ON Reports.users_id = users.id " +
                     "INNER JOIN Devices ON Reports.Device_id = Devices.Device_id";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            String user = System.getProperty("user.name"); // Get the current user's name
        String filePath = "C:\\Users\\" + user + "\\Desktop\\ReportDetailes.csv";
            try (FileWriter myWriter = new FileWriter(filePath)) {
               
                myWriter.append("FullName,Device,Model,Problem\n");

               
                while (rs.next()) {
                    String fullName = rs.getString("FullName");
                    String problem = rs.getString("Problem");
                    String model = rs.getString("Model");
                    String deviceName = rs.getString("Device_Name");

                   
                    myWriter.write(String.format("%s,%s,%s,%s\n", fullName,deviceName , model, problem));
                }

                System.out.println("Successfully wrote report details to Excel File");
            } catch (IOException e) {
                System.err.println("Error writing to the file: " + e.getMessage());
            }

        } catch (SQLException e) {
            System.err.println("Error fetching report details: " + e.getMessage());
        }
    }

              
    public void setVisible(boolean b) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
