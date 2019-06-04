package com.deyi.clock.config.shiro;

import org.apache.shiro.codec.Base64;
import org.apache.shiro.codec.CodecSupport;
import org.apache.shiro.codec.Hex;

import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Arrays;

/**
 * @program: xiaowantong
 * @Date: 2019/3/24 11:29
 * @Author: lyz
 * @Description:
 */
public class ByteSource implements org.apache.shiro.util.ByteSource, Serializable {
    private byte[] bytes;
    private String cachedHex;
    private String cachedBase64;

    public ByteSource() {
    }

    public ByteSource(byte[] bytes) {
        this.bytes = bytes;
    }


    public ByteSource(char[] chars) {
        this.bytes = CodecSupport.toBytes(chars);
    }


    public ByteSource(String string) {
        this.bytes = CodecSupport.toBytes(string);
    }


    public ByteSource(org.apache.shiro.util.ByteSource source) {
        this.bytes = source.getBytes();
    }


    public ByteSource(File file) {
        this.bytes = new ByteSource.BytesHelper().getBytes(file);
    }


    public ByteSource(InputStream stream) {
        this.bytes = new ByteSource.BytesHelper().getBytes(stream);
    }

    public static boolean isCompatible(Object o) {
        return o instanceof byte[] || o instanceof char[] || o instanceof String ||
                o instanceof org.apache.shiro.util.ByteSource || o instanceof File || o instanceof InputStream;
    }

    @Override
    public byte[] getBytes() {
        return this.bytes;
    }

    @Override
    public boolean isEmpty() {
        return this.bytes == null || this.bytes.length == 0;
    }

    @Override
    public String toHex() {
        if ( this.cachedHex == null ) {
            this.cachedHex = Hex.encodeToString(getBytes());
        }
        return this.cachedHex;
    }

    @Override
    public String toBase64() {
        if ( this.cachedBase64 == null ) {
            this.cachedBase64 = Base64.encodeToString(getBytes());
        }
        return this.cachedBase64;
    }

    @Override
    public String toString() {
        return toBase64();
    }

    @Override
    public int hashCode() {
        if (this.bytes == null || this.bytes.length == 0) {
            return 0;
        }
        return Arrays.hashCode(this.bytes);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof org.apache.shiro.util.ByteSource) {
            org.apache.shiro.util.ByteSource bs = (org.apache.shiro.util.ByteSource) o;
            return Arrays.equals(getBytes(), bs.getBytes());
        }
        return false;
    }

    //will probably be removed in Shiro 2.0.  See SHIRO-203:
    //https://issues.apache.org/jira/browse/SHIRO-203
    private static final class BytesHelper extends CodecSupport {

        /**
         * 嵌套类也需要提供无参构造器
         */
        private BytesHelper() {
        }

        public byte[] getBytes(File file) {
            return toBytes(file);
        }

        public byte[] getBytes(InputStream stream) {
            return toBytes(stream);
        }
    }
}