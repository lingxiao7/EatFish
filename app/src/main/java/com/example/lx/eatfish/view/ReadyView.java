package com.example.lx.eatfish.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import com.example.lx.eatfish.R;
import com.example.lx.eatfish.constant.ConstantUtil;
import com.example.lx.eatfish.object.FishHero;
import com.example.lx.eatfish.sounds.GameSoundPool;

/**
 * Created by lx on 2016/11/16.
 */
public class ReadyView extends BaseView{

    private float button_start_x;
    private float button_start_y;
    private float button_help_x;
    private float button_help_y;
    private float button_exit_x;
    private float button_exit_y;

    private float button_music_x;
    private float button_music_y;

    private float button_help2_x;
    private float button_help2_y;

    private float logo_x;
    private float logo_y;

    private boolean isBtChangeS;				// 按钮图片改变的标记
    private boolean isBtChangeH;
    private boolean isBtChangeE;

    private Bitmap button_start;
    private Bitmap button_startd;
    private Bitmap button_help;
    private Bitmap button_helpd;
    private Bitmap button_help2;
    private Bitmap button_exit;
    private Bitmap button_exitd;
    private Bitmap button_music;
    private Bitmap button_musicd;

    private Bitmap logo;
    private Bitmap bubble;
    private Bitmap background;				// 背景图片
    private FishHero mFishHero;

    public ReadyView(Context context, GameSoundPool sounds) {
        super(context,sounds);
        // TODO Auto-generated constructor stub
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
            if (x > button_start_x && x < button_start_x + button_start.getWidth()
                    && y > button_start_y && y < button_start_y + button_start.getHeight()) {
                sounds.playSound(7, 0);
                isBtChangeS = true;
                drawSelf();
                mainActivity.getHandler().sendEmptyMessage(ConstantUtil.TO_MAIN_VIEW);
            }
            //判断帮助按钮是否被按下
            else if ((x > button_help_x && x < button_help_x + button_help.getWidth()
                    && y > button_help_y && y < button_help_y + button_help.getHeight())
                    ||(x > button_help2_x && x < button_help2_x + button_help2.getWidth()
                    && y > button_help2_y && y < button_help2_y + button_help2.getHeight())) {
                //sounds.playSound(7, 0);
                isBtChangeH = true;
                drawSelf();
                mainActivity.getHandler().sendEmptyMessage(ConstantUtil.TO_HELP_VIEW);
            }
            //判断结束按钮是否被按下
            else if (x > button_exit_x && x < button_exit_x + button_exit.getWidth()
                    && y > button_exit_y && y < button_exit_y + button_exit.getHeight()) {
                sounds.playSound(8, 0);
                isBtChangeE = true;
                drawSelf();
                mainActivity.getHandler().sendEmptyMessage(ConstantUtil.END_GAME);
            }
            //判断音效开关是否被按下
            else if(x > button_music_x && x < button_music_x + button_music.getWidth()
                        && y > button_music_y && y < button_music_y + button_music.getHeight()) {
                //sounds.playSound(8, 0);
                drawSelf();
                if(mainActivity.enablePlaySound) mainActivity.enablePlaySound = false;
                else mainActivity.enablePlaySound = true;
            }

            return true;
        }
        //响应屏幕单点移动的消息
        else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            float x = event.getX();
            float y = event.getY();
            if (x > button_start_x && x < button_start_x + button_start.getWidth()
                    && y > button_start_y && y < button_start_y + button_start.getHeight()) {
                isBtChangeS = true;
            } else {
                isBtChangeS = false;
            }

            if ((x > button_help_x && x < button_help_x + button_help.getWidth()
                    && y > button_help_y && y < button_help_y + button_help.getHeight())
                    ||(x > button_help2_x && x < button_help2_x + button_help2.getWidth()
                    && y > button_help2_y && y < button_help2_y + button_help2.getHeight())) {
                isBtChangeH = true;
            } else {
                isBtChangeH = false;
            }

            if (x > button_exit_x && x < button_exit_x + button_exit.getWidth()
                    && y > button_exit_y && y < button_exit_y + button_exit.getHeight()){
                isBtChangeE = true;
            } else {
                isBtChangeE = false;
            }

