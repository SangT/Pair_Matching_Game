package com.sharkto.pair_matching_game;

import android.view.View;
import android.widget.ImageButton;

public class MyButton {
    private ImageButton imageButton;
    private int imageNumber; // save the order to use in compareGame()
    private int imageDefault;
    private int imageId; // store images
    private boolean isClicked = false;
    private boolean isPaired = false;

    public MyButton(ImageButton imageButton, int imageDefault) {
        this.imageButton = imageButton;
        this.imageDefault = imageDefault;
        imageButton.setImageResource(imageDefault);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isClicked) {
                    setClicked(false);
                } else {
                    setClicked(true);
                }
                MainActivity.playGame();
            }
        });
    }

    public void setClicked(boolean clicked) {
        isClicked = clicked;
    }

    public void setImageButton(ImageButton imageButton) {
        this.imageButton = imageButton;
    }

    public void setImageDefault(int imageDefault) {
        this.imageDefault = imageDefault;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public void setImageNumber(int imageNumber) {
        this.imageNumber = imageNumber;
    }

    public void setPaired(boolean paired) {
        isPaired = paired;
    }

    public int getImageId() {
        return imageId;
    }

    public int getImageDefault() {
        return imageDefault;
    }

    public int getImageNumber() {
        return imageNumber;
    }

    public boolean isClicked() {
        return isClicked;
    }

    public boolean isPaired() {
        return isPaired;
    }

    public ImageButton getImageButton() {
        return imageButton;
    }
}
