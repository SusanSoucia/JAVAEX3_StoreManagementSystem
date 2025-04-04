import java.awt.BorderLayout;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author susan
 */
public class MainWindow_ui extends JFrame {
    private JPanel mainPanel;
    private JPanel buttonPanel;
    private JTable datatable;
    private JMenuBar bar;
    private JMenu fc;
    private JMenuItem fc1, fc2;
    private dbHelper db;
    private JScrollPane scroll;
    private JButton deleteBtn;
    tableModel tb;

    public MainWindow_ui(){
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(600, 400);
        db = new dbHelper();
        db.checkState();
        tb = new tableModel(db.getData());
        List<DataVO> data = db.getData();
    if (data.isEmpty()) {
        System.err.println("数据库未返回数据！");
    }

    bar = new JMenuBar();
    fc = new JMenu("功能");
    fc1 = new JMenuItem("查找");
    fc2 = new JMenuItem("插入");
    fc.add(fc1);
    fc.add(fc2);
    fc1.addActionListener(e->showFindDialog());
    fc2.addActionListener(e->showInsertDialog());
    bar.add(fc);

    deleteBtn = new JButton("删除选中项");
    deleteBtn.setVisible(false);
    deleteBtn.addActionListener(e -> deleteSelectedRow());
    buttonPanel = new JPanel();
    buttonPanel.add(deleteBtn);

        mainPanel = new JPanel(new BorderLayout());
        datatable = new JTable(tb);
        scroll = new JScrollPane(datatable);
        datatable.setVisible(true);
        datatable.getSelectionModel().addListSelectionListener(e -> toggleDeleteButton());

       
        mainPanel.add(scroll,BorderLayout.CENTER);
        mainPanel.add(buttonPanel,BorderLayout.SOUTH);
        mainPanel.setVisible(true);
        
        setJMenuBar(bar);
        add(mainPanel);
        setVisible(true);

    }

    private void toggleDeleteButton() {
        int selectedRow = datatable.getSelectedRow();
        deleteBtn.setVisible(selectedRow != -1);
    }
/* 
    private void setPagecontent(){
        List<DataVO> tableDataVO = db.getData();
        int totalNum = tableDataVO.size();
        int n = 5; //每页的记录条数
         while (totalNum%5 != 0 )
         {
            
         }
}
*/
//控制方法
private void deleteSelectedRow() {
    int selectedRow = datatable.getSelectedRow();
    if (selectedRow == -1) return;

    // 获取选中行的ID
    int itemId = (int) datatable.getValueAt(selectedRow, 0);
    
    int confirm = JOptionPane.showConfirmDialog(
        this, 
        "确定要删除商品ID为 " + itemId + " 的记录吗？",
        "确认删除",
        JOptionPane.YES_NO_OPTION
    );

    if (confirm == JOptionPane.YES_OPTION) {
        try {
            if (db.deleteData(itemId)) {
                tb.setDataList(db.getData()); // 刷新表格
                deleteBtn.setVisible(false); // 隐藏按钮
                JOptionPane.showMessageDialog(this, "删除成功！");
            } else {
                JOptionPane.showMessageDialog(this, "删除失败！");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, 
                "数据库错误: " + ex.getMessage(),
                "错误",
                JOptionPane.ERROR_MESSAGE);
        }
    }
}
private void showFindDialog() {
        FindFC dialog = new FindFC(this);
        dialog.setVisible(true);

        if (dialog.isConfirmed()) {
            Map<String, Object> conditions = dialog.getSearchConditions();
            
            // 处理非法ID输入
            if (conditions.containsKey("itemid_error")) {
                JOptionPane.showMessageDialog(this, "商品ID必须为数字！", 
                    "输入错误", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                List<DataVO> result = db.findData(conditions);
                if (!result.isEmpty()) {
                    tb.setDataList(result);
                } else {
                    JOptionPane.showMessageDialog(this, "未找到匹配记录");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, 
                    "数据库错误：" + ex.getMessage(),
                    "错误",
                    JOptionPane.ERROR_MESSAGE);
            }
        } else {
            // 点击"显示全部"时恢复数据
            refreshTable();
        }
    }

private void refreshTable() {
    tb.setDataList(db.getData());
}

private void showInsertDialog() {
    InsertFC dialog = new InsertFC(this);
    dialog.setVisible(true);
    
    DataVO newData = dialog.getInputData();
    if (newData != null) {
        try {
            if (db.insertData(newData)) {
                // 刷新表格
                tb.setDataList(db.getData());
                JOptionPane.showMessageDialog(this, "插入成功！");
            } else {
                JOptionPane.showMessageDialog(this, "插入失败！", "错误", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, 
                "数据库错误：" + ex.getMessage(),
                "错误",
                JOptionPane.ERROR_MESSAGE);
        }
    }
}


private class tableModel extends AbstractTableModel{
    private List<DataVO> dataList;
    private final String[] columnNames = {"商品ID", "商品名称", "价格", "库存"};

    public tableModel(List<DataVO> dataList) {
        this.dataList = dataList;
    }

    @Override
    public int getRowCount() {
        return dataList.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int row, int col) {
        DataVO data = dataList.get(row);
        switch (col) {
            case 0: return data.getId();
            case 1: return data.getName();
            case 2: return data.getPrice();
            case 3: return data.getAmt();
            default: return null;
        }
    }

    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }

    // 更新数据并刷新表格
    public void setDataList(List<DataVO> dataList) {
        this.dataList = dataList;
        fireTableDataChanged();
        }

    @Override
    public boolean isCellEditable(int row , int column){
        return true;
    }

    @Override
    public void setValueAt(Object value,int row,int colunm){
        DataVO data = dataList.get(row);
        String columnName = getColumnName(colunm);
        boolean updateSuccess = false;

        try {
            switch(colunm){
                case 1:
                data.setName((String)value);
                updateSuccess = db.updateData(data.getId(), "itemname", value);
                break;

                case 3:
                data.setPrice(Integer.parseInt(value.toString()));
                updateSuccess = db.updateData(data.getId(), "itemprice", value);
                break;

                case 2:
                data.setAmt(Integer.parseInt(value.toString()));
                updateSuccess = db.updateData(data.getId(), "itemamt", value);
                break;
            }
        } 
        catch(NumberFormatException e){JOptionPane.showMessageDialog(MainWindow_ui.this, "输入格式错误: " + e.getMessage());}
        catch(SQLException e){JOptionPane.showMessageDialog(MainWindow_ui.this, "数据库错误: " + e.getMessage());}

        fireTableCellUpdated(row, colunm);
    }
    }
}
