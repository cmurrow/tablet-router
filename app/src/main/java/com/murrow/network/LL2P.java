package com.murrow.network;

import com.murrow.support.NetworkConstants;
import com.murrow.support.Utilities;

import java.util.Arrays;

/**
 * Created by Corbin Murrow on 1/27/2016.
 */
public class LL2P
{
    private Integer dstAddr;
    private Integer srcAddr;
    private Integer type;
    private byte[] payload;
    private CRC16 crc;

    /* ---- CONSTRUCTORS ---- */

    public LL2P(String dstAddr, String srcAddr, String type, String payload)
    {
        setDstAddr(dstAddr);
        setSrcAddr(srcAddr);
        setType(type);
        setPayload(Utilities.stringToBytes(payload));

        this.crc = new CRC16();
        calculateCRC();
    }

    public LL2P(byte[] data)
    {
        String tmpData = Utilities.bytesToString(data);

        setDstAddr(tmpData.substring(0, 6));
        setSrcAddr(tmpData.substring(6, 12));
        setType(tmpData.substring(12, 16));

        crc = new CRC16();
        setCRC(tmpData.substring(16,20));

        setPayload(Utilities.stringToBytes(tmpData.substring((20))));
    }

    public LL2P()
    {
        setDstAddr("000000");
        setSrcAddr("000000");
        setType("0000");
        setPayload(Utilities.stringToBytes("0"));

        this.crc = new CRC16();
        calculateCRC();
    }

    /* ---- SETTERS ---- */

    void setDstAddr(String val)
    {
        dstAddr = Integer.valueOf(val, 16);
    }

    public void setSrcAddr(String val)
    {
        srcAddr = Integer.valueOf(val, 16);
    }

    public void setType(String val)
    {
        type = Integer.valueOf(val, 16);
    }

    public void setDstAddr(int val)
    {
        dstAddr = val;
    }

    public void setSrcAddr(int val)
    {
        srcAddr = val;
    }

    public void setType(int val)
    {
        type = val;
    }

    public void setPayload(byte[] arr)
    {
        payload = arr;
    }

    public void setCRC(String val)
    {
        crc.setCRC(Integer.valueOf(val,16));
    }

    /* ---- GETTERS ---- */
    public String getDstAddrHexString()
    {
        return Utilities.padHex(Integer.toHexString(dstAddr), NetworkConstants.LL2P_ADDR_LENGTH * 2);
    }

    public String getSrcAddrHexString()
    {
        return Utilities.padHex(Integer.toHexString(srcAddr), NetworkConstants.LL2P_ADDR_LENGTH * 2);
    }

    public String getTypeHexString()
    {
        return Utilities.padHex(Integer.toHexString(type), NetworkConstants.LL2P_TYPE_LENGTH * 2);
    }

    public String getCRCHexString()
    {
        return Utilities.padHex(crc.getCRCHexString(), NetworkConstants.CRC_LENGTH * 2);
    }

    public int getDstAddr()
    {
        return dstAddr;
    }

    public int getSrcAddr()
    {
        return srcAddr;
    }

    public int getType()
    {
        return type;
    }

    public int getCRC()
    {
        return crc.getCRC();
    }

    public byte[] getPayloadBytes()
    {
        return payload;
    }

    public String getPayloadString()
    {
        return Utilities.bytesToString(payload);
    }

    /* ---- PRIVATE METHODS ---- */
    private void calculateCRC()
    {
        crc.update(this.toString().getBytes());
    }

    //Todo: implement getFrameBytes
    private byte[] getFrameBytes()
    {
        return Utilities.stringToBytes(this.toString() + getCRCHexString());
    }

    /* ---- OVERRIDES ---- */

    @Override
    public String toString()
    {
        return getDstAddrHexString() + getSrcAddrHexString() + getTypeHexString() + getPayloadString();
    }
}
