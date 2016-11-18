package com.example.lx.eatfish.sounds;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

import com.example.lx.eatfish.R;
import com.example.lx.eatfish.myfish.MainActivity;

import java.util.HashMap;

/**
 * Created by lx on 2016/11/16.
 * 设置声音类GameSoundPool
 * 该类是声音设置界面的实现类，主要负责声音的播放
 */
public class GameSoundPool {
    private MainActivity mainActivity;
    private SoundPool soundPool;
    private HashMap<Integer,Integer> map;


    public GameSoundPool(MainActivity mainActivity){
        this.mainActivity = mainActivity;
        map = new HashMap<Integer,Integer>();
        soundPool = new SoundPool(8, AudioManager.STREAM_MUSIC,0);
        mainActivity.enablePlaySound = true;
    }

    public void initGameSound(){
        map.put(1, soundPool.load(mainActivity, R.raw.daoju, 1));
        map.put(2, soundPool.load(mainActivity, R.raw.fishdie, 1));
        map.put(3, soundPool.load(mainActivity, R.raw.fisheat, 1));
        map.put(4, soundPool.load(mainActivity, R.raw.fishbg1, 1));
        map.put(5, soundPool.load(mainActivity, R.raw.hitme, 1));
        map.put(6, soundPool.load(mainActivity, R.raw.jingbao, 1));

        map.put(7, soundPool.load(mainActivity, R.raw.start, 1));
        map.put(8, soundPool.load(mainActivity, R.raw.over, 1));
        map.put(9, soundPool.load(mainActivity, R.raw.win, 1));
    }

    //播放音效
    public void playSound(int sound,int loop){
        if(!mainActivity.enablePlaySound) return;
        AudioManager am = (AudioManager)mainActivity.getSystemService(Context.AUDIO_SERVICE);
        float stramVolumeCurrent = am.getStreamVolume(AudioManager.STREAM_MUSIC);
        float stramMaxVolumeCurrent = am.getStreamVolume(AudioManager.STREAM_MUSIC);
        float volume = stramVolumeCurrent/stramMaxVolumeCurrent;
        soundPool.play(map.get(sound), volume, volume, 1, loop, 1.0f);
    }
}
