package com.soulyaroslav.spawners;

import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.TimeUtils;
import com.soulyaroslav.entities.GoldAcorn;
import com.soulyaroslav.entitiesstate.ObjectState;
import com.soulyaroslav.gameworld.Path;
import com.soulyaroslav.gameworld.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GoldAcornSpawner {

        private final World world;
        //private Nut nut;
        private Pool<GoldAcorn> goldAcornPool;
        private Path path;

        private Random random;
        private float timeSpawn;
        private float time = 1500000000;

        public GoldAcornSpawner(World world){
            this.world = world;
            goldAcornPool = new Pool<GoldAcorn>() {
                @Override
                protected GoldAcorn newObject() {
                    return new GoldAcorn();
                }
            };
            random = new Random();
        }

        public void update(float delta){
            if (TimeUtils.nanoTime() - timeSpawn > time) {
                spawnGoldAcorns();
            }

            free(delta);
        }

        public void spawnGoldAcorns() {
            if(random.nextFloat() > 0.98f &&
                    world.getNutSpawner().getSpawnCount() % 15 == 0) {
                // Створення або отримання обєкта з пула
                GoldAcorn goldAcorn = goldAcornPool.obtain();
                // ініціалізація обєкта
                goldAcorn.initObject(path);
                // додавання обєкта в колекцію
                world.addDynamicGameObject(goldAcorn);
                // час генерації обєкта
                timeSpawn = TimeUtils.nanoTime();
            }
        }
        // Вивільнення обєктів
        public void free(float delta) {
            for (int i = 0; i < world.getAllGameObjects().size(); i++) {
                if(world.getAllGameObjects().get(i) instanceof GoldAcorn) {
                    GoldAcorn goldAcorn = (GoldAcorn) world.getAllGameObjects().get(i);
                    if (goldAcorn.getState() == ObjectState.STATE_ALIVE) {
                        goldAcorn.update(delta);
                    }  else {
                        world.getAllGameObjects().remove(i);
                        goldAcornPool.free(goldAcorn);
                    }
                }
            }
        }

        public void setPath(Path path){
            this.path = path;
        }
}
