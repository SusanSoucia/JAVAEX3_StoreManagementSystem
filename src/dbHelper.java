/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 *
 * @author susan
 */
public class dbHelper {
    private String id;
    private  String password;
    private static boolean logFlag = false;
    Connection conn;

    public void setID(String id){
        this.id = id;
    }

    public void setPass(String pass){
        this.password =pass;
        //从用户输入获取
    }

    public boolean connectDB(){
        String url = "jdbc:mysql://localhost:3306/store";
        String user = "root";
        String dbPassword = "123456";
        try {
             this.conn = DriverManager.getConnection(url,user,dbPassword);
             System.out.println("连接成功");
             return true;
        } 
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean  login(){
        try{
            String sql = "select password from userinfo where id = ?";
            PreparedStatement stmt = this.conn.prepareStatement(sql);
            stmt.setString(1, this.id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()){
                if (this.password.equals(rs.getString("password"))){
                    this.logFlag = true;
                    dbHelper.logFlag=true;
                    return true;
                }
                else{
                    return false;
                }
            }
            return false;
        }
        catch(SQLException e){
            e.printStackTrace();
            return false;
        }
        
    }

    public boolean checkState(){
        if (logFlag = true){
            this.connectDB();
        }

        return this.logFlag;
    }

    public List<DataVO> getData (){
        //从数据库中获取data
        List<DataVO> datalist = new ArrayList<>();
        String sql = "select * from item";

        try (PreparedStatement stmt = conn.prepareStatement(sql);ResultSet rs = stmt.executeQuery()){
            while(rs.next()){
                int itemid = rs.getInt("itemid");
                String itemname = rs.getString("itemname");
                int itemprice = rs.getInt("itemprice");
                int itemamt = rs.getInt("itemamt");

                datalist.add(new DataVO(itemid, itemname, itemprice, itemamt));
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }

        return datalist;
    }

    public boolean updateData(int itemId, String column, Object value) throws SQLException {
        String sql = "UPDATE item SET " + column + " = ? WHERE itemid = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            // 根据字段类型设置参数
            if (value instanceof String) {
                pstmt.setString(1, (String) value);
            } else if (value instanceof Integer) {
                pstmt.setInt(1, (Integer) value);
            }
            pstmt.setInt(2, itemId);
            return pstmt.executeUpdate() > 0;
        }
    }

    public boolean insertData(DataVO data) throws SQLException {
        String sql = "INSERT INTO item (itemname, itemprice, itemamt) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, data.getName());
            pstmt.setInt(2, data.getPrice());
            pstmt.setInt(3, data.getAmt());
            return pstmt.executeUpdate() > 0;
        }
    }

    public boolean deleteData(int itemId) throws SQLException {
        String sql = "DELETE FROM item WHERE itemid = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, itemId);
            return pstmt.executeUpdate() > 0;
        }
        //删除数据
    }

    public List<DataVO> findData(Map<String, Object> conditions) throws SQLException {
        List<DataVO> result = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM item WHERE 1=1");
        List<Object> params = new ArrayList<>();

        // 动态构建查询条件
        if (conditions.containsKey("itemid")) {
            sql.append(" AND itemid = ?");
            params.add(conditions.get("itemid"));
        }
        if (conditions.containsKey("itemname")) {
            sql.append(" AND itemname LIKE ?");
            params.add("%" + conditions.get("itemname") + "%"); // 模糊查询
        }
        if (conditions.containsKey("itemprice")) {
            sql.append(" AND itemprice = ?");
            params.add(conditions.get("itemprice"));
        }
        if (conditions.containsKey("itemamt")) {
            sql.append(" AND itemamt = ?");
            params.add(conditions.get("itemamt"));
        }

        try (PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                pstmt.setObject(i + 1, params.get(i));
            }
            
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                result.add(new DataVO(
                    rs.getInt("itemid"),
                    rs.getString("itemname"),
                    rs.getInt("itemprice"),
                    rs.getInt("itemamt")
                ));
            }
        }
        return result;
    }

    public void byeDB(){
        //退出数据库登录状态
    }


}
