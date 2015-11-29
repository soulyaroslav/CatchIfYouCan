package com.soulyaroslav.gameworld;

import com.soulyaroslav.entities.Acorn;
import com.soulyaroslav.game.QGame;

import java.util.ArrayList;
import java.util.List;

public class WorldPaths {

    private List<Path> freePaths;
    // ширина дорожки
    private float pathWidth = 9;

    public WorldPaths() {
        freePaths = new ArrayList<Path>();
        initList();
    }

    public void initList() {
        while (pathWidth < QGame.VIRTUAL_WIDTH - Acorn.ACORN_WIDTH) {
            freePaths.add(new Path(pathWidth, 0));
            pathWidth += 64;
        }
        System.out.println("Size of freePaths: " + freePaths.size());
    }

    public List<Path> getPaths(){
        return freePaths;
    }
}
