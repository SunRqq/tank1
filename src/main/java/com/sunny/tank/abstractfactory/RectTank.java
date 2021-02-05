package com.sunny.tank.abstractfactory;


import com.sunny.tank.*;

import java.awt.*;
import java.util.Random;

public class RectTank extends BaseTank {
    /**
     *抽象出tank类，设置相应的属性和方法
     */
    int x,y;
    Dir dir=Dir.DOWN;
    private static final int SPEED=10;
    private boolean moving = true;

    private boolean living = true;

    public static final int WIDTH = ResourceMgr.goodTankU.getWidth();
    public static final int HEIGHT = ResourceMgr.goodTankU.getHeight();

    private Random random = new Random();
     TankFrame tf =null;

//    public Rectangle rect = new Rectangle();

     FireStrategy fs ;

    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }

    public void setDir(Dir dir) {
        this.dir = dir;
    }

    public Dir getDir() {
        return dir;
    }

    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public boolean isLiving() {
        return living;
    }

    public void setLiving(boolean living) {
        this.living = living;
    }

    public RectTank(int x, int y, Dir dir, Group group, TankFrame tf) {
        super();
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group = group;
        this.tf = tf;

        rect.x = this.x;
        rect.y = this.y;
        rect.width = WIDTH;
        rect.height = HEIGHT;

        if(group == Group.GOOD){
            String goodFsName = (String) PropertyMgs.get("goodFs");
            try {
                fs = (FireStrategy) Class.forName(goodFsName).newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            fs = new DefaultFireStrategy();
        }
    }

    @Override
    public void paint(Graphics g) {
        if(!living){
            tf.tanks.remove(this);
        }
        Color c = g.getColor();
        g.setColor(group==Group.GOOD?Color.RED:Color.BLUE);
        g.fillRect(x,y,30,30);
        g.setColor(c);

        move();

    }

    private void move() {
        if(!moving){
            return;
        }
        //根据方向进行移动
        switch (dir){
            case LEFT:x-=SPEED;
                break;
            case UP:y-=SPEED;
                break;
            case RIGHT:x+=SPEED;
                break;
            case DOWN:y+=SPEED;
                break;
        }

        //敌人tank随机打出子弹
        if(this.group==Group.BAD && random.nextInt(100)>95){
            this.fire();
        }
        if(this.group==Group.BAD && random.nextInt(100)>95) {
            randomDir();
        }

        boundsCheck();

        //update rect
        rect.x = this.x;
        rect.y = this.y;

    }

    //边界留2个px,不然太丑
    private void boundsCheck() {
        if(this.x<2){
            x=2;
        }
        if(this.y<28){
            y=28;
        }
        if(this.x>TankFrame.GAME_WIDTH- RectTank.WIDTH-2){
            x = TankFrame.GAME_WIDTH- RectTank.WIDTH-2;
        }
        if(this.y>TankFrame.GAME_HEIGHT- RectTank.HEIGHT-2){
            y=TankFrame.GAME_HEIGHT- RectTank.HEIGHT-2;
        }
    }

    private void randomDir() {
        this.dir = Dir.values()[random.nextInt(4)];
    }

    public void fire(){
//        fs.fire(this);//c策略模式
        int bX = this.x+ RectTank.WIDTH/2-Bullet.WIDTH/2;
        int bY = this.y+ RectTank.HEIGHT/2-Bullet.HEIGHT/2;

        Dir[] dirs = Dir.values();
        for (Dir dir :dirs) {
            tf.gf.ctreateBullet(bX, bY, dir, group, tf);
        }
        if(group==Group.GOOD){
            new Thread(() -> new Audio("audio/tank_fire.war"));
        }
    }

    @Override
    public void die() {
        this.living = false;
    }

    @Override
    public int getX() {
        return 0;
    }

    @Override
    public int getY() {
        return 0;
    }
}
