package com.wxy.subject.common.utils;

import lombok.Getter;

public class IdWorkerUtil {

    @Getter
    private final long workerId;

    @Getter
    private final long datacenterId;

    private long sequence;

    private final long workerIdBits = 5L;

    private final long datacenterIdBits = 5L;

    private long lastTimestamp = -1L;

    public long getTimestamp() {
        return System.currentTimeMillis();
    }

    public IdWorkerUtil(long workerId, long datacenterId, long sequence) {
        long maxWorkerId = ~(-1L << workerIdBits);
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(
                    String.format("worker Id can't be greater than %d or less than 0", maxWorkerId));
        }
        long maxDatacenterId = ~(-1L << datacenterIdBits);
        if (datacenterId > maxDatacenterId || datacenterId < 0) {

            throw new IllegalArgumentException(
                    String.format("datacenter Id can't be greater than %d or less than 0", maxDatacenterId));
        }
        this.workerId = workerId;
        this.datacenterId = datacenterId;
        this.sequence = sequence;
    }

    public synchronized long nextId() {
        long timestamp = timeGen();
        if (timestamp < lastTimestamp) {
            System.err.printf(
                    "clock is moving backwards. Rejecting requests until %d.", lastTimestamp);
            throw new RuntimeException(
                    String.format("Clock moved backwards. Refusing to generate id for %d milliseconds",
                            lastTimestamp - timestamp));
        }

        long sequenceBits = 12L;
        if (lastTimestamp == timestamp) {

            long sequenceMask = ~(-1L << sequenceBits);
            sequence = (sequence + 1) & sequenceMask;
            if (sequence == 0) {
                timestamp = tilNextMillis(lastTimestamp);
            }

        } else {
            sequence = 0;
        }
        lastTimestamp = timestamp;
        long twepoch = 1585644268888L;
        long datacenterIdShift = sequenceBits + workerIdBits;
        long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;
        return ((timestamp - twepoch) << timestampLeftShift) |
                (datacenterId << datacenterIdShift) |
                (workerId << sequenceBits) | sequence;
    }

    private long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    //获取当前时间戳
    private long timeGen() {
        return System.currentTimeMillis();
    }

}