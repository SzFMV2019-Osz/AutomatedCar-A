package hu.oe.nik.szfmv.automatedcar.systemcomponents;

import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.VirtualFunctionBus;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputReader implements KeyListener {
    private InputManager inputManager;

    public InputReader(VirtualFunctionBus bus) {
        inputManager = new InputManager(bus);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        /* Not used */
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int pressedKey = e.getKeyCode();
        inputManager.HandleSyncKeys(pressedKey, true);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int releasedKey = e.getKeyCode();

        inputManager.HandleAsyncKeys(releasedKey);
        inputManager.HandleSyncKeys(releasedKey,false);
    }

}
