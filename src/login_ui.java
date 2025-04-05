/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.awt.*;
import  java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
/**
 *
 * @author susan
 */
public class login_ui extends JFrame{
    private JButton login = new JButton("点击登录");
    private  JTextField inputID;
    private  JPasswordField password;
    private dbHelper db;
    private JPanel mainPanel;
    private JDialog wrong;


    ///////
    public login_ui(){
        setSize(300, 200); // 调整窗口大小以适应新布局
        setTitle("Please login!");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainPanel = new JPanel(new GridBagLayout()); // 改用 GridBagLayout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // 组件间距
        gbc.anchor = GridBagConstraints.WEST; // 左对齐

        // 账号标签和输入框
        JLabel idLabel = new JLabel("账号:");
        inputID = new JTextField(15);
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(idLabel, gbc);
        gbc.gridx = 1;
        mainPanel.add(inputID, gbc);

        // 密码标签和输入框
        JLabel pwdLabel = new JLabel("密码:");
        password = new JPasswordField(15);
        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(pwdLabel, gbc);
        gbc.gridx = 1;
        mainPanel.add(password, gbc);

        // 登录按钮（居中显示）
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2; // 跨两列
        gbc.anchor = GridBagConstraints.CENTER; // 居中对齐
        login.addActionListener(new loginListener());
        mainPanel.add(login, gbc);

        add(mainPanel);
        setIconImage(new ImageIcon("src\\resources\\登录.png").getImage());
        setVisible(true);
    
    }

    private class loginListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO 连接数据库
            db = new dbHelper();
            db.connectDB();
            db.setID(inputID.getText().trim());
            String ps = new String(password.getPassword());
            db.setPass(ps);
            if (db.login()) {
                System.out.println("登录成功");
                /* 
                Loading turning = new Loading(login_ui.this);
                turning.setVisible(true);
                turning.dispose();
                */
                dispose(); // 关闭登录窗口
                new MainWindow_ui().setVisible(true); // 假设 MainWindow 是主界面
            } else {
                JOptionPane.showMessageDialog(
                    login_ui.this,
                    "请重新输入帐号与密码",
                    "登录失败",
                    JOptionPane.ERROR_MESSAGE
                );
            
            
                }
    }
        
        
            

        
        }
        
    }

