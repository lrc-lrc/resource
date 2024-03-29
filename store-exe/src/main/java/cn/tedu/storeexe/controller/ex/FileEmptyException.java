package cn.tedu.storeexe.controller.ex;

/**
 * 上传文件为空异常
 * @author Administrator
 *
 */
public class FileEmptyException extends FileUploadException {

	private static final long serialVersionUID = 1L;

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
