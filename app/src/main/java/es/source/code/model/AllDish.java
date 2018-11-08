package es.source.code.model;


import android.graphics.Bitmap;

import java.io.Serializable;

public class AllDish  implements Serializable {
    private String dishName;
    private int dishImageId;
    private Bitmap dishImg;
    private int dishPrice;
    private String dishRemark;
    private String dishSelect;
    private int number;
    private int inventory;
    private int dishId;

    public AllDish() {

    }

    public AllDish(int dishId,String dishName, Bitmap dishImg, int dishPrice) {
        this.dishId = dishId;
        this.dishName = dishName;
        this.dishImg = dishImg;
        this.dishPrice = dishPrice;

    }

    public AllDish(int dishId,String dishName, int dishPrice) {
        this.dishId = dishId;
        this.dishName = dishName;
        this.dishPrice = dishPrice;

    }

    public AllDish(int dishId,String dishName, Bitmap dishImg, int inventory,int dishPrice) {
        this.dishId = dishId;
        this.dishName = dishName;
        this.dishImg = dishImg;
        this.inventory = inventory;
        this.dishPrice = dishPrice;

    }

    public int getDishId() {
        return dishId;
    }

    public void setDishId(int dishId) {
        this.dishId = dishId;
    }

    public String getDishSelect() {
        return dishSelect;
    }

    public void setDishSelect(String dishSelect) {
        this.dishSelect = dishSelect;
    }

    public int getInventory() {
        return inventory;
    }

    public void setInventory(int inventory) {
        this.inventory = inventory;
    }

    public int getDishPrice() {
        return dishPrice;
    }

    public void setDishPrice(int dishPrice) {
        this.dishPrice = dishPrice;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Bitmap getDishImg() {
        return dishImg;
    }

    public void setDishImg(Bitmap dishImg) {
        this.dishImg = dishImg;
    }

    public String getDishRemark() {
        return dishRemark;
    }

    public void setDishRemark(String dishRemark) {
        this.dishRemark = dishRemark;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public int getDishImageId() {
        return dishImageId;
    }

    public void setDishImageId(int dishImageId) {
        this.dishImageId = dishImageId;
    }
}
