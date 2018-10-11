package es.source.code.model;


public class AllDish {
    private String dishName;
    private int dishImageId;
    private String dishPrice;
    private String dishSelect;
    private int dishId;


    public AllDish(int dishId,String dishName, int dishImageId, String dishPrice) {
        this.dishId = dishId;
        this.dishName = dishName;
        this.dishImageId = dishImageId;
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

    public String getDishPrice() {
        return dishPrice;
    }

    public void setDishPrice(String dishPrice) {
        this.dishPrice = dishPrice;
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
