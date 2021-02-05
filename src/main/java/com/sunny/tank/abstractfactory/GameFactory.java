package com.sunny.tank.abstractfactory;

import com.sunny.tank.Dir;
import com.sunny.tank.Group;
import com.sunny.tank.TankFrame;

public abstract class GameFactory {
    public abstract  BaseTank ctreateTank(int x, int y, Dir dir, Group group, TankFrame tf);
    public abstract  BaseExplode ctreateExplode(int x, int y, TankFrame tf);
    public abstract  BaseBullet ctreateBullet(int x, int y,Dir dir, Group group, TankFrame tf);
}
