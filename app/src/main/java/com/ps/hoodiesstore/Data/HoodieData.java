package com.ps.hoodiesstore.Data;

public class HoodieData {
    private String name,about;
    private int price;
    private String size;
    private String image;
    private String product_id;

    public HoodieData(String name, int price, String size, String image, String product_id,String about) {
        this.name = name;
        this.price = price;
        this.size = size;
        this.image = image;
        this.product_id = product_id;
        this.about=about;
    }





    public HoodieData() {
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }
}
