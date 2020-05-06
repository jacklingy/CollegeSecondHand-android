package cn.edu.ncu.collegesecondhand.entity;

/**
 * Created by ren lingyun on 2020/4/15 16:05
 */
public class MyBanner {
    private String imagePath;
    private String intent;
    private int productId;

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public MyBanner() {
    }

    public MyBanner(String imagePath, int productId) {
        this.imagePath = imagePath;
        this.productId = productId;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getIntent() {
        return intent;
    }

    public void setIntent(String intent) {
        this.intent = intent;
    }

    public MyBanner(String imagePath, String intent) {
        this.imagePath = imagePath;
        this.intent = intent;
    }
}
