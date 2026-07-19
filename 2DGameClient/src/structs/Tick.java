package structs;

import java.nio.ByteBuffer;

public class Tick {
    public long tick;
    public boolean wPressed;
    public boolean sPressed;
    public boolean aPressed;
    public boolean dPressed;
    public boolean enterPressed;
    public boolean spacePressed;

    public Tick(){
        
    }

    public Tick(long tick, boolean wPressed, boolean sPressed, boolean aPressed, boolean dPressed, boolean enterPressed, boolean spacePressed){
        this.tick = tick;
        this.wPressed = wPressed;
        this.sPressed = sPressed;
        this.aPressed = aPressed;
        this.dPressed = dPressed;
        this.enterPressed = enterPressed;
        this.spacePressed = spacePressed;
    }

    public byte[] serialize(){
        int bitmask = 0;

        if(wPressed) bitmask |= 1;
        bitmask <<= 1;
        if(sPressed) bitmask |= 1;
        bitmask <<= 1;
        if(aPressed) bitmask |= 1;
        bitmask <<= 1;
        if(dPressed) bitmask |= 1;
        bitmask <<= 1;
        if(enterPressed) bitmask |= 1;
        bitmask <<= 1;
        if(spacePressed) bitmask |= 1;

        ByteBuffer buffer = ByteBuffer.allocate(12);
        buffer.putLong(tick);
        buffer.putInt(bitmask);
        
        return buffer.array();
    }

    public static Tick deserialize(byte [] arr){
        ByteBuffer buffer = ByteBuffer.wrap(arr);
        long t = buffer.getLong();
        int bitmask = buffer.getInt();

        boolean wPressed = ((bitmask>>5)&1)==1;
        boolean sPressed = ((bitmask>>4)&1)==1;
        boolean aPressed = ((bitmask>>3)&1)==1;
        boolean dPressed = ((bitmask>>2)&1)==1;
        boolean enterPressed = ((bitmask>>1)&1)==1;
        boolean spacePressed = ((bitmask>>0)&1)==1;

        return new Tick(t, wPressed, sPressed, aPressed, dPressed, enterPressed, spacePressed);
    }
}
