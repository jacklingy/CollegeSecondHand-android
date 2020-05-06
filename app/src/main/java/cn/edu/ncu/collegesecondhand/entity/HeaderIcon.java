package cn.edu.ncu.collegesecondhand.entity;

/**
 * Created by ren lingyun on 2020/4/18 12:45
 */
public class HeaderIcon {
    private String imageUrl;
    private String name;
    private String keyWord;
    private int image;

    public HeaderIcon(String imageUrl, String name, String keyWord,int image) {
        this.imageUrl = imageUrl;
        this.name = name;
        this.keyWord = keyWord;
        this.image=image;
    }

    public HeaderIcon() {
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }
}
