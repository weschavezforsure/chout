package com.wesleychavez.chout;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

/**
 * Created by wchavez on 5/23/17.
 */

public class MainThread extends Thread {

    private int mFPS = 60;
    private double mAverageFPS;
    private SurfaceHolder mSurfaceHolder;
    private GamePanel mGamePanel;
    private boolean mRunning;
    public static Canvas sCanvas;

    public MainThread (SurfaceHolder surfaceHolder, GamePanel gamePanel) {
        super();
        this.mSurfaceHolder = surfaceHolder;
        this.mGamePanel = gamePanel;
    }

    @Override
    public void run() {
        long startTime;
        long timeMillis;
        long waitTime;
        long totalTime = 0;
        int frameCount = 0;
        long targetTime = 1000/mFPS;

        while (mRunning) {
            startTime = System.nanoTime();
            sCanvas = null;
            try {
                sCanvas = this.mSurfaceHolder.lockCanvas();
                synchronized (mSurfaceHolder) {
                    this.mGamePanel.update();
                    this.mGamePanel.draw(sCanvas);
                }
            } catch (Exception e) {
            } finally {
                if (sCanvas != null) {
                    try {
                        mSurfaceHolder.unlockCanvasAndPost(sCanvas);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            timeMillis = (System.nanoTime() - startTime) / 1000000;
            waitTime = targetTime-timeMillis;
            try {
                this.sleep(waitTime);
            } catch (Exception e) {
            }
            totalTime += System.nanoTime()-startTime;
            frameCount++;
            if (frameCount == mFPS) {
                mAverageFPS = 1000/((totalTime/frameCount)/1000000);
                frameCount = 0;
                totalTime = 0;
                System.out.println(mAverageFPS);
            }
        }

    }
    public void setRunning (boolean b) {
        mRunning = b;
    }
}
