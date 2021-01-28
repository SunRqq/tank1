package com.sunny.tank;

import java.awt.*;

public class Explode {
    private int x,y;
    private boolean living = true;
    TankFrame tf = null;

    public static final int WIDTH = ResourceMgr.explodes[0].getWidth();
    public static final int HEIGHT = ResourceMgr.explodes[0].getHeight();

    private  int step=0;
    public Explode(int x, int y, TankFrame tf) {
        this.x = x;
        this.y = y;
        this.tf = tf;
    }

    public void paint(Graphics g) {
        //画出一个爆炸
        g.drawImage(ResourceMgr.explodes[step++],x,y,null);

        if(step>=ResourceMgr.explodes.length){
            tf.explodes.remove(this);
        }
    }

}
