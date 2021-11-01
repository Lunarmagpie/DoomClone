package com.DoomClone;

import com.DoomClone.core.GameLoop;
import com.DoomClone.states.*;

class App {
    public static void main(String[] args) {
        GameLoop loop = new GameLoop(60, new Stage());
        loop.changeState(new Stage());
        loop.run();
    }
}