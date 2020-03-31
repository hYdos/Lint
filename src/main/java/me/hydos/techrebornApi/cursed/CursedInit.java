package me.hydos.techrebornApi.cursed;

import com.chocohead.mm.api.ClassTinkerers;

public class CursedInit implements Runnable{


    @Override
    public void run() {
        ClassTinkerers.enumBuilder("techreborn.client.EGui", new Class<?>[0]).addEnum("mcp").build();
        System.out.println("stfu Tech Reborn");
    }
}
