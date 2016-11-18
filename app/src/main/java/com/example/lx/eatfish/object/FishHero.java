package com.example.lx.eatfish.object;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;

import com.example.lx.eatfish.R;
import com.example.lx.eatfish.constant.ConstantUtil;
import com.example.lx.eatfish.factory.GameObjectFactory;
import com.example.lx.eatfish.interfaces.IMyFish;
import com.example.lx.eatfish.view.MainView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by lx on 2016/11/16.
 */
public class FishHero extends GameObject implements IMyFish{
    private float middle_x;			// 鱼的中心坐标
    private float middle_y;
    private long startTime;	 	 	// 开始的时间
    private long endTime;	 	 	// 结束的时间
    private boolean isHit;          // 被击晕
    private boolean isEat;          // 进食

    private static float fish_w;
    private static float fish_h;
    private static float fishHit_w;
    private static float fishHit_h;
    private static float fishEat_w;
    private static float fishEat_h;

    private boolean isDoubleSpeed;
    private boolean isStar;

    private List<Bitmap> mFishList; // 鱼游时的图片
    private List<Bitmap> mFishEatList;// 鱼吃鱼时的图片
    private List<Bitmap> mFishHitList;// 鱼击晕时的图片

    private MainView mainView;
    private GameObjectFactory factory;

    private static int[] fishSwim = new int[] {R.drawable.n0_0, R.drawable.n0_1,
            R.drawable.n0_2, R.drawable.n0_3, R.drawable.n0_4, R.drawable.n0_5,
            R.drawable.n0_6, R.drawable.n0_7, R.drawable.n0_8, R.drawable.n0_9,
            R.drawable.n0_10, R.drawable.n0_11, R.drawable.n0_12, R.drawable.n0_13
    };
    private static int[] fishEat = new int[] {
            R.drawable.n0e_0, R.drawable.n0e_1, R.drawable.n0e_2
    };
    private static int[] fishHit = new int[] {
            R.drawable.n0l_0, R.drawable.n0l_1, R.drawable.n0l_2
    };

    public FishHero(Resources resources) {
        super(resources);
        this.speed = 8;
        isHit = false;
        isEat = false;
        isStar = false;
        isDoubleSpeed = false;
        size = 50;
        factory = new GameObjectFactory();
        mFishList = new ArrayList<Bitmap>();
        mFishEatList = new ArrayList<Bitmap>();
        mFishHitList = new ArrayList<Bitmap>();
        initBitmap();
    }

    public void setMainView(MainView mainView) {
        this.mainView = mainView;
    }

    // 设置屏幕宽度和高度
    @Override
    public void setScreenWH(float screen_width, float screen_height) {
        super.setScreenWH(screen_width, screen_height);
        Random ran = new Random();
        object_x = screen_width/2 - object_width/2;
        object_y = ran.nextFloat();
        middle_x = object_x + object_width/2;
        middle_y = object_y + object_height/2;
    }

    @Override
    protected void initBitmap() {
        for (int i = 0; i < 14; i++) mFishList.add(BitmapFactory.decodeResource(resources, fishSwim[i]));
        for (int i = 0; i < 3; i++) {
            mFishEatList.add(BitmapFactory.decodeResource(resources, fishEat[i]));
            mFishHitList.add(BitmapFactory.decodeResource(resources, fishHit[i]));
        }
        fish_w = mFishList.get(0).getWidth();
        fish_h = mFishList.get(0).getHeight();
        fishEat_w = mFishEatList.get(0).getWidth();
        fishEat_h = mFishEatList.get(0).getHeight();
        fishHit_w = mFishHitList.get(0).getWidth();
        fishHit_h = mFishHitList.get(0).getHeight();


        object_width = size; // 获得每一帧位图的宽
        object_height = size * fish_h / fish_w; 	// 获得每一帧位图的高
    }

