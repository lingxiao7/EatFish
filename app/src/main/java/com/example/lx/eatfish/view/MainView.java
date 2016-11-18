package com.example.lx.eatfish.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Message;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import com.example.lx.eatfish.R;
import com.example.lx.eatfish.constant.ConstantUtil;
import com.example.lx.eatfish.factory.GameObjectFactory;
import com.example.lx.eatfish.object.ClearAllGoods;
import com.example.lx.eatfish.object.DoubleGoods;
import com.example.lx.eatfish.object.EnemyFish;
import com.example.lx.eatfish.object.FishHero;
import com.example.lx.eatfish.object.GameObject;
import com.example.lx.eatfish.object.StarGoods;
import com.example.lx.eatfish.object.npc1;
import com.example.lx.eatfish.object.npc2;
import com.example.lx.eatfish.object.npc3;
import com.example.lx.eatfish.object.npc4;
import com.example.lx.eatfish.object.npc5;
import com.example.lx.eatfish.object.npc6;
import com.example.lx.eatfish.object.npc7;
import com.example.lx.eatfish.object.npc8;
import com.example.lx.eatfish.sounds.GameSoundPool;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by lx on 2016/11/16.
 */
public class MainView extends BaseView{
    private int missileCount; 		//
    private int clearAllScors;
    private int doubleScore;
    private int starScore;
    private int npc1Score;
    private int npc2Score;
    private int npc3Score;
    private int npc4Score;
    private int npc5Score;
    private int npc6Score;
    private int npc8Score;
    private int npc7Score;
    private int sumScore;			//
    private int speedTime;			//
    private int starCount = 0;


    private float play_bt_x;
    private float play_bt_y;
    private float missile_bt_y;

    private boolean isPlay;			//
    private boolean isTouchFish;	//

    private List<Bitmap> mBackgroundList;
    private Bitmap missile_bt;		//
    private Bitmap play_bt;
    private Bitmap stop_bt;

    private FishHero mFishHero;		//
    private npc8 npc8;
    private List<EnemyFish> mEnemyFishs;
    private ClearAllGoods mClearAllGoods;
    private DoubleGoods mDoubleGoods;
    private StarGoods mStarGoods;
    private GameObjectFactory factory;

    private static int[] game_background = new int[] {
            R.drawable.game_bg1, R.drawable.game_bg2, R.drawable.game_bg3, R.drawable.game_bg4,
            R.drawable.game_bg5, R.drawable.game_bg6, R.drawable.game_bg7, R.drawable.game_bg8
    };

    public MainView(Context context, GameSoundPool sounds) {
        super(context,sounds);
        // TODO Auto-generated constructor stub
        isPlay = true;
        speedTime = 1;
        factory = new GameObjectFactory();
        mBackgroundList = new ArrayList<Bitmap>();
        mEnemyFishs = new ArrayList<EnemyFish>();
        mFishHero = (FishHero) factory.createMyFish(getResources());
        mFishHero.setMainView(this);
        for(int i = 0; i < npc1.sumCount; i++){
            npc1 npc = (npc1) factory.createFishNpc1(getResources());
            mEnemyFishs.add(npc);
        }
        for(int i = 0; i < npc2.sumCount; i++){
            npc2 npc = (npc2) factory.createFishNpc2(getResources());
            mEnemyFishs.add(npc);
        }
        for(int i = 0; i < npc3.sumCount; i++){
            npc3 npc = (npc3) factory.createFishNpc3(getResources());
            mEnemyFishs.add(npc);
        }
        for(int i = 0; i < npc4.sumCount; i++){
            npc4 npc = (npc4) factory.createFishNpc4(getResources());
            mEnemyFishs.add(npc);
        }
        for(int i = 0; i < npc5.sumCount; i++){
            npc5 npc = (npc5) factory.createFishNpc5(getResources());
            mEnemyFishs.add(npc);
        }
        for(int i = 0; i < npc6.sumCount; i++){
            npc6 npc = (npc6) factory.createFishNpc6(getResources());
            mEnemyFishs.add(npc);
        }
        for(int i = 0; i < npc7.sumCount; i++){
            npc7 npc = (npc7) factory.createFishNpc7(getResources());
            mEnemyFishs.add(npc);
        }
        npc8 = (npc8) factory.createFishNpc8(getResources());

        mClearAllGoods = (ClearAllGoods) factory.createClearAllGoods(getResources());
        mDoubleGoods = (DoubleGoods) factory.createDoubleGoods(getResources());
        mStarGoods = (StarGoods) factory.createStarGoods(getResources());

        thread = new Thread(this);
    }

