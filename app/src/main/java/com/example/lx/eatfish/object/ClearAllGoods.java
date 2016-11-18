package com.example.lx.eatfish.object;

import android.content.res.Resources;
import android.graphics.BitmapFactory;

import com.example.lx.eatfish.R;

/**
 * Created by lx on 2016/11/16.
 */
public class ClearAllGoods extends GameGoods{
    public ClearAllGoods(Resources resources) {
        super(resources);
    }

    // 初始化图片资源的
    @Override
    protected void initBitmap() {
        // TODO Auto-generated method stub
        bmp = BitmapFactory.decodeResource(resources, R.drawable.goods_clearall);
        object_width = bmp.getWidth();
        object_height = bmp.getHeight();
    }
}
