package com.soulyaroslav.spawners;

import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.TimeUtils;
import com.soulyaroslav.entities.BadAcorn;
import com.soulyaroslav.entitiesstate.ObjectState;
import com.soulyaroslav.gameworld.World;
import com.soulyaroslav.gameworld.Path;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BadNutSpawner {

    private World world;
    private Pool<BadAcorn> badAcornPool;
    public final Random random;
    private Path path;
    private float timeSpawn;
    private float time = 1100000000;

    public BadNutSpawner(World world) {
        this.world = world;
        badAcornPool = new Pool<BadAcorn>() {
            @Override
            protected BadAcorn newObject() {
                return new BadAcorn();
            }
        };
        random = new Random();
    }

    public void update(float delta){
        if (TimeUtils.nanoTime() - timeSpawn > time) {
            spawnBadNuts();
        }

        free(delta);
    }

    public void spawnBadNuts(){
        if(random.nextFloat() < 0.5f
                && world.getNutSpawner().getSpawnCount() % 2 == 0) {
            // Створення або отримання обєкта з пула
            BadAcorn badAcorn = badAcornPool.obtain();
            // ініціалізація обєкта
            badAcorn.initObject(path);
            // додавання обєкта в колекцію
            world.addDynamicGameObject(badAcorn);
            // час генерації обєкта
            timeSpawn = TimeUtils.nanoTime();
        }
    }

    public void free(float delta){
        for (int i = 0; i < world.getAllGameObjects().size(); i++) {
            if(world.getAllGameObjects().get(i) instanceof BadAcorn) {
                BadAcorn badAcorn = (BadAcorn) world.getAllGameObjects().get(i);
                if (badAcorn.getState() == ObjectState.STATE_ALIVE) {
                    badAcorn.update(delta);
                }  else {
                    world.getAllGameObjects().remove(i);
                    badAcornPool.free(badAcorn);
                }
            }
        }
    }

    public void setPath(Path path){
        this.path = path;
    }
}
