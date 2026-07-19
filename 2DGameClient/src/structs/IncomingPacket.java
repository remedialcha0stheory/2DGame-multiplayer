package structs;

import java.nio.ByteBuffer;

public class IncomingPacket {
    public long tick;
    public int numEntities;
    public EntityInfo[] entity;

    public byte[] serialize(){
        ByteBuffer buffer = ByteBuffer.allocate(8+4+numEntities*36);
        buffer.putLong(tick);
        buffer.putInt(numEntities);
        for(EntityInfo info: entity){
            buffer.put(info.serialize());
        }

        return buffer.array();
    }

    static public IncomingPacket deserialize(byte[] arr){
        ByteBuffer buffer = ByteBuffer.wrap(arr);
        IncomingPacket packet = new IncomingPacket();
        packet.tick = buffer.getLong();
        packet.numEntities = buffer.getInt();
        packet.entity = new EntityInfo[packet.numEntities];
        byte[] next36 = new byte[36];
        for(int i=0; i<packet.numEntities; i++){
            buffer.get(next36);
            packet.entity[i] = EntityInfo.deserialize(next36);
        }

        return packet;
    }
}
