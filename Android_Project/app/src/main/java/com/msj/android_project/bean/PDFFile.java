package com.msj.android_project.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class PDFFile implements Parcelable {

    private String filePath;
    private String fileName;

    private boolean isSelected;

    public PDFFile() {
    }

    public PDFFile(Parcel in) {
        filePath = in.readString();
        fileName = in.readString();
        isSelected = in.readByte() != 0;
    }

    public static final Creator<PDFFile> CREATOR = new Creator<PDFFile>() {
        @Override
        public PDFFile createFromParcel(Parcel in) {
            PDFFile file = new PDFFile();
            file.setFilePath(in.readString());
            file.setFileName(in.readString());
            file.setSelected(in.readByte() != 0);
            return file;
        }

        @Override
        public PDFFile[] newArray(int size) {
            return new PDFFile[size];
        }
    };


    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(filePath);
        parcel.writeString(fileName);
        parcel.writeByte((byte) (isSelected ? 1 : 0));
    }
}