            return true;
        }
        //响应手指离开屏幕的消息
        else if (event.getAction() == MotionEvent.ACTION_UP) {
            isBtChangeS = false;
            isBtChangeH = false;
            isBtChangeE = false;
            return true;
        }
        return false;
    }

    // 初始化图片资源方法
    @Override
    public void initBitmap() {
        // TODO Auto-generated method stub
        button_start = BitmapFactory.decodeResource(getResources(), R.drawable.menu_start_1);
        button_startd = BitmapFactory.decodeResource(getResources(), R.drawable.menu_start_2);
        button_help = BitmapFactory.decodeResource(getResources(), R.drawable.menu_help_1);
        button_helpd = BitmapFactory.decodeResource(getResources(), R.drawable.menu_help_2);
        button_help2 = BitmapFactory.decodeResource(getResources(), R.drawable.menu_help);
        button_exit = BitmapFactory.decodeResource(getResources(), R.drawable.menu_exit_1);
        button_exitd = BitmapFactory.decodeResource(getResources(), R.drawable.menu_exit_2);
        button_music = BitmapFactory.decodeResource(getResources(), R.drawable.menu_music1);
        button_musicd = BitmapFactory.decodeResource(getResources(), R.drawable.menu_music2);

        logo =  BitmapFactory.decodeResource(getResources(), R.drawable.menu_logo);
        background = BitmapFactory.decodeResource(getResources(), R.drawable.menu_bg);
        bubble =  BitmapFactory.decodeResource(getResources(), R.drawable.menu_qipao);

        scalex = screen_width / background.getWidth();
        scaley = screen_height / background.getHeight();
        logo_x =  screen_width / 2 - logo.getWidth() / 2;
        logo_y = screen_height / 4 - logo.getHeight() / 2;
        button_start_x = screen_width / 2 - button_start.getWidth() / 2;
        button_start_y = screen_height / 2 - button_start.getHeight() - 20;
        button_help_x = screen_width / 2 - button_help.getWidth() / 2;
        button_help_y = screen_height / 2 + 20;
        button_exit_x = screen_width / 2 - button_exit.getWidth() / 2;
        button_exit_y = screen_height / 4 * 3;
        button_help2_x = screen_width * 9 / 10 - button_help.getWidth() / 2;
        button_help2_y = 20;
        button_music_x = screen_width / 10 - button_music.getWidth() / 2;
        button_music_y = screen_height - 20 - button_music.getHeight();
    }

    // 释放图片资源的方法
    @Override
    public void release() {
        // TODO Auto-generated method stub
        if (! button_start.isRecycled()) button_start.recycle();
        if (! button_startd.isRecycled()) button_startd.recycle();
        if (! button_help.isRecycled()) button_help.recycle();
        if (! button_help2.isRecycled()) button_help2.recycle();
        if (! button_exitd.isRecycled()) button_exitd.recycle();
        if (! button_music.isRecycled()) button_music.recycle();
        if (! button_musicd.isRecycled()) button_musicd.recycle();
        if (! background.isRecycled()) background.recycle();
        if (! bubble.isRecycled()) bubble.recycle();
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

            canvas.drawBitmap(logo, logo_x, logo_y, paint);
            canvas.drawBitmap(button_help2, button_help2_x, button_help2_y, paint);

            //当手指滑过按钮时变换图片
            //开始按钮
            if (isBtChangeS) canvas.drawBitmap(button_startd, button_start_x, button_start_y, paint);
            else canvas.drawBitmap(button_start, button_start_x, button_start_y, paint);
            //帮助按钮
            if (isBtChangeH) canvas.drawBitmap(button_helpd, button_help_x, button_help_y, paint);
            else canvas.drawBitmap(button_help, button_help_x, button_help_y, paint);
            //退出按钮
            if (isBtChangeE) canvas.drawBitmap(button_exitd, button_exit_x, button_exit_y, paint);
            else canvas.drawBitmap(button_exit, button_exit_x, button_exit_y, paint);
            //音效按钮
            if(mainActivity.enablePlaySound) canvas.drawBitmap(button_music, button_music_x, button_music_y, paint);
            else canvas.drawBitmap(button_musicd, button_music_x, button_music_y, paint);

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