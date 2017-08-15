package com.wesleychavez.chout;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.Random;

/**
 * Created by wchavez on 5/23/17.
 */

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback, SensorEventListener {
    private SensorManager mSensorManager;
    private Sensor mSensor;
    private float [] mAcceleration = new float[3];

    public static final int WIDTH = 856;
    public static final int HEIGHT = 480;
    private int scaleFactorX,scaleFactorY;
    private MainThread mMainThread;
    private Bitmap mBackground;
    private Paint paint_red=new Paint();
    private Paint paint_cyan=new Paint();
    private Paint paint_black=new Paint();
    private Paint paint_text=new Paint();

    private Wanderer[] mEnemies;
    private int numEnemies;
    private int sizeEnemies;
    private Wanderer[] mPowerups;
    private int numPowerups;
    private int numPowerupsLeft;
    private boolean [] mPowerupsAlive;
    //private int powerupType;
    private int sizePowerups;
    private int powerupStepSize;
    private int enemyStepSize;
    private int enemySpeedDivider;
    private int enemySpeedCounter;
    private int numCollisions;

    private boolean lose = false;
    //private Wanderer[] mPowerdowns;
    //private int numPowerdowns;
    //private int sizePowerdowns;


    public GamePanel(Context context) {
        super(context);

        getHolder().addCallback(this);
        mMainThread = new MainThread(getHolder(),this);

        // Make GamePanel focusable so it can handle events
        setFocusable(true);

        mSensorManager = (SensorManager)context.getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_GAME);

        Typeface tf = Typeface.createFromAsset(context.getAssets(),"Champignon.ttf");
        paint_text.setTypeface(tf);
    }
    @Override
    public void surfaceChanged (SurfaceHolder holder, int format, int width, int height) {}

    @Override
    public void surfaceDestroyed (SurfaceHolder holder) {
        mSensorManager.unregisterListener(this);
        boolean retry = true;
        while (retry) {
            try {
                mMainThread.setRunning(false);
                mMainThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            retry = false;
        }
    }

    @Override
    public void surfaceCreated (SurfaceHolder holder) {
        scaleFactorX=getMeasuredWidth()/WIDTH;
        scaleFactorY=getMeasuredHeight()/HEIGHT;
        //System.out.println(WIDTH*scaleFactorX);
        //System.out.println(HEIGHT*scaleFactorY);
        mBackground = BitmapFactory.decodeResource(getResources(), R.drawable.white);
        paint_red.setColor(Color.RED);
        paint_cyan.setColor(Color.CYAN);
        paint_black.setColor(Color.BLACK);
        paint_text.setColor(Color.BLACK);
        paint_text.setTextSize(100f);
        enemySpeedDivider = 1;
        enemySpeedCounter = 0;
        numEnemies = 100;
        sizeEnemies = 16;
        mEnemies = new Wanderer[numEnemies];
        powerupStepSize = 2;
        enemyStepSize = 2;
        numPowerups = 10;
        numPowerupsLeft = numPowerups;
        sizePowerups = 16;
        mPowerups = new Wanderer[numPowerups];
        numCollisions = 0;


        //numPowerdowns = 8;
        //sizePowerdowns = 24;
        //mPowerdowns = new Wanderer[numPowerdowns];
        Random r = new Random();
        for (int i = 0; i < numEnemies; i++) {
            int randX = r.nextInt(WIDTH-sizeEnemies);
            int randY = r.nextInt(HEIGHT-sizeEnemies);
            mEnemies [i] = new Wanderer(randX, randY, sizeEnemies, enemyStepSize, WIDTH, HEIGHT);
        }
        mPowerupsAlive = new boolean[numPowerups];
        for (int i = 0; i < numPowerups; i++) {
            int randX = r.nextInt(WIDTH-sizePowerups);
            int randY = r.nextInt(HEIGHT-sizePowerups);
            mPowerups [i] = new Wanderer(randX, randY, sizePowerups, powerupStepSize, WIDTH, HEIGHT);
            mPowerupsAlive[i] = true;
        }
        //for (int i = 0; i < numPowerdowns; i++) {
        //    int randX = r.nextInt(WIDTH-sizePowerdowns);
        //    int randY = r.nextInt(HEIGHT-sizePowerdowns);
        //    mPowerdowns [i] = new Wanderer(randX, randY, sizePowerdowns, WIDTH, HEIGHT);
        //}
        // Start the game loop
        mMainThread.setRunning(true);
        //if (mMainThread.getState() == Thread.State.NEW) {
            mMainThread.start();
        //}

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    public void update() {
        if (enemySpeedCounter % enemySpeedDivider == 0) {
            for (int i = 0; i < numEnemies; i++) {
                boolean collision = mEnemies[i].update(mAcceleration);
                if (collision) {
                    numCollisions++;
                    //lose = true;
                    //paint_red.setColor(Color.BLACK);
                    if (mEnemies[i].getStepSize() <= 8) {
                        mEnemies[i].setStepSize(mEnemies[i].getStepSize() * 2);
                    }
                    mEnemies[i].setX(0);
                    mEnemies[i].setY(0);
                }
            }
        }
        enemySpeedCounter++;

        for (int i = 0; i < numPowerups; i++) {
            if (mPowerupsAlive[i] == true) {
                boolean collision = mPowerups[i].update(mAcceleration);
                if (collision) {
                    numPowerupsLeft--;
                    mPowerupsAlive[i] = false;
                    Random r = new Random();
                    int rand = r.nextInt(2);
                    if (rand==0) {
                        sizePowerups = sizePowerups + 8;
                        for (int j = 0; j < numPowerups; j++) {
                            mPowerups[j].setSize(sizePowerups);
                        }
                    }
                    else if (rand==1) {
                        if (enemySpeedDivider == 1) {
                            enemySpeedDivider=2;
                        }
                        else {
                            enemySpeedDivider = enemySpeedDivider+2;
                        }
                    }
                    //paint_red.setColor(Color.CYAN);
                }
            }

        }
        //for (int i = 0; i < numPowerdowns; i++) {
        //    boolean collision = mPowerdowns[i].update(mAcceleration);
        //    if (collision) {
        //        paint_red.setColor(Color.DKGRAY);
        //    }
        //}

    }

    @Override
    public void draw (Canvas canvas) {
        //final float scaleFactorX = getWidth()/WIDTH;
        //final float scaleFactorY = getHeight()/HEIGHT;
        //System.out.println(scaleFactorX);
        //System.out.println(scaleFactorY);
        if (canvas!=null) {
            final int savedState = canvas.save();
            canvas.scale(scaleFactorX,scaleFactorY);
            //long startnow;
            //long endnow;

            //startnow = android.os.SystemClock.uptimeMillis();
            //canvas.drawBitmap(mBackground,0,0,null);
            //endnow = android.os.SystemClock.uptimeMillis();
            //Log.d("MYTAG", "Execution time 1: " + (endnow - startnow) + " ms");
            //Paint paint = new Paint();
            //paint.setColor(Color.WHITE);
            //paint.setStyle(Paint.Style.FILL);

            //startnow = android.os.SystemClock.uptimeMillis();
            //canvas.drawPaint(paint);
            //endnow = android.os.SystemClock.uptimeMillis();
            //Log.d("MYTAG", "Execution time 2: " + (endnow - startnow) + " ms");
            canvas.drawBitmap(mBackground,0,0,null);
            for (int i = 0; i < numPowerups; i++) {
                if (mPowerupsAlive[i] == true) {
                    canvas.drawRoundRect(mPowerups[i].getX(),mPowerups[i].getY(),mPowerups[i].getX()+mPowerups[i].getSize(),mPowerups[i].getY()+mPowerups[i].getSize(),4,4,paint_cyan);
                }
            }
            for (int i = 0; i < numEnemies; i++) {
                canvas.drawRoundRect(mEnemies[i].getX(),mEnemies[i].getY(),mEnemies[i].getX()+mEnemies[i].getSize(),mEnemies[i].getY()+mEnemies[i].getSize(),4,4,paint_red);
            }
            paint_text.setTextSize(100f);
            canvas.drawText("chout",20,60,paint_text);
            canvas.drawText(String.valueOf(numCollisions),50,120,paint_text);
            if (numPowerupsLeft==0) {
                //Random r = new Random();
                //int rand = r.nextInt(3);
                //if (rand==0) {
                //    paint_text.setTextSize(200f);
                //    canvas.drawText("Wow",300,200,paint_text);
                //    canvas.drawText("you suck",300,300,paint_text);
                //}
                //else if (rand==1) {
                    paint_text.setTextSize(200f);
                    canvas.drawText("K",300,200,paint_text);
                //}
            }
            //for (int i = 0; i < numPowerdowns; i++) {
            //    canvas.drawRect(mPowerdowns[i].getX(),mPowerdowns[i].getY(),mPowerdowns[i].getX()+mPowerdowns[i].getSize(),mPowerdowns[i].getY()+mPowerdowns[i].getSize(),paint_black);
            //}
            canvas.restoreToCount(savedState);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        mAcceleration[0] = event.values[0];
        mAcceleration[1] = event.values[1];
        mAcceleration[2] = event.values[2];
        //System.out.println("x: " + mAcceleration[0]);
        //System.out.println("y: " + mAcceleration[1]);
        //System.out.println("z: " + mAcceleration[2]);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