    @Override
    public void drawSelf(Canvas canvas) {
        if (isDoubleSpeed) {
            if (System.currentTimeMillis() - startTime > 2000) {
                speed /= 2;
                isDoubleSpeed = false;
            }
        }
        if(isEat) {
            if (currentFrame >= 3) {
                currentFrame = 0;
            }
            int index = (int) (currentFrame); // 获得当前帧相对于位图的X坐标
            canvas.save();
            canvas.clipRect(object_x, object_y, object_x + object_width, object_y + object_height);
            if (!directionL) {
                canvas.drawBitmap(mFishEatList.get(index),
                        new Rect(0, 0, (int)fishEat_w , (int)fishEat_h),
                        new Rect((int) object_x, (int) object_y, (int) (object_x + object_width), (int) (object_y + object_height)),
                        paint);
            } else {
                Matrix m = canvas.getMatrix();
                m.setScale(-1, 1);
                int width = (int)fishEat_w;
                int height = (int)fishEat_h;
                Bitmap img = Bitmap.createBitmap(mFishEatList.get(index), 0, 0, width, height, m, true);
                canvas.drawBitmap(img,
                        new Rect(0, 0, (int)fishEat_w , (int)fishEat_h),
                        new Rect((int) object_x, (int) object_y, (int) (object_x + object_width), (int) (object_y + object_height)),
                        paint);
            }
            canvas.restore();
            currentFrame++;
            if (currentFrame >= 3) {
                currentFrame = 0;
                isEat = false;
            }
        } else if (isHit) {
            if (currentFrame >= 3) {
                currentFrame = 0;
            }
            int index = (int) (currentFrame); // 获得当前帧相对于位图的X坐标
            canvas.save();
            canvas.clipRect(object_x, object_y, object_x + object_width, object_y + object_height);

            if (!directionL) {

                canvas.drawBitmap(mFishHitList.get(index),
                        new Rect(0, 0, (int)fishHit_w , (int)fishHit_h),
                        new Rect((int) object_x, (int) object_y, (int) (object_x + object_width), (int) (object_y + object_height)),
                        paint);
            } else {
                Matrix m = canvas.getMatrix();
                m.setScale(-1, 1);
                int width = (int)fishHit_w;
                int height = (int)fishHit_h;
                Bitmap img = Bitmap.createBitmap(mFishHitList.get(index), 0, 0, width, height, m, true);
                canvas.drawBitmap(img,
                        new Rect(0, 0, (int)fishHit_w , (int)fishHit_h),
                        new Rect((int) object_x, (int) object_y, (int) (object_x + object_width), (int) (object_y + object_height)),
                        paint);
            }
            canvas.restore();
            currentFrame++;
            if (currentFrame >= 3) {
                currentFrame = 0;
                isHit = false;
            }
        } else {
            int index = (int) (currentFrame); // 获得当前帧相对于位图的X坐标
            canvas.save();
            canvas.clipRect(object_x, object_y, object_x + object_width, object_y + object_height);
            if (!directionL) {
                canvas.drawBitmap(mFishList.get(index),
                        new Rect(0, 0, (int)fish_w , (int)fish_h),
                        new Rect((int) object_x, (int) object_y, (int) (object_x + object_width), (int) (object_y + object_height)),
                        paint);
            } else {
                Matrix m = canvas.getMatrix();
                m.setScale(-1, 1);
                int width = (int)fish_w;
                int height = (int)fish_h;
                Bitmap img = Bitmap.createBitmap(mFishList.get(index), 0, 0, width, height, m, true);
                canvas.drawBitmap(img,
                        new Rect(0, 0, (int)fish_w , (int)fish_h),
                        new Rect((int) object_x, (int) object_y, (int) (object_x + object_width), (int) (object_y + object_height)),
                        paint);
            }
            canvas.restore();
            currentFrame++;
            if (currentFrame >= 14) {
                currentFrame = 0;
            }
        }
    }

    @Override
    public void release() {
        for(Bitmap b:mFishList){
            if (!b.isRecycled())
                b.recycle();
        }
        for(Bitmap b:mFishEatList){
            if (!b.isRecycled())
                b.recycle();
        }
        for(Bitmap b:mFishHitList){
            if (!b.isRecycled())
                b.recycle();
        }
    }

    public void setFishSize(int arg) {
        size = 50 + arg / 30;
        object_width = size;
        object_height = object_width * fish_h / fish_w;
    }

    //getter和setter方法
    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public boolean isDoubleSpeed() {
        return isDoubleSpeed;
    }

    public void setDoubleSpeed(boolean doubleSpeed) {
        isDoubleSpeed = doubleSpeed;
    }

    public boolean isStar() {
        return isStar;
    }

    public void setStar(boolean star) {
        isStar = star;
    }

    public boolean isHit() {
        return isHit;
    }

    public void setHit(boolean hit) {
        isHit = hit;
    }

    public boolean isEat() {
        return isEat;
    }

    public void setEat(boolean eat) {
        isEat = eat;
    }

    @Override
    public float getMiddle_x() {
        return middle_x;
    }

    @Override
    public void setMiddle_x(float middle_x) {
        this.middle_x = middle_x;
        this.object_x = middle_x - object_width/2;
    }

    @Override
    public float getMiddle_y() {
        return middle_y;
    }

    @Override
    public void setMiddle_y(float middle_y) {
        this.middle_y = middle_y;
        this.object_y = middle_y - object_height/2;
    }
}
