package structs;

import java.nio.ByteBuffer;

public class GameEvent {
    public int actorId;
    public int targetId;
    public int objectType;
    public int worldX;
    public int worldY;
    public int eventType;
    public int direction;
// event:
    // type 1: object nulled
    // type2: object created
    // type 3: entity died
    // type 4: entity created
    // type 5: olman created
    // type 6: greenslime create
    // type 7: fireball create
    // type 8: game won

// object:
    //type 1: key
    // type 2: door
    // type 3: chest
    

    public byte[] serialize(){
        ByteBuffer buffer = ByteBuffer.allocate(28);
        buffer.putInt(actorId);
        buffer.putInt(targetId);
        buffer.putInt(objectType);
        buffer.putInt(worldX);
        buffer.putInt(worldY);
        buffer.putInt(eventType);
        buffer.putInt(direction);

        return buffer.array();
    }

    public static GameEvent deserialize(byte[] arr){
        ByteBuffer buffer = ByteBuffer.wrap(arr);
        GameEvent event = new GameEvent();
        event.actorId = buffer.getInt();
        event.targetId = buffer.getInt();
        event.objectType = buffer.getInt();
        event.worldX = buffer.getInt();
        event.worldY = buffer.getInt();
        event.eventType = buffer.getInt();
        event.direction = buffer.getInt();

        return event;
    }
}
