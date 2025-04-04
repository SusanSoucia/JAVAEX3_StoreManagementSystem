
import java.awt.*;
import javax.swing.*;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author susan
 */
public class InsertFC extends JDialog {
    private JTextField nameField;
    private JSpinner priceSpinner;
    private JSpinner amtSpinner;
    private boolean confirmed = false;

    public InsertFC (JFrame parent){
        super(parent, "插入新商品", true);
        setupUI();
        pack();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(parent);
    }

    private void setupUI() {
        JPanel panel = new JPanel(new GridLayout(4,2,5,5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        nameField = new JTextField(15);
        priceSpinner = new JSpinner(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));
        amtSpinner = new JSpinner(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));

        panel.add(new JLabel("商品名称:"));
        panel.add(nameField);
        panel.add(new JLabel("商品单价:"));
        panel.add(priceSpinner);
        panel.add(new JLabel("数量:"));
        panel.add(amtSpinner);

        //制作按钮
        JButton confirmBtn = new JButton("确认");
        JButton cancelBtn = new JButton("取消");

        confirmBtn.addActionListener(e -> {
            if(validateInput()){
                confirmed = true;
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "请检查输入的商品信息是否正确！", "错误", JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelBtn.addActionListener(e -> {
            confirmed = false;
            dispose();
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(confirmBtn);
        buttonPanel.add(cancelBtn);

        add(panel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

    }

    private boolean validateInput() {
        if (nameField.getText().trim().isEmpty()){
            JOptionPane.showMessageDialog(this, "商品名称不能为空！", "错误", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    public DataVO getInputData(){
        if (!confirmed) {
            return null;
        }
        return new DataVO(
        0,
        nameField.getText().trim(),
        (int) priceSpinner.getValue(),
        (int) amtSpinner.getValue()
        );
    }
}
