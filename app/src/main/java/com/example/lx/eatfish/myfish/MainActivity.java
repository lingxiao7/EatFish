package com.example.lx.eatfish.myfish;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.example.lx.eatfish.R;
import com.example.lx.eatfish.constant.ConstantUtil;
import com.example.lx.eatfish.sounds.GameSoundPool;
import com.example.lx.eatfish.view.EndView;
import com.example.lx.eatfish.view.HelpView;
import com.example.lx.eatfish.view.MainView;
import com.example.lx.eatfish.view.ReadyView;

public class MainActivity extends Activity {
    private EndView endView;
    private MainView mainView;
    private ReadyView readyView;
    private HelpView helpView;
    private GameSoundPool sounds;

    public boolean enablePlaySound;

    private Handler handler = new Handler(){  // 更新UI线程控件
        @Override
        public void handleMessage(Message msg){
            if(msg.what == ConstantUtil.TO_MAIN_VIEW){
                toMainView();
            }
            else  if(msg.what == ConstantUtil.TO_END_VIEW){
                toEndView(msg.arg1);
            }
            else  if(msg.what == ConstantUtil.TO_HELP_VIEW){
                toHelpView();
            }
            else  if(msg.what == ConstantUtil.TO_READY_VIEW){
                toReadyView();
            }
            else  if(msg.what == ConstantUtil.END_GAME){
                endGame();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        sounds = new GameSoundPool(this);
        sounds.initGameSound();
        readyView = new ReadyView(this,sounds);
        setContentView(readyView);
    }

    @Override
    protected void onResume() {
        /**
         * 设置为横屏
         */
        if(getRequestedOrientation()!=ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
        super.onResume();
    }

    private void toReadyView() {
        if(readyView == null){
            readyView = new ReadyView(this,sounds);
        }
        setContentView(readyView);
        helpView = null;
    }

    //显示游戏的主界面
    public void toMainView(){
        sounds.playSound(7, 0);
        if(mainView == null){
            mainView = new MainView(this,sounds);
        }
        setContentView(mainView);
        readyView = null;
        endView = null;
    }
    //显示游戏结束的界面
    public void toEndView(int score){
        if(endView == null){
            endView = new EndView(this,sounds);
            endView.setScore(score);
        }
        setContentView(endView);
        mainView = null;
    }
    // 帮助界面
    public void toHelpView(){
        if(helpView == null){
            helpView = new HelpView(this,sounds);
        }
        setContentView(helpView);
        readyView = null;
    }

    //结束游戏
    public void endGame(){
        if(readyView != null){
            readyView.setThreadFlag(false);
        }
        else if(mainView != null){
            mainView.setThreadFlag(false);
        }
        else if(endView != null){
            endView.setThreadFlag(false);
        }
        else if(helpView != null) {
            helpView.setThreadFlag(false);
        }
        this.finish();
    }

    //getter和setter方法
    public Handler getHandler() {
        return handler;
    }
    public void setHandler(Handler handler) {
        this.handler = handler;
    }
}