package com.sharkto.pair_matching_game;

public class ImagePair {
    private int imageId; // represent images
    private int pairNumber; // address of images

    public ImagePair(int imageId, int pairNumber) {
        this.imageId = imageId;
        this.pairNumber = pairNumber;
    }

    public int getImageId() {
        return imageId;
    }

    public int getPairNumber() {
        return pairNumber;
    }
}
