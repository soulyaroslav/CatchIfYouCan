package com.soulyaroslav.spawners;

import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.TimeUtils;
import com.soulyaroslav.entities.Bomb;
import com.soulyaroslav.entitiesstate.ObjectState;
import com.soulyaroslav.gameworld.Path;
import com.soulyaroslav.gameworld.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BombSpawner {
    // world instance
    private final World world;
    // pool for fine
    private Pool<Bomb> bombPool;
    //
    private Path path;
    //
    private Random random;
    private float timeSpawn;
    private float time = 1200000000;

    public BombSpawner(World world) {
        this.world = world;
        bombPool = new Pool<Bomb>() {
            @Override
            protected Bomb newObject() {
                return new Bomb();
            }
        };
        random = new Random();
    }

    public void update(float delta){
        if (TimeUtils.nanoTime() - timeSpawn > time) {
            spawnBombs();
        }

        free(delta);
    }

    public void spawnBombs() {
        if(random.nextFloat() > 0.5f  &&
                world.getNutSpawner().getSpawnCount() % 3 == 0) {
            // Створення або отримання обєкта з пула
            Bomb bomb = bombPool.obtain();
            // ініціалізація обєкта
            bomb.initObject(path);
            // додавання обєкта в колекцію
            world.addDynamicGameObject(bomb);
            // час генерації обєкта
            timeSpawn = TimeUtils.nanoTime();
        }
    }
    // Вивільнення обєктів
    public void free(float delta) {
        for (int i = 0; i < world.getAllGameObjects().size(); i++) {
            if(world.getAllGameObjects().get(i) instanceof Bomb) {
                Bomb bomb = (Bomb) world.getAllGameObjects().get(i);
                if (bomb.getState() == ObjectState.STATE_ALIVE) {
                    bomb.update(delta);
                }  else {
                    world.getAllGameObjects().remove(i);
                    bombPool.free(bomb);
                }
            }
        }
    }

    public void setPath(Path path) {
        this.path = path;
    }
}

