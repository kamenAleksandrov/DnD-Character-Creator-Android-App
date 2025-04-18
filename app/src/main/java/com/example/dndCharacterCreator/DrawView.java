package com.example.dndCharacterCreator;

import static com.example.dndCharacterCreator.MainActivity.characterNameToPass;
import static com.example.dndCharacterCreator.MainActivity.imagesFolderToPass;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.io.File;
import java.util.ArrayList;

public class DrawView extends View {

    private static final float TOUCH_TOLERANCE = 4;
    private float mX, mY;
    private Path mPath;

    // the Paint class encapsulates the color
    // and style information about
    // how to draw the geometries,text and bitmaps
    private Paint mPaint;

    // ArrayList to store all the strokes
    // drawn by the user on the Canvas
    private ArrayList<Stroke> paths = new ArrayList<>();
    private int currentColor;
    private int strokeWidth;
    private Bitmap mBitmap;
    private Canvas mCanvas;
    private Paint mBitmapPaint = new Paint(Paint.DITHER_FLAG);

    // Constructors to initialise all the attributes

    public DrawView(Context context) {
        this(context, null);
    }

    public DrawView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();

        // the below methods smoothens
        // the drawings of the user
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(Color.parseColor("#421A1A"));
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);

        // 0xff=255 in decimal
        mPaint.setAlpha(0xff);

        mBitmapPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    }

    //public DrawView(Context context, AttributeSet attributeSet, int defstyle){}

    // this method instantiate the bitmap and object
    public void init(int height, int width) {

        ContextWrapper cw = new ContextWrapper(getContext());
        File directory = cw.getDir(imagesFolderToPass, Context.MODE_PRIVATE);
        if (directory.exists()){
            File mypath = new File(directory, characterNameToPass + ".png");
            if (mypath.exists()){
                String filePath = mypath.getPath();
                Bitmap tempBitmap = BitmapFactory.decodeFile(filePath);
                mBitmap = tempBitmap.copy(Bitmap.Config.ARGB_8888, true);
            }
            else {
                mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            }
        }else {
            mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        }
        mCanvas = new Canvas(mBitmap);

        // set an initial color of the brush
        currentColor = Color.parseColor("#421A1A");

        // set an initial brush size
        strokeWidth = 10;
    }

    // sets the current color of stroke
    public void setColor(int color) {
        currentColor = color;
    }

    // sets the stroke width
    public void setStrokeWidth(int width) {
        strokeWidth = width;
    }

    public void undo() {
        // check whether the List is empty or not
        // if empty, the remove method will return an error
        /*
        if (paths.size() != 0) {
            paths.remove(paths.size() - 1);
            invalidate();
        }
         */
        //s toq focus undo stava eraser
        setColor(Color.parseColor("#BFB081"));
        strokeWidth = 15;
    }

    public void brush() {

        setColor(Color.parseColor("#421A1A"));
        strokeWidth = 10;

    }

    public void clearAll() {

        if (paths.size() != 0) {
            paths.removeAll(paths);
            invalidate();
        }
        int backgroundColor = Color.parseColor("#BFB081");
        mCanvas.drawColor(backgroundColor);
        invalidate();
    }

    // this methods returns the current bitmap
    public Bitmap save() {
        return mBitmap;
    }

    // this is the main method where
    // the actual drawing takes place
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // save the current state of the canvas before,
        // to draw the background of the canvas
        canvas.save();

        //tiq dvete nasirat zarejdaneto na poslednoto izobrajenie
        //moje bi globalni promenlivi s proverki dali ima bitmap i ako nqma, togava da se puskat tiq
        // DEFAULT color of the canvas
        ContextWrapper cw = new ContextWrapper(getContext());
        File directory = cw.getDir(imagesFolderToPass, Context.MODE_PRIVATE);
        if (directory.exists()){
            File mypath = new File(directory, characterNameToPass + ".png");
            if (mypath.exists()){
                int backgroundColor = Color.TRANSPARENT;
                mCanvas.drawColor(backgroundColor);
            } else {
                int backgroundColor = Color.parseColor("#BFB081");
                mCanvas.drawColor(backgroundColor);
            }
        }else {
            int backgroundColor = Color.parseColor("#BFB081");
            mCanvas.drawColor(backgroundColor);
        }
        // now, we iterate over the list of paths
        // and draw each path on the canvas
        for (Stroke fp : paths) {
            mPaint.setColor(fp.color);
            mPaint.setStrokeWidth(fp.strokeWidth);
            mCanvas.drawPath(fp.path, mPaint);
        }
        canvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);
        canvas.restore();
    }

    // the below methods manages the touch
    // response of the user on the screen

    // firstly, we create a new Stroke
    // and add it to the paths list
    private void touchStart(float x, float y) {
        mPath = new Path();
        Stroke fp = new Stroke(currentColor, strokeWidth, mPath);
        paths.add(fp);

        // finally remove any curve
        // or line from the path
        mPath.reset();

        // this methods sets the starting
        // point of the line being drawn
        mPath.moveTo(x, y);

        // we save the current
        // coordinates of the finger
        mX = x;
        mY = y;
    }

    // in this method we check
    // if the move of finger on the
    // screen is greater than the
    // Tolerance we have previously defined,
    // then we call the quadTo() method which
    // actually smooths the turns we create,
    // by calculating the mean position between
    // the previous position and current position
    private void touchMove(float x, float y) {
        float dx = Math.abs(x - mX);
        float dy = Math.abs(y - mY);

        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
            mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
            mX = x;
            mY = y;
        }
    }

    // at the end, we call the lineTo method
    // which simply draws the line until
    // the end position
    private void touchUp() {
        mPath.lineTo(mX, mY);
    }

    // the onTouchEvent() method provides us with
    // the information about the type of motion
    // which has been taken place, and according
    // to that we call our desired methods
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touchStart(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                touchMove(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                touchUp();
                invalidate();
                break;
        }
        return true;
    }
}
