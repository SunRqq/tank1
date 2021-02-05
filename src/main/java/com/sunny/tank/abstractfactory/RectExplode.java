package com.sunny.tank.abstractfactory;

import com.sunny.tank.Audio;
import com.sunny.tank.ResourceMgr;
import com.sunny.tank.TankFrame;

import java.awt.*;

public class RectExplode extends BaseExplode {
    private int x,y;
    private boolean living = true;
    TankFrame tf = null;

    public static final int WIDTH = ResourceMgr.explodes[0].getWidth();
    public static final int HEIGHT = ResourceMgr.explodes[0].getHeight();

    private  int step=0;
    public RectExplode(int x, int y, TankFrame tf) {
        this.x = x;
        this.y = y;
        this.tf = tf;
        //new Thread(()->new Audio("audio/explode.wav").play()).start();
    }

    @Override
    public void paint(Graphics g) {
        //画出一个爆炸
//        g.drawImage(ResourceMgr.explodes[step++],x,y,null);
        //画一个方的
        Color c = g.getColor();
        g.setColor(Color.RED);
        g.fillRect(x,y,10*step,10*step);
        step++;
        if(step>=5){
            tf.explodes.remove(this);
        }
        g.setColor(c);
    }


}
