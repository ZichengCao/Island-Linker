package com.ada.federate.ope;

import java.math.BigInteger;

/**
 * @author Savvas Savvides <savvas@purdue.edu>
 */
public class ValueRange {

    Long start;
    Long end;

    public ValueRange(Long s, Long e) {
        this.start = s;
        this.end = e;

        if (this.start.compareTo(this.end) > 0)
            throw new RuntimeException("start > end");

    }

    /**
     * Copy constructor
     *
     * @param other
     */
    public ValueRange(ValueRange other) {
        this(other.start, other.end);
    }

    /**
     * the range length, including its start and end
     *
     * @return
     */
    public Long size() {
        return this.end - this.start + 1;
    }


    public boolean contains(Long number) {
        if (number >= this.start && number <= this.end) return true;
        return false;
    }

    @Override
    public String toString() {
        return "Range [" + start + ", " + end + ']';
    }
}
