package cn.edu.ncu.collegesecondhand.entity;

/**
 * Created by ren lingyun on 2020/4/15 0:56
 */
public class SubCategory {
    private int id;
    private String name;
    private String image;
    private int categoryId;
    private int type;//0-brand,1-feature

    public SubCategory() {
    }

    public SubCategory(String name, String image, int categoryId, int type) {
        this.name = name;
        this.image = image;
        this.categoryId = categoryId;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
