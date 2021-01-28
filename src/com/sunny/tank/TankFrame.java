package com.sunny.tank;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

public class TankFrame extends Frame {
    //tank
    Tank myTank = new Tank(200,400,Dir.DOWN,Group.GOOD,this);
    //子弹
    List<Bullet> bullets = new ArrayList<>();
    //敌方tank
    List<Tank> tanks = new ArrayList<>();
    //爆炸
    List<Explode> explodes = new ArrayList<>();
//    Explode e = new Explode(100,100,this);

    static final int GAME_WIDTH=1080,GAME_HEIGHT=760;

    public TankFrame() {
        setSize(GAME_WIDTH, GAME_HEIGHT);
        setResizable(false);
        setTitle("tank war");
        setVisible(true);

        this.addKeyListener(new MyKeyListener());
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }

        });
    }

    /**
     * 闪烁问题解决：双缓冲
     * 在内存里有一张图片，在内存里全部画好后再一次性加载（画）到屏幕上
     */
    Image offScreenImage = null;
    @Override
    public void update(Graphics g) {
        if(offScreenImage==null){
            offScreenImage = this.createImage(GAME_WIDTH,GAME_HEIGHT);
        }
        Graphics gOffScreen = offScreenImage.getGraphics();
        Color c = gOffScreen.getColor();
        gOffScreen.setColor(Color.BLACK);
        gOffScreen.fillRect(0,0,GAME_WIDTH,GAME_HEIGHT);
        gOffScreen.setColor(c);
        paint(gOffScreen);//在内存里把图片画满
        g.drawImage(offScreenImage,0,0,null);//一次性画到屏幕上
    }

    @Override
    public void paint(Graphics g) {
        Color c = g.getColor();
        g.setColor(Color.WHITE);
        g.drawString("子弹的数量："+bullets.size(),10,60);
        g.drawString("敌人的数量："+tanks.size(),10,80);
        g.drawString("爆炸的数量："+explodes.size(),10,100);
        g.setColor(c);

        myTank.paint(g);
        //集合遍历删除问题
        for(int i=0;i<bullets.size();i++) {
            bullets.get(i).paint(g);
        }

        for(int i=0;i<tanks.size();i++){
            tanks.get(i).paint(g);
        }

        //碰撞检测
        for(int i=0;i<bullets.size();i++){
            for(int j=0;j<tanks.size();j++){
            bullets.get(i).collideWith(tanks.get(j));
            }
        }

        //爆炸
        for(int i=0;i<explodes.size();i++){
            explodes.get(i).paint(g);
        }

    }


    class MyKeyListener extends KeyAdapter {
        /**
         * 增加键盘处理，根据上下左右箭头的状态判定tank的移动方向
         */
        boolean bl = false;
        boolean bu = false;
        boolean br = false;
        boolean bd = false;
        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            switch (key){
                case  KeyEvent.VK_LEFT:
                    bl=true;
                    break;
                case KeyEvent.VK_UP:
                    bu=true;
                    break;
                case KeyEvent.VK_RIGHT:
                    br = true;
                    break;
                case KeyEvent.VK_DOWN:
                    bd=true;
                    break;
                default:
                    break;
            }
            //根据tank方法确定tank位移
            setMainTankDir();
        }

        private void setMainTankDir() {
            if (!bl && !bu && !br && !bd) {
                myTank.setMoving(false);
            }else {
                myTank.setMoving(true);
                if (bl) {
                    myTank.setDir(Dir.LEFT);
                }
                if (bu) {
                    myTank.setDir(Dir.UP);
                }
                if (br) {
                    myTank.setDir(Dir.RIGHT);
                }
                if (bd) {
                    myTank.setDir(Dir.DOWN);
                }
            }

        }

        @Override
        public void keyReleased(KeyEvent e) {
            int key = e.getKeyCode();
            switch (key){
                case  KeyEvent.VK_LEFT:
                    bl=false;
                    break;
                case KeyEvent.VK_UP:
                    bu=false;
                    break;
                case KeyEvent.VK_RIGHT:
                    br = false;
                    break;
                case KeyEvent.VK_DOWN:
                    bd=false;
                    break;

                case KeyEvent.VK_CONTROL:
                    myTank.fire();
                    break;
                default:
                    break;
            }
            setMainTankDir();

        }


    }

}
