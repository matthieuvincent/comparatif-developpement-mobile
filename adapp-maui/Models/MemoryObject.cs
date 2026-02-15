namespace AD.APP.Models;

public class MemoryObject
{
    public byte[] Payload;

    public MemoryObject(int payloadSize)
    {
        Payload = new byte[payloadSize];
    }
}