    @Override
    public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
        // TODO Auto-generated method stub
        super.surfaceChanged(arg0, arg1, arg2, arg3);
    }


    @Override
    public void surfaceCreated(SurfaceHolder arg0) {
        // TODO Auto-generated method stub
        super.surfaceCreated(arg0);
        initBitmap();

        for(GameObject obj:mEnemyFishs){
            obj.setScreenWH(screen_width,screen_height);
        }
        npc8.setScreenWH(screen_width, screen_height);
        mClearAllGoods.setScreenWH(screen_width, screen_height);
        mDoubleGoods.setScreenWH(screen_width, screen_height);
        mStarGoods.setScreenWH(screen_width, screen_height);
        mFishHero.setScreenWH(screen_width,screen_height);
        mFishHero.setAlive(true);
        if(thread.isAlive()){
            thread.start();
        }
        else{
            thread = new Thread(this);
            thread.start();
        }
    }


    @Override
    public void surfaceDestroyed(SurfaceHolder arg0) {
        // TODO Auto-generated method stub
        super.surfaceDestroyed(arg0);
        release();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_UP){
            isTouchFish = false;
            return true;
        }
        else if(event.getAction() == MotionEvent.ACTION_DOWN){
            float x = event.getX();
            float y = event.getY();
            // 按下暂停键
            if(x > play_bt_x && x < play_bt_x + play_bt.getWidth()
                    && y > play_bt_y && y < play_bt_y+ play_bt.getHeight()) {
                if (isPlay) {
                    isPlay = false;
                } else {
                    isPlay = true;
                    synchronized (thread) {
                        thread.notify();
                    }
                }
                return true;
            }
            // 按住玩家的鱼
            else if (x > mFishHero.getObject_x() && x < mFishHero.getObject_x() + mFishHero.getObject_width()
                    && y > mFishHero.getObject_y() && y < mFishHero.getObject_y() + mFishHero.getObject_height()){
                if(isPlay){
                    isTouchFish = true;
                }
                return true;
            }
//            //
//            else if(x > 10 && x < 10 + missile_bt.getWidth()
//                    && y > missile_bt_y && y < missile_bt_y + missile_bt.getHeight()){
//                if(missileCount > 0){
//                    missileCount--;
//                    sounds.playSound(5, 0);
//                    for(EnemyFish pobj:mEnemyFishs){
//                        if(pobj.isCanCollide()){
//                            pobj.die();
//                            addGameScore(pobj.getScore());
//                        }
//                    }
//                }
//                return true;
//            }
        }
        // 单处按下屏幕移动
        else if(event.getAction() == MotionEvent.ACTION_MOVE && event.getPointerCount() == 1){
            // 移动玩家的鱼
            if(isTouchFish){
                float x = event.getX();
                float y = event.getY();
                if (x > mFishHero.getMiddle_x()) mFishHero.setDirectionL(true);
                else mFishHero.setDirectionL(false);
                if(x > mFishHero.getMiddle_x() + 20){
                    if(mFishHero.getMiddle_x() + mFishHero.getSpeed() <= screen_width){
                        mFishHero.setMiddle_x(mFishHero.getMiddle_x() + mFishHero.getSpeed());
                    }
                }
                else if(x < mFishHero.getMiddle_x() - 20){
                    if(mFishHero.getMiddle_x() - mFishHero.getSpeed() >= 0){
                        mFishHero.setMiddle_x(mFishHero.getMiddle_x() - mFishHero.getSpeed());
                    }
                }
                if(y > mFishHero.getMiddle_y() + 20){
                    if(mFishHero.getMiddle_y() + mFishHero.getSpeed() <= screen_height){
                        mFishHero.setMiddle_y(mFishHero.getMiddle_y() + mFishHero.getSpeed());
                    }
                }
                else if(y < mFishHero.getMiddle_y() - 20){
                    if(mFishHero.getMiddle_y() - mFishHero.getSpeed() >= 0){
                        mFishHero.setMiddle_y(mFishHero.getMiddle_y() - mFishHero.getSpeed());
                    }
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public void initBitmap() {
        // TODO Auto-generated method stub
        play_bt = BitmapFactory.decodeResource(getResources(), R.drawable.gm_play);
        stop_bt = BitmapFactory.decodeResource(getResources(),R.drawable.gm_stop);
        for (int i = 0; i < 8; i++) {
            mBackgroundList.add(BitmapFactory.decodeResource(getResources(), game_background[i]));
        }
        scalex = screen_width / mBackgroundList.get(0).getWidth();
        scaley = screen_height / mBackgroundList.get(0).getHeight();
        play_bt_x = 10;
        play_bt_y = 10;

        // missile_bt_y = screen_height - 10 - missile_bt.getHeight();
    }

    public void initObject(){
        Collections.shuffle(mEnemyFishs);
        for(EnemyFish obj:mEnemyFishs){

            // npc1
            if(obj instanceof npc1){
                if(!obj.isAlive()){
                    obj.initial(speedTime,0,0);
                    break;
                }
            }
            // npc2
            else if(obj instanceof npc2){
                if(npc2Score > 100){
                  if(!obj.isAlive()){
                        obj.initial(speedTime,0,0);
                        break;
                    }
                }
            }
            // npc3
            else if(obj instanceof npc3){
                if(npc3Score >= 300){
                    if(!obj.isAlive()){
                        obj.initial(speedTime,0,0);
                        break;
                    }
                }
            }
            // npc4
            else if(obj instanceof npc4){
                if(npc4Score >= 500){
                    if(!obj.isAlive()){
                        obj.initial(speedTime,0,0);
                        break;
                    }
                }
            }
            // npc5
            else if(obj instanceof npc5){
                if(npc5Score >= 700){
                    if(!obj.isAlive()){
                        obj.initial(speedTime,0,0);
                        break;
                    }
                }
            }
            // npc6
            else if(obj instanceof npc6){
                if(npc6Score >= 900){
                    if(!obj.isAlive()){
                        obj.initial(speedTime,0,0);
                        break;
                    }
                }
            }
            // npc7
            else if(obj instanceof npc7){
                if(npc7Score >= 1100){
                    if(!obj.isAlive()){
                        obj.initial(speedTime,0,0);
                        break;
                    }
                }
            }
        }
        if(doubleScore >= 500){
            if(!mDoubleGoods.isAlive()){
                mDoubleGoods.initial(0,0,0);
                doubleScore = 0;
            }
        }
        if(starScore >= 1000){
            if(!mStarGoods.isAlive()){
                mStarGoods.initial(0,0,0);
                starScore = 0;
            }
        }
        if(clearAllScors >= 2000){
            if(!mClearAllGoods.isAlive()){
                mClearAllGoods.initial(0,0,0);
                clearAllScors = 0;
            }
        }
        if (npc8Score >= 1500) {
            if (!npc8.isAlive()) {
                npc8.initial(speedTime, 0, 0);
                npc8Score = 0;
            }
        }

        // speedTime++
        if(sumScore >= speedTime * 400 && speedTime < 6){
            speedTime++;
        }
    }

    // 绘画中心函数
    @Override
    public void drawSelf() {
        // TODO Auto-generated method stub
        try {
            canvas = sfh.lockCanvas();
            canvas.drawColor(Color.BLACK); // 设置画布背景色为黑色

            // 绘制背景
            canvas.save();
            canvas.scale(scalex, scaley, 0, 0);
            canvas.drawBitmap(mBackgroundList.get(sumScore / (1000) % 8), 0, 0, paint);   // ????????
            //canvas.drawBitmap(background2, 0, 0, paint); // ????????
            canvas.restore();

            // 绘制开始暂停按钮
            canvas.save();
            canvas.clipRect(play_bt_x, play_bt_y, play_bt_x + play_bt.getWidth(),play_bt_y + play_bt.getHeight());
            if(isPlay){
                canvas.drawBitmap(stop_bt, play_bt_x, play_bt_y, paint);
            } else {
                canvas.drawBitmap(play_bt, play_bt_x, play_bt_y, paint);
            }
            canvas.restore();

            // 双倍速度buffer
            if(mDoubleGoods.isAlive()){
                if (!mFishHero.isDoubleSpeed()) sounds.playSound(1, 0);
                if (mDoubleGoods.isCollide(mFishHero)) {
                    if (!mFishHero.isDoubleSpeed()) {
                        mFishHero.setSpeed(mFishHero.getSpeed() * 2);
                        mFishHero.setDoubleSpeed(true);
                        mFishHero.setStartTime(System.currentTimeMillis());
                        sounds.playSound(6, 0);
                    } else {
                        mFishHero.setStartTime(System.currentTimeMillis());
                    }
                } else {
                    mDoubleGoods.drawSelf(canvas);
                }
            }

            // 无敌状态buffer
            if(mStarGoods.isAlive()){
                if (!mFishHero.isStar()) sounds.playSound(1, 0);
                if (mStarGoods.isCollide(mFishHero)) {
                    if (!mFishHero.isStar()) {
                        mFishHero.setStar(true);
                        mFishHero.setStartTime(System.currentTimeMillis());
                        sounds.playSound(6, 0);
                    } else {
                        mFishHero.setStartTime(System.currentTimeMillis());
                    }
                } else {
                    mStarGoods.drawSelf(canvas);
                }
            }

            // 清屏状态buffer
            if(mClearAllGoods.isAlive()){
                sounds.playSound(1, 0);
                if (mClearAllGoods.isCollide(mFishHero)) {
                    addGameScore(200);
                    mFishHero.setSize(sumScore);
                    for (EnemyFish f : mEnemyFishs) {
                        if (f.isAlive() && f.isVisible()) {
                            f.die();
                            sounds.playSound(3, 0);
                        }
                    }
                } else {
                    mClearAllGoods.drawSelf(canvas);
                }

            }

            if (npc8.isAlive()) {
                npc8.drawSelf(canvas);
                if (npc8.getObject_x() / 100 > 0) {
                    Random ran = new Random();
                    int ca = ran.nextInt(10);
                    switch (ca / 5) {
                        case 0:
                        case 1:
                        case 2:
                            if(!mDoubleGoods.isAlive()){
                                mDoubleGoods.initial(0,0,0);
                                doubleScore = 0;
                            }
                            break;
                        case 3:
                            if(!mStarGoods.isAlive()){
                                mDoubleGoods.initial(0,0,0);
                                doubleScore = 0;
                            }
                            break;
                        case 4:
                            if(!mClearAllGoods.isAlive()){
                                mDoubleGoods.initial(0,0,0);
                                doubleScore = 0;
                            }
                            break;
                        default:
                            break;
                    }
                }
            }

            // 敌鱼
            for(EnemyFish obj:mEnemyFishs) {
                if (obj.isAlive()) {
                    // 敌鱼与玩家鱼碰撞
                    if (obj.isCanCollide() && mFishHero.isAlive()) {
                        if (obj.isCollide(mFishHero)) {
                            if (mFishHero.isStar() || mFishHero.getSize() > obj.getSize()) {
                                if (mFishHero.isStar() &&  mFishHero.getSize() <= obj.getSize()) {
                                    if (++starCount > 5) {
                                        starCount = 0;
                                        mFishHero.setStar(false);
                                    }
                                }
                                obj.die();
                                addGameScore(obj.getScore());
                                mFishHero.setEat(true);
                                sounds.playSound(3, 0);
                                mFishHero.setFishSize(sumScore);
                            } else if (mFishHero.getSize() < obj.getSize()) {
                                obj.setEat(true);
                                sounds.playSound(2, 0);
                                mFishHero.setAlive(false);
                            }
                        }
                    }
                    obj.drawSelf(canvas);
                }
            }


            if(!mFishHero.isAlive()){
                threadFlag = false;
                sounds.playSound(8, 0);
            }

            if (sumScore > 8000) {
                threadFlag = false;
                sounds.playSound(9, 0);
            }

            mFishHero.drawSelf(canvas);
            sounds.playSound(4, 0);

//            //??????????
//            if(missileCount > 0){
//                paint.setTextSize(40);
//                paint.setColor(Color.BLACK);
//                canvas.drawBitmap(missile_bt, 10,missile_bt_y, paint);
//                canvas.drawText("X "+String.valueOf(missileCount), 20 + missile_bt.getWidth(), screen_height - 25, paint);//????????
//            }


            paint.setTextSize(25);
            paint.setColor(Color.rgb(235, 161, 1));
            canvas.drawText("Score:"+String.valueOf(sumScore), 10 + play_bt.getWidth(), 40, paint);
            canvas.drawText("speedTime  "+String.valueOf(speedTime), screen_width - 250, 40, paint);

        } catch (Exception err) {
            err.printStackTrace();
        } finally {
            if (canvas != null)
                sfh.unlockCanvasAndPost(canvas);
        }
    }

    // 得分
    public void addGameScore(int score){
        npc1Score += score;
        npc2Score += score;
        npc3Score += score;
        npc4Score += score;
        npc5Score += score;
        npc6Score += score;
        npc7Score += score;
        npc8Score += score;

        clearAllScors += score;
        doubleScore += score;
        starScore += score;
        sumScore += score;

    }

    // 播放背景音乐
    public void playSound(int key){
        sounds.playSound(key, 0);
    }

    // 线程函数，同时是绘画中心函数
    @Override
    public void run() {
        // TODO Auto-generated method stub
        while (threadFlag) {
            long startTime = System.currentTimeMillis();
            initObject();
            drawSelf();
            long endTime = System.currentTimeMillis();
            if(!isPlay){
                synchronized (thread) {
                    try {
                        thread.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            try {
                if (endTime - startTime < 100)
                    Thread.sleep(100 - (endTime - startTime));
            } catch (InterruptedException err) {
                err.printStackTrace();
            }
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Message message = new Message();
        message.what = 	ConstantUtil.TO_END_VIEW;
        message.arg1 = Integer.valueOf(sumScore);
        mainActivity.getHandler().sendMessage(message);
    }


    @Override
    public void release() {
        // TODO Auto-generated method stub
        for(GameObject obj:mEnemyFishs){
            obj.release();
        }
        for (Bitmap b : mBackgroundList) {
            if (!b.isRecycled())
                b.recycle();
        }
        mFishHero.release();
        mStarGoods.release();
        mDoubleGoods.release();
        mClearAllGoods.release();
        if(!play_bt.isRecycled()){
            play_bt.recycle();
        }
        if(!stop_bt.isRecycled()){
            stop_bt.recycle();
        }
//        if(!missile_bt.isRecycled()){
//            missile_bt.recycle();
//        }
    }
}
