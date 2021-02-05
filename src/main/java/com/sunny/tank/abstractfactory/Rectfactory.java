package com.sunny.tank.abstractfactory;

import com.sunny.tank.Dir;
import com.sunny.tank.Group;
import com.sunny.tank.TankFrame;

public class Rectfactory extends GameFactory {
    @Override
    public BaseTank ctreateTank(int x, int y, Dir dir, Group group, TankFrame tf) {
        return null;
    }

    @Override
    public BaseExplode ctreateExplode(int x, int y, TankFrame tf) {
        return new RectExplode(x,y,tf);
    }

    @Override
    public BaseBullet ctreateBullet(int x, int y,Dir dir, Group group, TankFrame tf) {
        return new RectBullet(x,y,dir,group,tf);
    }
}
