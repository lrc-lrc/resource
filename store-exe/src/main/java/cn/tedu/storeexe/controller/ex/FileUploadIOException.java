package cn.tedu.storeexe.controller.ex;

/**
 * 文件上传IO异常
 * @author Administrator
 *
 */
public class FileUploadIOException extends FileUploadException {

	private static final long serialVersionUID = 1L;

	public FileUploadIOException() {
		super();
	}

	public FileUploadIOException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public FileUploadIOException(String message, Throwable cause) {
		super(message, cause);
	}

	public FileUploadIOException(String message) {
		super(message);
	}

	public FileUploadIOException(Throwable cause) {
		super(cause);
	}

}
