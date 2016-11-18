package com.example.lx.eatfish.object;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;

import com.example.lx.eatfish.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 * Created by lx on 2016/11/16.
 */
public class npc8 extends GameObject{

    private static List<Bitmap> npcList;
    private static int[] npcImg = new int[]{
            R.drawable.n8_0, R.drawable.n8_1, R.drawable.n8_2, R.drawable.n8_3,
            R.drawable.n8_4, R.drawable.n8_5, R.drawable.n8_6, R.drawable.n8_7,
            R.drawable.n8_8, R.drawable.n8_9, R.drawable.n8_10, R.drawable.n8_11,
            R.drawable.n8_12, R.drawable.n8_13, R.drawable.n8_14, R.drawable.n8_15,
            R.drawable.n8_16, R.drawable.n8_17, R.drawable.n8_18, R.drawable.n8_19,
    };

    public npc8(Resources resources) {
        super(resources);
        npcList = new ArrayList<Bitmap>();
        initBitmap();
    }


    //初始化数据
    @Override
    public void initial(int arg0,float arg1,float arg2){
        isAlive = true;
        Random ran = new Random();
        speed = ran.nextInt(2) + 3 * arg0;
        object_x = screen_width;
        object_y = 60;
    }

    @Override
    protected void initBitmap() {
        for (int i = 0; i < 20; i ++) npcList.add(BitmapFactory.decodeResource(resources, npcImg[i]));
        object_width = npcList.get(0).getWidth();			//获得每一帧位图的宽
        object_height = npcList.get(0).getHeight() ;		//获得每一帧位图的高
    }

    @Override
    public void drawSelf(Canvas canvas) {
        if (isAlive) {
            int index = currentFrame; // 获得当前帧相对于位图的Y坐标
            canvas.save();
            canvas.clipRect(object_x,object_y,object_x + object_width,object_y + object_height);
            canvas.drawBitmap(npcList.get(index), object_x, object_y, paint);
            canvas.restore();
            currentFrame++;
            logic();
            if(currentFrame >= 20){
                currentFrame = 0;
            }
        }
    }

    @Override
    public void logic() {
        if (object_x + object_width  > 0 ) {
            object_x -= speed;
        } else {
            isAlive = false;
        }
    }

    @Override
    public void release() {
        for (Bitmap obj:npcList) {
            if (!obj.isRecycled())
                obj.recycle();
        }
    }
}
