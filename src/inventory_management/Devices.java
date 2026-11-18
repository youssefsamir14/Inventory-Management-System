package inventory_management;

import java.sql.*;

public class Devices extends ReportBack {

    private int deviceId;
    private String deviceName;
    private boolean deviceStatus;
    private int deviceCount;
    private String model;
    private String SerialNumber;

    public Devices(int deviceId, String deviceName, boolean deviceStatus, int deviceCount, String model) {
        super.setDevice(deviceId);
        this.deviceName = deviceName;
        this.deviceStatus = deviceStatus;
        this.deviceCount = deviceCount;
        this.model = model;
    }
    public Devices(){};

    @Override
    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public boolean isDeviceStatus() {
        return deviceStatus;
    }

    public void setDeviceStatus(boolean deviceStatus) {
        this.deviceStatus = deviceStatus;
    }



    public void setDeviceCount(int deviceCount) {
        this.deviceCount = deviceCount;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

   public boolean addDevices(String deviceName ,  String Model ,int deviceCount ){
        String sql_in = "Insert into Devices (Device_Name,Model,Device_Count) values(?,?,?)";
    try {
            Connection con = DriverManager.getConnection("jdbc:sqlite:inventory_management.db");
            PreparedStatement pstmt = con.prepareStatement(sql_in);
               pstmt.setString(1, deviceName);
               pstmt.setString(2, Model);
               pstmt.setInt(3,deviceCount);
               if(!deviceName.isEmpty() && !Model.isEmpty()){
               
                 pstmt.executeUpdate();
               System.out.println("Inserted Scussful");
               return true;
               }
        } catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
            return false;
        }
        return false;
    }
     
     
        public void DeleteDevices(String SerialNumber){
         this.SerialNumber=SerialNumber;
        String sql_in = "DELETE FROM Devices WHERE SerialNumber = ?";
    try {
            Connection con = DriverManager.getConnection("jdbc:sqlite:inventory_management.db");
            PreparedStatement pstmt = con.prepareStatement(sql_in);
               pstmt.setString(1, SerialNumber);
               pstmt.executeUpdate();
               System.out.println("Delete Scussful");
               
        } catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
         
    }

    public void ShowDevices() {
        String sql = "SELECT * FROM Devices";

        try (Connection con = DriverManager.getConnection("jdbc:sqlite:inventory_management.db"); Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int Device_id = rs.getInt("Device_id");
                String Device_name = rs.getString("Device_Name");
                int Device_Count = rs.getInt("Device_Count");
                int Device_Status = rs.getInt("Device_Stauts");

                System.out.println("Device ID: " + Device_id);
                System.out.println("Device Name: " + Device_name);
                System.out.println("Device Count: " + Device_Count);
                System.out.println("Device Status: " + Device_Status);
            }
        } catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    public int getDeviceCount() {
        String sql = "SELECT Sum(Device_Count) FROM Devices";
        int Device_Count = 0;
        try (Connection con = DriverManager.getConnection("jdbc:sqlite:inventory_management.db"); Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {

                Device_Count = rs.getInt(1);
                System.out.println("Device Count: " + Device_Count);
            }
        } catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        return Device_Count;

    }
}
