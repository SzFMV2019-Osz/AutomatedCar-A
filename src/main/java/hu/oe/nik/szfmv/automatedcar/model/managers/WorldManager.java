package hu.oe.nik.szfmv.automatedcar.model.managers;

import hu.oe.nik.szfmv.automatedcar.model.World;
import hu.oe.nik.szfmv.automatedcar.model.interfaces.IWorld;

public class WorldManager {

    private World currentWorld;


    /**
     * Initializes the world from a filepath
     * @param filepath Path of the world file (eg.: test_world.xml)
     * @return Return an IWorld object
     */
    public IWorld initWorld(String filepath) {
        // TODO
        return null;
    }

    /**
     * Returns the current world if it's been initialized
     * @return IWorld of the current world
     */
    public IWorld getWorld() {
        // TODO
        validate();
        return null;
    }

    /**
     * Throws an error if the initWorld hasn't been called yet
     */
    private void validate() {
    }
}
