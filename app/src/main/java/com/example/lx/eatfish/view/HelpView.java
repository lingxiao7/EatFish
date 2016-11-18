package com.example.lx.eatfish.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import com.example.lx.eatfish.R;
import com.example.lx.eatfish.constant.ConstantUtil;
import com.example.lx.eatfish.sounds.GameSoundPool;

/**
 * Created by lx on 2016/11/16.
 */
public class HelpView extends BaseView {
    private float back_x;
    private float back_y;
    private float helptext_x;
    private float helptext_y;

    private boolean isBtChange;

    private Bitmap background;
    private Bitmap back;
    private Bitmap backd;
    private Bitmap helptext;

    public HelpView(Context context, GameSoundPool sounds) {
        super(context, sounds);
        paint.setTextSize(40);
        thread = new Thread(this);
    }

    // 视图改变的方法
    @Override
    public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
        // TODO Auto-generated method stub
        super.surfaceChanged(arg0, arg1, arg2, arg3);
    }

    // 视图创建的方法
    @Override
    public void surfaceCreated(SurfaceHolder arg0) {
        // TODO Auto-generated method stub
        super.surfaceCreated(arg0);
        initBitmap();
        if(thread.isAlive()){
            thread.start();
        }
        else{
            thread = new Thread(this);
            thread.start();
        }
    }

    // 视图销毁的方法
    @Override
    public void surfaceDestroyed(SurfaceHolder arg0) {
        // TODO Auto-generated method stub
        super.surfaceDestroyed(arg0);
        release();
    }

    // 响应触屏事件的方法
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN
                && event.getPointerCount() == 1) {
            float x = event.getX();
            float y = event.getY();
            //判断开始按钮是否被按下
            if (x > back_x && x < back_x + back.getWidth()
                    && y > back_y && y < back_y + back.getHeight()) {
                isBtChange = true;
                drawSelf();
                mainActivity.getHandler().sendEmptyMessage(ConstantUtil.TO_READY_VIEW);
            }
            return true;
        }
        //响应屏幕单点移动的消息
        else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            float x = event.getX();
            float y = event.getY();
            if (x > back_x && x < back_x + back.getWidth()
                    && y > back_y && y < back_y + back.getHeight()) {
                isBtChange = true;
            } else {
                isBtChange = false;
            }
            return true;
        }
        //响应手指离开屏幕的消息
        else if (event.getAction() == MotionEvent.ACTION_UP) {
            isBtChange = false;
            return true;
        }
        return false;
    }

    // 初始化图片资源方法
    @Override
    public void initBitmap() {
        // TODO Auto-generated method stub_start_1);
        background = BitmapFactory.decodeResource(getResources(), R.drawable.menu_bg);
        back = BitmapFactory.decodeResource(getResources(), R.drawable.help_back1);
        backd = BitmapFactory.decodeResource(getResources(), R.drawable.help_back2);
        helptext = BitmapFactory.decodeResource(getResources(), R.drawable.help_text);

        scalex = screen_width / background.getWidth();
        scaley = screen_height / background.getHeight();
        helptext_x =  screen_width / 2 - helptext.getWidth() / 2;
        helptext_y = screen_height / 2 - helptext.getHeight() / 2 - 20;
        back_x =  screen_width / 2 - back.getWidth() / 2;
        back_y = screen_height / 4  * 3 + 20;
    }

    // 释放图片资源的方法
    @Override
    public void release() {
        // TODO Auto-generated method stub
        if (! back.isRecycled()) back.recycle();
        if (! backd.isRecycled()) backd.recycle();
        if (! helptext.isRecycled()) helptext.recycle();
        if (! background.isRecycled()) background.recycle();
    }

    // 绘图方法
    @Override
    public void drawSelf() {
        // TODO Auto-generated method stub
        try {
            canvas = sfh.lockCanvas();
            canvas.drawColor(Color.BLACK); 						// 绘制背景色
            canvas.save();
            canvas.scale(scalex, scaley, 0, 0);					// 计算背景图片与屏幕的比例
            canvas.drawBitmap(background, 0, 0, paint); 		// 绘制背景图
            canvas.restore();

            canvas.drawBitmap(helptext, helptext_x, helptext_y, paint);

            //当手指滑过按钮时变换图片
            if (isBtChange) canvas.drawBitmap(backd, back_x, back_y, paint);
            else canvas.drawBitmap(back, back_x, back_y, paint);

//            //小鱼的动画
//            canvas.save();
//            canvas.clipRect(fly_x, fly_y, fly_x + planefly.getWidth(), fly_y + fly_height);
//            canvas.drawBitmap(planefly, fly_x, fly_y - currentFrame * fly_height,paint);
//            currentFrame++;
//            if (currentFrame >= 3) {
//                currentFrame = 0;
//            }
//            canvas.restore();

        } catch (Exception err) {
            err.printStackTrace();
        } finally {
            if (canvas != null)
                sfh.unlockCanvasAndPost(canvas);
        }
    }

    // 线程运行的方法
    @Override
    public void run() {
        // TODO Auto-generated method stub
        while (threadFlag) {
            long startTime = System.currentTimeMillis();
            drawSelf();
            long endTime = System.currentTimeMillis();
            try {
                if (endTime - startTime < 400)
                    Thread.sleep(400 - (endTime - startTime));
            } catch (InterruptedException err) {
                err.printStackTrace();
            }
        }
    }
}
