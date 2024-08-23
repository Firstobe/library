package org.example.librarysystem.utils;

import lombok.Data;

import java.io.Serializable;

@Data
public class Result<T> implements Serializable {
	private int code;
	private String msg;
	private T data;
	public static <T> Result<T> succ(T data) {
		return succ(0, "操作成功", data);
	}

	public static Result<Object> fail(String msg) {
		return fail(1, msg, null);
	}

	public static Result<Object> fail(int code, String msg) {
		return fail(code, msg, null);
	}

	public static <T> Result<T> fail(ErrorCode errorCode) {
		return fail(errorCode.getCode(), errorCode.getMsg(), null);
	}

	public static <T> Result<T> succ (int code, String msg, T data) {
		Result<T> result = new Result<>();
		result.setCode(code);
		result.setMsg(msg);
		result.setData(data);
		return result;
	}
	public static <T> Result<T> succ (String msg, T data) {
		Result<T> result = new Result<>();
		result.setCode(0);
		result.setMsg(msg);
		result.setData(data);
		return result;
	}
	public static <T> Result<T> fail (int code, String msg, T data) {
		Result<T> result = new Result<>();
		result.setCode(code);
		result.setMsg(msg);
		result.setData(data);
		return result;
	}

}
