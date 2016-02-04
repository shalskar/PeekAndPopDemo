package com.shalskar.peekandpopdemo;

import java.util.List;

/**
 * Created by Vincent on 22/01/2016.
 */
public class DemoObject {

    private List<String> imageUrls;
    private int currentImageIndex = 0;
    private String text;

    public DemoObject(List<String> imageUrl, String text) {
        this.imageUrls = imageUrl;
        this.text = text;
    }

    public String getCurrentImageUrl(){
        return imageUrls.get(currentImageIndex);
    }

    public void nextImage(){
        if(currentImageIndex < imageUrls.size() - 1){
            currentImageIndex ++;
        } else {
            currentImageIndex = 0;
        }
    }

    public void previousImage(){
        if(currentImageIndex > 0){
            currentImageIndex --;
        } else {
            currentImageIndex = imageUrls.size() - 1;
        }
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
