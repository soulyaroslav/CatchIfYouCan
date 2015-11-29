package com.soulyaroslav.spawners;

import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.TimeUtils;
import com.soulyaroslav.entities.Fine;
import com.soulyaroslav.entitiesstate.ObjectState;
import com.soulyaroslav.gameworld.Path;
import com.soulyaroslav.gameworld.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FineSpawner {
    // world instance
    private final World world;
    // pool for fine
    private Pool<Fine> finePool;
    //
    private Path path;
    //
    private Random random;
    private float timeSpawn;
    private float time = 1300000000;

    public FineSpawner(World world) {
        this.world = world;
        finePool = new Pool<Fine>() {
            @Override
            protected Fine newObject() {
                return new Fine();
            }
        };
        random = new Random();
    }

    public void update(float delta){
        if (TimeUtils.nanoTime() - timeSpawn > time) {
            spawnAcorns();
        }

        free(delta);
    }

    public void spawnAcorns() {
        if(random.nextFloat() < 0.5f
                && world.getNutSpawner().getSpawnCount() % 4 == 0) {
            // Створення або отримання обєкта з пула
            Fine fine = finePool.obtain();
            // ініціалізація обєкта
            fine.initObject(path);
            // додавання обєкта в колекцію
            world.addDynamicGameObject(fine);
            // час генерації обєкта
            timeSpawn = TimeUtils.nanoTime();
        }
    }
    // Вивільнення обєктів
    public void free(float delta) {
        for (int i = 0; i < world.getAllGameObjects().size(); i++) {
            if(world.getAllGameObjects().get(i) instanceof Fine) {
                Fine fine = (Fine) world.getAllGameObjects().get(i);
                if (fine.getState() == ObjectState.STATE_ALIVE) {
                    fine.update(delta);
                }  else {
                    world.getAllGameObjects().remove(i);
                    finePool.free(fine);
                }
            }
        }
    }

    public void setPath(Path path) {
        this.path = path;
    }
}
