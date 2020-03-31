package me.hydos.techrebornApi.cursed;

import com.chocohead.mm.api.ClassTinkerers;

public class CursedInit implements Runnable{


    @Override
    public void run() {
        System.out.println("stfu Tech Reborn");
        addEntry("mcp");
    }

    public void addEntry(String die){
        ClassTinkerers.enumBuilder("techreborn.client.EGui", new Class<?>[0]).addEnum(die).build();
    }
}
