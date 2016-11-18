package com.example.lx.eatfish.factory;

import android.content.res.Resources;

import com.example.lx.eatfish.object.ClearAllGoods;
import com.example.lx.eatfish.object.DoubleGoods;
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

/**
 * Created by lx on 2016/11/16.
 */
public class GameObjectFactory {
    public GameObject createMyFish(Resources resources){
        return new FishHero(resources);
    }
    public GameObject createFishNpc1(Resources resources){
        return new npc1(resources);
    }
    public GameObject createFishNpc2(Resources resources){
        return new npc2(resources);
    }
    public GameObject createFishNpc3(Resources resources){
        return new npc3(resources);
    }
    public GameObject createFishNpc4(Resources resources){
        return new npc4(resources);
    }
    public GameObject createFishNpc5(Resources resources){
        return new npc5(resources);
    }
    public GameObject createFishNpc6(Resources resources){
        return new npc6(resources);
    }
    public GameObject createFishNpc7(Resources resources){
        return new npc7(resources);
    }
    public GameObject createFishNpc8(Resources resources){
        return new npc8(resources);
    }
    public GameObject createClearAllGoods(Resources resources){
        return new ClearAllGoods(resources);
    }
    public GameObject createDoubleGoods(Resources resources){
        return new DoubleGoods(resources);
    }
    public GameObject createStarGoods(Resources resources){
        return new StarGoods(resources);
    }
}
