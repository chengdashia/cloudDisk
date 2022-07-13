package com.example.cloudDisk.utils.reflect;


import java.io.Serializable;
import java.util.function.Function;

/**
 * @author 成大事
 * @since 2022/7/13 10:11
 */
public interface PropertyFunc<T,R> extends Function<T,R>, Serializable {
}
