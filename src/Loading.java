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
    private ImageIcon gifIcon;

    public Loading(JFrame parent) {
        super(parent, true); // 模态对话框
        setUndecorated(true); // 隐藏标题栏
        setSize(200, 200);
        setLocationRelativeTo(parent); // 居中显示
        
        // 加载 GIF 资源
        gifIcon = new ImageIcon("D:\\VSCODE\\java\\stroeSystem\\src\\resources\\loading.gif");
        JLabel gifLabel = new JLabel(gifIcon);
        gifLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        // 透明背景
        getContentPane().setBackground(new Color(0, 0, 0, 0));
        setBackground(new Color(0, 0, 0, 0));
        add(gifLabel);

    }

    public void showAnimation(){
        setVisible(true);
    }

    public void hideAnimation() {
        // 关键步骤：强制释放 GIF 资源
        if (gifIcon != null) {
            Image image = gifIcon.getImage();
            image.flush(); // 终止动画线程
            gifIcon.setImage(null); // 清空引用
        }
        dispose(); // 关闭对话框
    }

}
