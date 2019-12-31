package com.msj.android_imageselector.bean;

public class FolderBean {
    /**
     * 当前文件夹的路径
     */
    private String dir;

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;

        int lastIndecof = this.dir.lastIndexOf("/");
        this.name = this.dir.substring(lastIndecof+1);
    }

    public String getFirstImgPath() {
        return firstImgPath;
    }

    public void setFirstImgPath(String firstImgPath) {
        this.firstImgPath = firstImgPath;
    }

    public String getName() {
        return name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    private String firstImgPath;
    private String name;
    private  int count;
}
