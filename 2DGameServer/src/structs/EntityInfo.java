package structs;

import java.nio.ByteBuffer;

public class EntityInfo {
    public int id;
    public int worldX;
    public int worldY;
    public int direction; // 1:up 2:down 3:left 4:right
    public int life;
    public boolean invincibleOn = false;
    public boolean collisionOn = false;
    public int collidingEntityId;
    public boolean attacking = false;
    public int running;// 1-> not running 2-> running 
    public int keysCount;

    public byte[] serialize(){
        ByteBuffer buffer = ByteBuffer.allocate(36);
        int bitmask = 0;

        if(invincibleOn) bitmask |= 1;
        bitmask <<= 1;
        if(collisionOn) bitmask |= 1;
        bitmask <<= 1;
        if(attacking) bitmask |= 1;

        buffer.putInt(id);
        buffer.putInt(worldX);
        buffer.putInt(worldY);
        buffer.putInt(direction);
        buffer.putInt(life);
        buffer.putInt(collidingEntityId);
        buffer.putInt(running); // 1-> not running 2-> running 
        buffer.putInt(keysCount);
        buffer.putInt(bitmask);

        return buffer.array();
    }

    static public EntityInfo deserialize(byte[] arr){
        ByteBuffer buffer = ByteBuffer.wrap(arr);
        EntityInfo info = new EntityInfo();
        info.id = buffer.getInt();
        info.worldX = buffer.getInt();
        info.worldY = buffer.getInt();
        info.direction = buffer.getInt();
        info.life = buffer.getInt();
        info.collidingEntityId = buffer.getInt();
        info.running = buffer.getInt();
        info.keysCount = buffer.getInt();
        int bitmask = buffer.getInt();

        info.invincibleOn = ((bitmask>>2)&1)==1;
        info.collisionOn = ((bitmask>>1)&1)==1;
        info.attacking = ((bitmask>>0)&1)==1;

        return info;
    }
}
