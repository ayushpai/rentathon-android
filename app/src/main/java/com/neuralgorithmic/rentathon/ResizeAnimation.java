package com.neuralgorithmic.rentathon;

import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

public class ResizeAnimation extends Animation {
    final int targetHeight;
    View view;
    int startHeight;
    boolean open;

    public ResizeAnimation(View view, int targetHeight, int startHeight, boolean open) {
        this.view = view;
        this.targetHeight = targetHeight;
        this.startHeight = startHeight;
        this.open = open;
    }

    @Override
    public void applyTransformation(float interpolatedTime, Transformation t) {
        int newHeight;
        if(open){
            newHeight = (int) (startHeight + targetHeight * interpolatedTime);
        }
        else{
            newHeight = (int) (startHeight+(targetHeight - startHeight) * interpolatedTime);
        }
        //to support decent animation, change new heigt as Nico S. recommended in comments
        //int newHeight = (int) (startHeight+(targetHeight - startHeight) * interpolatedTime);
        view.getLayoutParams().height = newHeight;
        view.requestLayout();
    }

    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
    }

    @Override
    public boolean willChangeBounds() {
        return true;
    }


}