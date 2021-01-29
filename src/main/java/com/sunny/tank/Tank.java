package com.sunny.tank;


import com.sunny.tank.abstractfactory.BaseTank;

import java.awt.*;
import java.util.Random;

public class Tank extends BaseTank {
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
    Group group = Group.BAD;
    TankFrame tf =null;

    Rectangle rect = new Rectangle();

     FireStrategy fs ;

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
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

    public Tank(int x, int y, Dir dir, Group group,TankFrame tf) {
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

    public void paint(Graphics g) {
        if(!living){
            tf.tanks.remove(this);
        }
        switch (dir){
            case LEFT:
                g.drawImage(this.group==Group.GOOD?ResourceMgr.goodTankL:ResourceMgr.badTankL,x,y,null);
                break;
            case UP:
                g.drawImage(this.group==Group.GOOD?ResourceMgr.goodTankU:ResourceMgr.badTankU,x,y,null);
                break;
            case RIGHT:
                g.drawImage(this.group==Group.GOOD?ResourceMgr.goodTankR:ResourceMgr.badTankR,x,y,null);
                break;
            case DOWN:
                g.drawImage(this.group==Group.GOOD?ResourceMgr.goodTankD:ResourceMgr.badTankD,x,y,null);
                break;
        }

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
        if(this.x>TankFrame.GAME_WIDTH-Tank.WIDTH-2){
            x = TankFrame.GAME_WIDTH-Tank.WIDTH-2;
        }
        if(this.y>TankFrame.GAME_HEIGHT-Tank.HEIGHT-2){
            y=TankFrame.GAME_HEIGHT-Tank.HEIGHT-2;
        }
    }

    private void randomDir() {
        this.dir = Dir.values()[random.nextInt(4)];
    }

    public void fire(){
        fs.fire(this);
    }

    public void die() {
        this.living = false;
    }
}
