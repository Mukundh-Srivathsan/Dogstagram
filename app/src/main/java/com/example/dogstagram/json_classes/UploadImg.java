package com.example.dogstagram.json_classes;

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
