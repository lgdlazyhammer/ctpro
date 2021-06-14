package com.lgd.ctpro.serilizeExecutor;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtobufIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
/*
 * 序列化和反序列号工具类
 */
public class SerializeUtils {
	
	public static <T> byte[] serialize(T t, Class<T> clazz) {
		return ProtobufIOUtil.toByteArray(t, RuntimeSchema.createFrom(clazz),
				LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
	}

	public static <T> T deSerialize(byte[] data, Class<T> clazz) {
		RuntimeSchema<T> runtimeSchema = RuntimeSchema.createFrom(clazz);
		T t = runtimeSchema.newMessage();
		ProtobufIOUtil.mergeFrom(data, t, runtimeSchema);
		return t;
	}

}
