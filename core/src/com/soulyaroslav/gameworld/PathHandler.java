package com.soulyaroslav.gameworld;

import com.badlogic.gdx.math.MathUtils;
import com.soulyaroslav.gameobject.DynamicGameObject;
import java.util.List;

public class PathHandler {

    private World world;
    private List<Path> freePaths;
    private List<DynamicGameObject> dynamicGameObjects;

    public PathHandler(World world) {
        this.world = world;
        freePaths = world.getPathManager().getPaths();
        dynamicGameObjects = world.getAllGameObjects();
    }

    public Path getFreePath(){
        Path path = freePaths.get(MathUtils.random(0, freePaths.size() - 1));
        for(int i = 0; i < dynamicGameObjects.size(); i++){
            if(path.contains(dynamicGameObjects.get(i).getBounds())){
                path = freePaths.get(MathUtils.random(0, freePaths.size() - 1));
            }
        }
        return path;
    }
}
