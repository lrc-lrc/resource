package cn.tedu.storeexe.controller.ex;

/**
 * 文件类型异常
 * @author Administrator
 *
 */
public class FileTypeException extends FileUploadException {

	private static final long serialVersionUID = 1L;

	public FileTypeException() {
		super();
	}

	public FileTypeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public FileTypeException(String message, Throwable cause) {
		super(message, cause);
	}

	public FileTypeException(String message) {
		super(message);
	}

	public FileTypeException(Throwable cause) {
		super(cause);
	}

}
