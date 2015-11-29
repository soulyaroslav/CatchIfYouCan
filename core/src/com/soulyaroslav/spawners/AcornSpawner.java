package com.soulyaroslav.spawners;

import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.TimeUtils;
import com.soulyaroslav.entities.Acorn;
import com.soulyaroslav.entitiesstate.ObjectState;
import com.soulyaroslav.gameworld.World;
import com.soulyaroslav.gameworld.Path;

import java.util.ArrayList;
import java.util.List;

public class AcornSpawner {

    private final World world;
    //
    private List<Acorn> acorns;
    // array for acorn id
    private List<Integer> acornIds;
    // pool
    private Pool<Acorn> acornPool;
    private Path path;
    //
    private float timeSpawn;
    public static int nutSpawnCount = 0;
    private float time = 1000000000;
    private int id = 0;

    public AcornSpawner(World world){
        this.world = world;
        acorns = new ArrayList<Acorn>();
        acornIds = new ArrayList<Integer>();
        acornPool = new Pool<Acorn>() {
            @Override
            protected Acorn newObject() {
                return new Acorn();
            }
        };
    }

    public void update(float delta){
        if (TimeUtils.nanoTime() - timeSpawn > time) {
            spawnAcorns();
        }
        free(delta);
        freeAll();
    }

    private void spawnAcorns() {
        // increase id
        id += 1;
        // Створення або отримання обєкта з пула
        Acorn acorn = acornPool.obtain();
        // ініціалізація обєкта
        acorn.initObject(path);
        // додавання обєкта в колекцію
        world.addDynamicGameObject(acorn);
        acorns.add(acorn);
        // set id
        acornIds.add(id);
        // нарахування кількості генерацій обєкта
        nutSpawnCount++;
        // час генерації обєкта
        timeSpawn = TimeUtils.nanoTime();

    }
    // Вивільнення обєктів
    private void free(float delta) {
        for (int i = 0; i < world.getAllGameObjects().size(); i++) {
            if(world.getAllGameObjects().get(i) instanceof Acorn) {
                Acorn acorn = (Acorn) world.getAllGameObjects().get(i);
                if (acorn.getState() == ObjectState.STATE_ALIVE) {
                    acorn.update(delta);
                }  else {
                    world.getAllGameObjects().remove(i);
                    acornPool.free(acorn);
                }
            }
        }

    }

    private void freeAll(){
        for (int i = 0; i < acorns.size(); i++) {
            Acorn acorn = acorns.get(i);
            if (acorn.getState() != ObjectState.STATE_ALIVE) {
                acorns.remove(i);
                acornIds.remove(i);
            }
        }
    }

    public void setPath(Path path){
        this.path = path;
    }

    public int getSpawnCount(){
        return nutSpawnCount;
    }

    public List<Acorn> getAcorns() {
        return acorns;
    }

    public List<Integer> getAcornIds() {
        return acornIds;
    }
}
