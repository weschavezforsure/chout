package com.wesleychavez.chout;

import android.graphics.Rect;

import java.util.Random;

/**
 * Created by wchavez on 5/23/17.
 */

public class Wanderer {
    private float x;
    private int y;
    private int size;
    private int maxWidth;
    private int maxHeight;
    private int stepSize;
    private int diagStepSize;
    private boolean collision;
    private Random mRand = new Random();
    private float minAcceleration = 1.5f;

    public Wanderer(int x, int y, int size, int stepSize, int maxWidth, int maxHeight) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.stepSize = stepSize;
        this.diagStepSize = Math.round((float)stepSize/1.414f);
        this.maxWidth = maxWidth;
        this.maxHeight = maxHeight;
    }
    public void setX(int x)
    {
        this.x = x;
    }
    public void setY(int y)
    {
        this.y = y;
    }
    public void setSize(int size)
    {
        this.size = size;
        if (y+size>maxHeight) {
            y=maxHeight-size;
        }
        if (x+size>maxWidth) {
            x=maxWidth-size;
        }
    }
    public void setStepSize(int stepSize) {
        this.stepSize = stepSize;
        this.diagStepSize = Math.round((float)stepSize/1.414f);
    }
    public float getX()
    {
        return x;
    }
    public float getY()
    {
        return y;
    }
    public int getSize()
    {
        return size;
    }
    public int getStepSize() {
        return stepSize;
    }
    public boolean update (float [] acceleration) {
        int rand;
        if ((acceleration[0] > -minAcceleration) && (acceleration[0] < minAcceleration)){
            // Flat
            if ((acceleration[1] > -minAcceleration) && (acceleration[1] < minAcceleration)) {
                rand = mRand.nextInt(8);
                if (rand == 0) {
                    y=y-stepSize;
                    if (y < 0) {
                        y=0;
                    }
                }
                else if (rand == 1) {
                    y=y+stepSize;
                    if (y+size>maxHeight) {
                        y=maxHeight-size;
                    }
                }
                else if (rand == 2) {
                    x=x-stepSize;
                    if (x<0) {
                        x=0;
                    }
                }
                else if (rand == 3) {
                    x=x+stepSize;
                    if (x+size>maxWidth) {
                        x=maxWidth-size;
                    }
                }
                else if (rand == 4) {
                    x=x+diagStepSize;
                    if (x+size>maxWidth) {
                        x=maxWidth-size;
                    }
                    y=y+diagStepSize;
                    if (y+size>maxHeight) {
                        y=maxHeight-size;
                    }
                }
                else if (rand == 5) {
                    x=x+diagStepSize;
                    if (x+size>maxWidth) {
                        x=maxWidth-size;
                    }
                    y=y-diagStepSize;
                    if (y < 0) {
                        y=0;
                    }
                }
                else if (rand == 6) {
                    x=x-diagStepSize;
                    if (x < 0) {
                        x=0;
                    }
                    y=y-diagStepSize;
                    if (y < 0) {
                        y=0;
                    }
                }
                else if (rand == 7) {
                    x=x-diagStepSize;
                    if (x < 0) {
                        x=0;
                    }
                    y=y+diagStepSize;
                    if (y+size>maxHeight) {
                        y=maxHeight-size;
                    }
                }

            }
            // Left
            else if (acceleration[1] <= -minAcceleration) {
                rand = mRand.nextInt(11);
                if (0<=rand && rand<=1) {
                    x=x-stepSize;
                    if (x<0) {
                        x=0;
                    }
                }
                else if (2<=rand && rand <=3) {
                    x=x-diagStepSize;
                    if (x < 0) {
                        x=0;
                    }
                    y=y+diagStepSize;
                    if (y+size>maxHeight) {
                        y=maxHeight-size;
                    }
                }
                else if (4<=rand && rand <=5) {
                    x=x-diagStepSize;
                    if (x < 0) {
                        x=0;
                    }
                    y=y-diagStepSize;
                    if (y < 0) {
                        y=0;
                    }
                }
                else if (rand == 6) {
                    y=y+stepSize;
                    if (y+size>maxHeight) {
                        y=maxHeight-size;
                    }
                }
                else if (rand == 7) {
                    y=y-stepSize;
                    if (y < 0) {
                        y=0;
                    }
                }
                else if (rand == 8) {
                    x=x+stepSize;
                    if (x+size>maxWidth) {
                        x=maxWidth-size;
                    }
                }
                else if (rand==9) {
                    x=x+diagStepSize;
                    if (x+size>maxWidth) {
                        x=maxWidth-size;
                    }
                    y=y+diagStepSize;
                    if (y+size>maxHeight) {
                        y=maxHeight-size;
                    }
                }
                else if (rand==10) {
                    x=x+diagStepSize;
                    if (x+size>maxWidth) {
                        x=maxWidth-size;
                    }
                    y=y-diagStepSize;
                    if (y < 0) {
                        y=0;
                    }
                }
            }
            // Right
            else {
                rand = mRand.nextInt(11);
                if (0<=rand && rand<=1) {
                    x=x+stepSize;
                    if (x+size>maxWidth) {
                        x=maxWidth-size;
                    }
                }
                else if (2<=rand && rand<=3) {
                    x=x+diagStepSize;
                    if (x+size>maxWidth) {
                        x=maxWidth-size;
                    }
                    y=y+diagStepSize;
                    if (y+size>maxHeight) {
                        y=maxHeight-size;
                    }
                }
                else if (4<=rand && rand<=5) {
                    x=x+diagStepSize;
                    if (x+size>maxWidth) {
                        x=maxWidth-size;
                    }
                    y=y-diagStepSize;
                    if (y < 0) {
                        y=0;
                    }
                }
                else if (rand == 6) {
                    y=y+stepSize;
                    if (y+size>maxHeight) {
                        y=maxHeight-size;
                    }
                }
                else if (rand == 7) {
                    y=y-stepSize;
                    if (y < 0) {
                        y=0;
                    }
                }
                else if (rand == 8) {
                    x=x-stepSize;
                    if (x<0) {
                        x=0;
                    }
                }
                else if (rand==9) {
                    x=x-diagStepSize;
                    if (x<0) {
                        x=0;
                    }
                    y=y-diagStepSize;
                    if (y < 0) {
                        y=0;
                    }
                }
                else if (rand==10) {
                    x=x-diagStepSize;
                    if (x<0) {
                        x=0;
                    }
                    y=y+diagStepSize;
                    if (y+size>maxHeight) {
                        y=maxHeight-size;
                    }
                }
            }
        }
        else if (acceleration[0] <= -minAcceleration) {
            // Up
            if ((acceleration[1] > -minAcceleration) && (acceleration[1] < minAcceleration)) {
                rand = mRand.nextInt(11);
                if (0<=rand && rand<=1) {
                    y=y-stepSize;
                    if (y < 0) {
                        y=0;
                    }
                }
                else if (2<=rand && rand<=3) {
                    y=y-diagStepSize;
                    if (y < 0) {
                        y=0;
                    }
                    x=x+diagStepSize;
                    if (x+size>maxWidth) {
                        x=maxWidth-size;
                    }
                }
                else if (4<=rand && rand<=5) {
                    y=y-diagStepSize;
                    if (y < 0) {
                        y=0;
                    }
                    x=x-diagStepSize;
                    if (x<0) {
                        x=0;
                    }
                }
                else if (rand == 6) {
                    x=x+stepSize;
                    if (x+size>maxWidth) {
                        x=maxWidth-size;
                    }
                }
                else if (rand == 7) {
                    y=y+stepSize;
                    if (y+size>maxHeight) {
                        y=maxHeight-size;
                    }
                }
                else if (rand == 8) {
                    x=x-stepSize;
                    if (x<0) {
                        x=0;
                    }
                }
                else if (rand==9) {
                    y=y+diagStepSize;
                    if (y+size>maxHeight) {
                        y=maxHeight-size;
                    }
                    x=x+diagStepSize;
                    if (x+size>maxWidth) {
                        x=maxWidth-size;
                    }
                }
                else if (rand==10) {
                    y=y+diagStepSize;
                    if (y+size>maxHeight) {
                        y=maxHeight-size;
                    }
                    x=x-diagStepSize;
                    if (x<0) {
                        x=0;
                    }
                }
            }
            // Up left
            else if (acceleration[1] <= -minAcceleration) {
                rand = mRand.nextInt(11);
                if (0<=rand && rand<=1) {
                    y=y-diagStepSize;
                    if (y < 0) {
                        y=0;
                    }
                    x=x-diagStepSize;
                    if (x<0) {
                        x=0;
                    }
                }
                else if (2<=rand && rand<=3) {
                    x=x-stepSize;
                    if (x<0) {
                        x=0;
                    }
                }
                else if (4<=rand && rand<=5) {
                    y=y-stepSize;
                    if (y < 0) {
                        y=0;
                    }
                }
                else if (rand == 6) {
                    y=y+stepSize;
                    if (y+size>maxHeight) {
                        y=maxHeight-size;
                    }
                }
                else if (rand == 7) {
                    x=x+stepSize;
                    if (x+size>maxWidth) {
                        x=maxWidth-size;
                    }
                }
                else if (rand==8) {
                    y=y-diagStepSize;
                    if (y < 0) {
                        y=0;
                    }
                    x=x+diagStepSize;
                    if (x+size>maxWidth) {
                        x=maxWidth-size;
                    }
                }
                else if (rand==9) {
                    y=y+diagStepSize;
                    if (y+size>maxHeight) {
                        y=maxHeight-size;
                    }
                    x=x+diagStepSize;
                    if (x+size>maxWidth) {
                        x=maxWidth-size;
                    }
                }
                else if (rand==10) {
                    y=y+diagStepSize;
                    if (y+size>maxHeight) {
                        y=maxHeight-size;
                    }
                    x=x-diagStepSize;
                    if (x<0) {
                        x=0;
                    }
                }
            }
            // Up right
            else {
                rand = mRand.nextInt(11);
                if (0<=rand && rand<=1) {
                    y=y-diagStepSize;
                    if (y < 0) {
                        y=0;
                    }
                    x=x+diagStepSize;
                    if (x+size>maxWidth) {
                        x=maxWidth-size;
                    }
                }
                else if (2<=rand && rand<=3) {
                    x=x+stepSize;
                    if (x+size>maxWidth) {
                        x=maxWidth-size;
                    }
                }
                else if (4<=rand && rand<=5) {
                    y=y-stepSize;
                    if (y < 0) {
                        y=0;
                    }
                }
                else if (rand == 6) {
                    y=y+stepSize;
                    if (y+size>maxHeight) {
                        y=maxHeight-size;
                    }
                }
                else if (rand == 7) {
                    x=x-stepSize;
                    if (x<0) {
                        x=0;
                    }
                }
                else if (rand == 8) {
                    y=y+diagStepSize;
                    if (y+size>maxHeight) {
                        y=maxHeight-size;
                    }
                    x=x+diagStepSize;
                    if (x+size>maxWidth) {
                        x=maxWidth-size;
                    }
                }
                else if (rand == 9) {
                    y=y+diagStepSize;
                    if (y+size>maxHeight) {
                        y=maxHeight-size;
                    }
                    x=x-diagStepSize;
                    if (x<0) {
                        x=0;
                    }
                }
                else if (rand == 10) {
                    y=y-diagStepSize;
                    if (y < 0) {
                        y=0;
                    }
                    x=x-diagStepSize;
                    if (x<0) {
                        x=0;
                    }
                }
            }
        }
        else {
            // Down
            if ((acceleration[1] > -minAcceleration) && (acceleration[1] < minAcceleration)) {
                rand = mRand.nextInt(11);
                if (0<=rand && rand<=1) {
                    y=y+stepSize;
                    if (y+size>maxHeight) {
                        y=maxHeight-size;
                    }
                }
                if (2<=rand && rand<=3) {
                    y=y+diagStepSize;
                    if (y+size>maxHeight) {
                        y=maxHeight-size;
                    }
                    x=x+diagStepSize;
                    if (x+size>maxWidth) {
                        x=maxWidth-size;
                    }
                }
                if (4<=rand && rand<=5) {
                    y=y+diagStepSize;
                    if (y+size>maxHeight) {
                        y=maxHeight-size;
                    }
                    x=x-diagStepSize;
                    if (x<0) {
                        x=0;
                    }
                }
                else if (rand==6) {
                    x=x+stepSize;
                    if (x+size>maxWidth) {
                        x=maxWidth-size;
                    }
                }
                else if (rand == 7) {
                    y=y-stepSize;
                    if (y < 0) {
                        y=0;
                    }
                }
                else if (rand == 8) {
                    x=x-stepSize;
                    if (x<0) {
                        x=0;
                    }
                }
                else if (rand==9) {
                    y=y-diagStepSize;
                    if (y < 0) {
                        y=0;
                    }
                    x=x+diagStepSize;
                    if (x+size>maxWidth) {
                        x=maxWidth-size;
                    }
                }
                else if (rand==10) {
                    y=y-diagStepSize;
                    if (y < 0) {
                        y=0;
                    }
                    x=x-diagStepSize;
                    if (x<0) {
                        x=0;
                    }
                }
            }
            // Down left
            else if (acceleration[1] <= -minAcceleration) {
                rand = mRand.nextInt(11);
                if (0<=rand && rand<=1) {
                    x=x-stepSize;
                    if (x<0) {
                        x=0;
                    }
                }
                else if (2<=rand && rand<=3) {
                    y=y+stepSize;
                    if (y+size>maxHeight) {
                        y=maxHeight-size;
                    }
                }
                else if (4<=rand && rand<=5) {
                    y=y+diagStepSize;
                    if (y+size>maxHeight) {
                        y=maxHeight-size;
                    }
                    x=x-diagStepSize;
                    if (x<0) {
                        x=0;
                    }
                }
                else if (rand == 6) {
                    x=x+stepSize;
                    if (x+size>maxWidth) {
                        x=maxWidth-size;
                    }
                }
                else if (rand == 7) {
                    y=y-stepSize;
                    if (y < 0) {
                        y=0;
                    }
                }
                else if (rand==8) {
                    y=y+diagStepSize;
                    if (y+size>maxHeight) {
                        y=maxHeight-size;
                    }
                    x=x+diagStepSize;
                    if (x+size>maxWidth) {
                        x=maxWidth-size;
                    }
                }
                else if (rand==9) {
                    y=y-diagStepSize;
                    if (y < 0) {
                        y=0;
                    }
                    x=x+diagStepSize;
                    if (x+size>maxWidth) {
                        x=maxWidth-size;
                    }
                }
                else if (rand==10) {
                    y=y-diagStepSize;
                    if (y < 0) {
                        y=0;
                    }
                    x=x-diagStepSize;
                    if (x<0) {
                        x=0;
                    }
                }
            }
            // Down right
            else {
                rand = mRand.nextInt(11);
                if (0<=rand && rand<=1) {
                    x=x+stepSize;
                    if (x+size>maxWidth) {
                        x=maxWidth-size;
                    }
                }
                else if (2<=rand && rand<=3) {
                    y=y+stepSize;
                    if (y+size>maxHeight) {
                        y=maxHeight-size;
                    }
                }
                else if (4<=rand && rand<=5) {
                    y=y+diagStepSize;
                    if (y+size>maxHeight) {
                        y=maxHeight-size;
                    }
                    x=x+diagStepSize;
                    if (x+size>maxWidth) {
                        x=maxWidth-size;
                    }
                }
                else if (rand == 6) {
                    x=x-stepSize;
                    if (x<0) {
                        x=0;
                    }
                }
                else if (rand == 7) {
                    y=y-stepSize;
                    if (y < 0) {
                        y=0;
                    }
                }
                else if (rand==8) {
                    y=y-diagStepSize;
                    if (y < 0) {
                        y=0;
                    }
                    x=x+diagStepSize;
                    if (x+size>maxWidth) {
                        x=maxWidth-size;
                    }
                }
                else if (rand==9) {
                    y=y-diagStepSize;
                    if (y < 0) {
                        y=0;
                    }
                    x=x-diagStepSize;
                    if (x<0) {
                        x=0;
                    }
                }
                else if (rand==10) {
                    y=y+diagStepSize;
                    if (y+size>maxHeight) {
                        y=maxHeight-size;
                    }
                    x=x-diagStepSize;
                    if (x<0) {
                        x=0;
                    }
                }
            }
        }
        if (232<=y+size-1 && 247>=y && 419<=x+size-1 && 434>=x) {
            collision = true;
        }
        else {
            collision = false;
        }
        return collision;
    }
}