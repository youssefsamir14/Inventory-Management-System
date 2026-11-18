package inventory_management;

import java.sql.*;

public class Users extends ReportBack {

    private int id;
    private String fullName;
    private String username;
    private String password;
    private int jobId;

    public Users() {
    }

    public Users(int id, String fullName, String username, String password) {
        super.setUserId(id);
        this.fullName = fullName;
        this.username = username;
        this.password = password;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    public int getJobId() {
        return jobId;
    }

    public void addUser(String fullName, String username, String password, int jobId) {
    String url = "jdbc:sqlite:inventory_management.db"; // Database URL
    String insertUserSql = "INSERT INTO users (FullName, Username, Password, JOB_ID) VALUES (?, ?, ?, ?)";

    try (Connection con = DriverManager.getConnection(url)) {

        // Step 2: Insert the user into the users table with the provided job ID
        try (PreparedStatement pstmtInsertUser = con.prepareStatement(insertUserSql)) {
            pstmtInsertUser.setString(1, fullName);
            pstmtInsertUser.setString(2, username);
            pstmtInsertUser.setString(3, password);
            pstmtInsertUser.setInt(4, jobId);
            pstmtInsertUser.executeUpdate();
            System.out.println("User added successfully!");
        }

    } catch (SQLException e) {
        System.out.println("Error: " + e.getMessage());
    }
}


    public void RemoveUser(String username) {
        String url = "jdbc:sqlite:inventory_management.db"; // Database URL
        String sql = "DELETE FROM Users where username = ? ";

        try (Connection con = DriverManager.getConnection(url); PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setString(1, username);
            pstmt.executeUpdate();

            System.out.println("User Deleted successfully!");
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public boolean CheckLogin(String username, String password, String job) {
        String url = "jdbc:sqlite:inventory_management.db";
        String sql = "SELECT 1 FROM users WHERE username = ?  AND password = ? COLLATE NOCASE AND job_id = ?";

        // Validate inputs
        if (username == null || password == null || job == null) {
            throw new IllegalArgumentException("Username, password, or job cannot be null.");
        }

        try (Connection con = DriverManager.getConnection(url); PreparedStatement pstmt = con.prepareStatement(sql)) {

            // Set username and password
            pstmt.setString(1, username.trim());
            pstmt.setString(2, password.trim());

            // Map job roles to IDs
            int jobId;
            if ("Hr".equalsIgnoreCase(job)) {
                jobId = 1;
            } else if ("Technical".equalsIgnoreCase(job)) {
                jobId = 2;
            } else {
                throw new IllegalArgumentException("Invalid job role: " + job);
            }
            pstmt.setInt(3, jobId);

            // Execute query
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next(); // Return true if a match is found
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Log SQL errors
            return false;
        }
    }
}
