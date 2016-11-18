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
import java.util.Random;

/**
 * Created by lx on 2016/11/16.
 */
public class npc4 extends EnemyFish{
    private static int currentCount = 0;	 //	对象当前的数量
    public static int sumCount = 4;	 	 	 //	对象总的数量

    private static List<Bitmap> npcList;

    private static int[] npcSwim = new int[] {
            R.drawable.n4_0, R.drawable.n4_1, R.drawable.n4_2, R.drawable.n4_3,
    };


    private static int imgNpc_w;
    private static int imgNpc_h;

    public npc4(Resources resources) {
        super(resources);
        score = 100;
        size = 100;
        npcList = new ArrayList<Bitmap>();
        initBitmap();
    }

    //初始化数据
    @Override
    public void initial(int arg0,float arg1,float arg2){
        isAlive = true;
        Random ran = new Random();
        speed = ran.nextInt(5) + 5 * arg0;
        directionL = ran.nextBoolean();
        if (directionL) {
            object_x =  - object_width;
        } else {
            object_x = screen_width;
        }
        object_y = ran.nextInt((int)(screen_height - object_height));
        currentCount++;
        if(currentCount >= sumCount){
            currentCount = 0;
        }
    }

    @Override
    public void initBitmap() {
        for(int i = 0; i < 4; i++) npcList.add(BitmapFactory.decodeResource(resources, npcSwim[i]));
        imgNpc_w = npcList.get(0).getWidth();
        imgNpc_h =  npcList.get(0).getHeight();

        object_width = size;			//获得每一帧位图的宽
        object_height = size * imgNpc_h / imgNpc_w ;		//获得每一帧位图的高
    }


    @Override
    public void drawSelf(Canvas canvas) {
        //判断敌鱼是否死亡状态
        if(isAlive){
            int index = currentFrame; // 获得当前帧相对于位图的Y坐标
            canvas.save();
            canvas.clipRect(object_x,object_y,object_x + object_width,object_y + object_height);
            if (!directionL) {
                canvas.drawBitmap(npcList.get(index),
                        new Rect(0, 0, imgNpc_w , imgNpc_h),
                        new Rect((int) object_x, (int) object_y, (int) (object_x + object_width), (int) (object_y + object_height)),
                        paint);
            } else {
                Matrix m = canvas.getMatrix();
                m.setScale(-1, 1);
                int width = imgNpc_w;
                int height = imgNpc_h;
                Bitmap img = Bitmap.createBitmap(npcList.get(index), 0, 0, width, height, m, true);
                canvas.drawBitmap(img,
                        new Rect(0, 0, imgNpc_w , imgNpc_h),
                        new Rect((int) object_x, (int) object_y, (int) (object_x + object_width), (int) (object_y + object_height)),
                        paint);
            }
            canvas.restore();
            currentFrame++;
            logic();
            if(currentFrame >= 4){
                currentFrame = 0;
            }
        }
    }

    @Override
    public void release() {
        for (Bitmap b : npcList) {
            if (!b.isRecycled())
                b.recycle();
        }
    }
}
