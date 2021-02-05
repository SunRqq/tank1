package com.sunny.tank.abstractfactory;


import com.sunny.tank.*;
import com.sunny.tank.abstractfactory.BaseBullet;

import java.awt.*;

/**
 * 子弹
 */
public class RectBullet extends BaseBullet {
    private static final int SPEED = 20;
    public static final int WIDTH = ResourceMgr.bulletD.getWidth();
    public static final int HEIGHT = ResourceMgr.bulletD.getHeight();

    private boolean living = true;
    private int x,y;
    private Dir dir;

    Rectangle rect = new Rectangle();

    TankFrame tf = null;
    private Group group = Group.BAD;

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public RectBullet(int x, int y, Dir dir, Group group, TankFrame tf) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group = group;
        this.tf = tf;

        rect.x = this.x;
        rect.y = this.y;
        rect.width = WIDTH;
        rect.height = HEIGHT;

        tf.bullets.add(this);
    }

    @Override
    public void paint(Graphics g) {
        if(!living){
            tf.bullets.remove(this);
        }
        /**
         * 子弹的中心位置：计算炮筒的位置
         *
         */
        Color c = g.getColor();
        g.setColor(Color.YELLOW);
        g.fillRect(x,y,20,20);
        g.setColor(c);
        move();

    }

    private void move() {
        if(!living){
            tf.bullets.remove(this);
        }
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

        rect.x = this.x;
        rect.y = this.y;

        if(x<0||y<0||x>TankFrame.GAME_WIDTH||y>TankFrame.GAME_HEIGHT){
            living = false;
        }
    }

    /**
     * 打掉敌人，需要碰撞检测过程
     * @param tank
     */
    @Override
    public void collideWith(Tank tank) {
        if(this.group == tank.getGroup()){return;}

        //TODO:用一个rect来记录子弹的位置
//        Rectangle rect1 = new Rectangle(this.x,this.y,WIDTH,HEIGHT);
//        Rectangle rect2 = new Rectangle(tank.getX(),tank.getY(),Tank.WIDTH,Tank.HEIGHT);


        if(rect.intersects(tank.rect)){
            tank.die();
            this.die();
            int eX = tank.getX()+Tank.WIDTH/2-Explode.WIDTH/2;
            int eY = tank.getY()+Tank.HEIGHT/2-Explode.HEIGHT/2;
            tf.explodes.add(tf.gf.ctreateExplode(eX,eY,tf));
        }
    }

    private void die() {
        this.living=false;
    }
}
