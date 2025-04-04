/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author susan
 */
public class DataVO {
    private int itemid;
    private String itemname;
    private int itemprice;
    private  int itemamt;
    // 构造方法
    public DataVO(int itemid, String itemname, int itemprice,int itemamt) {
        this.itemid = itemid;
        this.itemname = itemname;
        this.itemprice = itemprice;
        this.itemamt = itemamt;
    }

    // Getter 方法
    public int getId() { return itemid; }
    public String getName() { return itemname; }
    public int getPrice()  {return itemprice;}
    public int getAmt() {return itemamt;}

    //Setter 方法
    public void setId(int id){this.itemid = id;}
    public void setName(String name){this.itemname = name;}
    public void setPrice(int price){this.itemprice = price;}
    public void setAmt(int amt){this.itemamt = amt;}
}
