package com.maxzuo.facade;

/**
 * Created by zfh on 2020/04/14
 */
public class Computer {

    private CPU cpu;

    private Memory memory;

    private Disk disk;

    public Computer() {
        cpu = new CPU();
        memory = new Memory();
        disk = new Disk();
    }

    public void startup() {
        cpu.startup();
        memory.startup();
        disk.startup();
    }

    public void shutdown() {
        cpu.shutdown();
        memory.shutdown();
        disk.shutdown();
    }
}
