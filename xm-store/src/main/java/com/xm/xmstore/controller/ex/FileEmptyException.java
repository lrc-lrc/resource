package com.xm.xmstore.controller.ex;

/**
 * 上传的文件为空的异常，可能是没有选择文件，或选择的文件是0字节
 */
public class FileEmptyException extends FileUploadException{

	private static final long serialVersionUID = 3508868707141311645L;

	public FileEmptyException() {
		super();
	}

	public FileEmptyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public FileEmptyException(String message, Throwable cause) {
		super(message, cause);
	}

	public FileEmptyException(String message) {
		super(message);
	}

	public FileEmptyException(Throwable cause) {
		super(cause);
	}

}
