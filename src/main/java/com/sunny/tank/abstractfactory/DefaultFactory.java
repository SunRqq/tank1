package com.sunny.tank.abstractfactory;


import com.sunny.tank.*;

public  class DefaultFactory extends GameFactory {
    @Override
    public BaseTank ctreateTank(int x, int y, Dir dir, Group group, TankFrame tf) {
        return new Tank(x,y,dir,group,tf);
    }

    @Override
    public BaseExplode ctreateExplode(int x, int y, TankFrame tf) {
        return new Explode(x,y,tf);
    }

    @Override
    public BaseBullet ctreateBullet(int x, int y, Dir dir, Group group, TankFrame tf) {
        return new Bullet(x,y,dir,group,tf);
    }

}
