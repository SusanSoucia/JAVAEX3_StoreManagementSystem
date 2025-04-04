/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.awt.*;
import javax.swing.*;
/**
 *
 * @author susan
 */
public class Loading extends  JDialog{
    public Loading(JFrame parent) {
        super(parent, true); // 模态对话框
        setUndecorated(true); // 隐藏标题栏
        setSize(200, 200);
        setLocationRelativeTo(parent); // 居中显示
        
        // 加载 GIF 资源
        ImageIcon gifIcon = new ImageIcon("D:\\VSCODE\\java\\stroeSystem\\loading.gif");
        JLabel gifLabel = new JLabel(gifIcon);
        gifLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        // 透明背景
        getContentPane().setBackground(new Color(0, 0, 0, 0));
        setBackground(new Color(0, 0, 0, 0));
        
        add(gifLabel);
    }

}
