package structs;

import java.nio.ByteBuffer;

public class OutgoingPacket {
    public int player_id;
    public Tick tick1;
    public Tick tick2; 
    public Tick tick3;

    public OutgoingPacket(int player_id, Tick tick1, Tick tick2, Tick tick3){
        this.player_id = player_id;
        this.tick1 = tick1;
        this.tick2 = tick2;
        this.tick3 = tick3;
    }

    public byte[] serialize(){
        ByteBuffer buffer = ByteBuffer.allocate(40);
        buffer.putInt(player_id);
        buffer.put(tick1.serialize());
        buffer.put(tick2.serialize());
        buffer.put(tick3.serialize());

        return buffer.array();
    }

    static public OutgoingPacket deserialize(byte[] arr){
        ByteBuffer buffer = ByteBuffer.wrap(arr);
        int player_id = buffer.getInt();
        byte[] next12 = new byte[12];
        buffer.get(next12);
        Tick tick1 = Tick.deserialize(next12);
        buffer.get(next12);
        Tick tick2 = Tick.deserialize(next12);
        buffer.get(next12);
        Tick tick3 = Tick.deserialize(next12);

        return new OutgoingPacket(player_id, tick1, tick2, tick3);
    }
}
