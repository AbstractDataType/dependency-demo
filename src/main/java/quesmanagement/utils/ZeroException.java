package quesmanagement.utils;

public class ZeroException extends RuntimeException{
    public ZeroException() {
    }
    public ZeroException(String message) {
        super(message);
    }
}
