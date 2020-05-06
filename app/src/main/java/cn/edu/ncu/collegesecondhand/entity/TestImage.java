package cn.edu.ncu.collegesecondhand.entity;

/**
 * Created by ren lingyun on 2020/4/15 12:14
 */
public class TestImage {
    private String name;
    private String path;

    public TestImage(String name, String path) {
        this.name = name;
        this.path = path;
    }

    public TestImage() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
