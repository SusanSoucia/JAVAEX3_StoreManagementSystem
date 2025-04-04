/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;
/**
 *
 * @author susan
 */
public class FindFC extends JDialog {
    private JTextField idField;
    private JTextField nameField;
    private JSpinner priceSpinner;
    private JSpinner amtSpinner;
    private boolean confirmed = false;

    public FindFC(JFrame parent) {
        super(parent, "查找商品", true);
        setupUI();
        pack();
        setLocationRelativeTo(parent);
    }

    private void setupUI() {
        JPanel panel = new JPanel(new GridLayout(5, 2, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // 输入字段
        idField = new JTextField();
        nameField = new JTextField();
        priceSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 100000, 1));
        amtSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 10000, 1));
        JButton showAllBtn = new JButton("显示全部");

        panel.add(new JLabel("商品ID："));
        panel.add(idField);
        panel.add(new JLabel("商品名称："));
        panel.add(nameField);
        panel.add(new JLabel("价格："));
        panel.add(priceSpinner);
        panel.add(new JLabel("库存："));
        panel.add(amtSpinner);
        panel.add(showAllBtn);

        // 按钮面板
        JButton findBtn = new JButton("查找");
        JButton cancelBtn = new JButton("取消");
        

        findBtn.addActionListener(e -> {
            confirmed = true;
            dispose();
        });
        
        cancelBtn.addActionListener(e -> dispose());
        showAllBtn.addActionListener(e -> {
            confirmed = false;
            dispose();
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(showAllBtn);
        buttonPanel.add(cancelBtn);
        buttonPanel.add(findBtn);

        add(panel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public Map<String, Object> getSearchConditions() {
        Map<String, Object> conditions = new HashMap<>();
        
        if (!idField.getText().trim().isEmpty()) {
            try {
                conditions.put("itemid", Integer.parseInt(idField.getText().trim()));
            } catch (NumberFormatException e) {
                conditions.put("itemid_error", true);
            }
        }
        if (!nameField.getText().trim().isEmpty()) {
            conditions.put("itemname", nameField.getText().trim());
        }
        if ((Integer) priceSpinner.getValue() != 0) {
            conditions.put("itemprice", priceSpinner.getValue());
        }
        if ((Integer) amtSpinner.getValue() != 0) {
            conditions.put("itemamt", amtSpinner.getValue());
        }
        return conditions;
    }

    public boolean isConfirmed() {
        return confirmed;
    }
}
