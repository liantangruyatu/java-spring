import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class UpdatePassword {
    public static void main(String[] args) {
        // 数据库连接信息
        String url = "jdbc:mysql://localhost:3306/facility_repair_db?useSSL=false&serverTimezone=Asia/Shanghai";
        String username = "root";
        String password = "root";
        
        try {
            // 加载驱动
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // 建立连接
            Connection conn = DriverManager.getConnection(url, username, password);
            
            // 创建Statement
            Statement stmt = conn.createStatement();
            
            // 执行更新语句，将所有用户密码设置为administer
            String sql = "UPDATE users SET password = 'administer'";
            int rowsAffected = stmt.executeUpdate(sql);
            
            System.out.println("成功更新了 " + rowsAffected + " 个用户的密码为 'administer'");
            
            // 关闭连接
            stmt.close();
            conn.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}