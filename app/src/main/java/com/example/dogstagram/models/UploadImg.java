package com.example.dogstagram.models;

import com.google.gson.annotations.SerializedName;

import java.io.File;

public class UploadImg {
    @SerializedName("file")
    private File file;

    public UploadImg(File file) {
        this.file = file;
    }

    public File getFile() {
        return file;
    }
}
