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
import com.example.lx.eatfish.myfish.MainActivity;
import com.example.lx.eatfish.sounds.GameSoundPool;

/**
 * Created by lx on 2016/11/16.
 */public class EndView extends BaseView{
    private int score;

    private float button_start_x;
    private float button_start_y;
    private float button_exit_x;
    private float button_exit_y;
    private float scorebg_x;
    private float scorebg_y;
    private float score_x;
    private float score_y;
    private float score_w;

    private boolean isBtChangeS;				// 按钮图片改变的标记
    private boolean isBtChangeE;

    private Bitmap button_start;
    private Bitmap button_startd;
    private Bitmap button_exit;
    private Bitmap button_exitd;

    private Bitmap scorebg;
    private Bitmap scorebmp;
    private Bitmap background;				// 背景图片
    private Rect rect;						// 绘制文字的区域
    private MainActivity mainActivity;
    public EndView(Context context, GameSoundPool sounds) {
        super(context,sounds);
        // TODO Auto-generated constructor stub
        this.mainActivity = (MainActivity)context;
        rect = new Rect();
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
        if(event.getAction() == MotionEvent.ACTION_DOWN && event.getPointerCount() == 1){
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
            //判断结束按钮是否被按下
            else if (x > button_exit_x && x < button_exit_x + button_exit.getWidth()
                    && y > button_exit_y && y < button_exit_y + button_exit.getHeight()) {
                isBtChangeE = true;
                drawSelf();
                mainActivity.getHandler().sendEmptyMessage(ConstantUtil.END_GAME);
            }
            return true;
        }
        else if(event.getAction() == MotionEvent.ACTION_MOVE){
            float x = event.getX();
            float y = event.getY();
            if (x > button_start_x && x < button_start_x + button_start.getWidth()
                    && y > button_start_y && y < button_start_y + button_start.getHeight()) {
                isBtChangeS = true;
            } else {
                isBtChangeS = false;
            }

            if (x > button_exit_x && x < button_exit_x + button_exit.getWidth()
                    && y > button_exit_y && y < button_exit_y + button_exit.getHeight()){
                isBtChangeE = true;
            } else {
                isBtChangeE = false;
            }
            return true;
        }
        else if(event.getAction() == MotionEvent.ACTION_UP){
            isBtChangeS = false;
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
        button_exit = BitmapFactory.decodeResource(getResources(), R.drawable.menu_exit_1);
        button_exitd = BitmapFactory.decodeResource(getResources(), R.drawable.menu_exit_2);

        scorebg = BitmapFactory.decodeResource(getResources(), R.drawable.game_menu);
        scorebmp = BitmapFactory.decodeResource(getResources(), R.drawable.game_zi1);
        background = BitmapFactory.decodeResource(getResources(), R.drawable.menu_bg);

        scalex = screen_width / background.getWidth();
        scaley = screen_height / background.getHeight();
        button_start_x = screen_width / 2 - button_start.getWidth() / 2;
        button_start_y = screen_height / 2;
        button_exit_x = screen_width / 2 - button_exit.getWidth() / 2;
        button_exit_y = screen_height / 3 * 2;

        scorebg_x = screen_width / 2 - scorebg.getWidth() / 2;
        scorebg_y = screen_height / 3 - scorebg.getHeight() / 2;
        score_x = scorebg_x + scorebg.getWidth() - 20;
        score_y = scorebg_y;
        score_w = scorebmp.getWidth() / 10;
    }
    // 释放图片资源的方法
    @Override
    public void release() {
        // TODO Auto-generated method stub

        if (! button_start.isRecycled()) button_start.recycle();
        if (! button_exit.isRecycled()) button_exit.recycle();
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
            //当手指滑过按钮时变换图片
            //开始按钮
            if (isBtChangeS) canvas.drawBitmap(button_startd, button_start_x, button_start_y, paint);
            else canvas.drawBitmap(button_start, button_start_x, button_start_y, paint);
            //退出按钮
            if (isBtChangeE) canvas.drawBitmap(button_exitd, button_exit_x, button_exit_y, paint);
            else canvas.drawBitmap(button_exit, button_exit_x, button_exit_y, paint);

            canvas.drawBitmap(scorebg, scorebg_x, scorebg_y, paint);


            paint.setTextSize(25);
            paint.setColor(Color.rgb(235, 161, 1));
            canvas.drawText(String.valueOf(score), 10 + scorebg_x + 100, scorebg_y + 25, paint);

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
    public void setScore(int score) {
        this.score = score;
    }
}
