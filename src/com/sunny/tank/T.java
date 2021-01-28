package com.sunny.tank;


public class T {
    public static void main(String[] args) throws InterruptedException {
        TankFrame tf = new TankFrame();
        int initTankCount = Integer.parseInt(PropertyMgs.get("initTankCount").toString());
        //初始化敌方tank
        for(int i=0;i<initTankCount;i++){
            tf.tanks.add(new Tank(50+i*80,200,Dir.DOWN,Group.BAD,tf));
        }

        /**
         * 主线程每隔50ms刷新窗口
         * repaint()方法自动调用paint()
         */
        while(true){
            Thread.sleep(50);
            tf.repaint();
        }
    }
}
