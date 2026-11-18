package inventory_management;

import java.util.ArrayList;
import java.sql.*;

public class Jobs extends Users {

    private int jobId;
    private String jobTitle;

    // Constructors
    public Jobs(int jobId, String jobTitle) {
        this.jobId = jobId;
        this.jobTitle = jobTitle;
    }
    public Jobs(){}
    // Getters and Setters
    @Override
    public int getJobId() {
        return jobId;
    }

    @Override
    public void setJobId(int jobId) {
        this.jobId = jobId;
    }


    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String[] getJobTitle() {
    String sql = "SELECT JOB_TITLE FROM JOBS";
    String[] jobTitles = new String[100]; // Assuming a maximum of 100 job titles
    int index = 0;

    try (Connection con = DriverManager.getConnection("jdbc:sqlite:inventory_management.db");
         Statement stmt = con.createStatement();
         ResultSet rs = stmt.executeQuery(sql)) {

        while (rs.next()) {
            jobTitles[index++] = rs.getString("JOB_TITLE");
        }
    } catch (SQLException ex) {
        System.out.println("Error: " + ex.getMessage());
    }

    // Trim the array to the actual number of elements
    String[] result = new String[index];
    System.arraycopy(jobTitles, 0, result, 0, index);

    return result;
}

}
