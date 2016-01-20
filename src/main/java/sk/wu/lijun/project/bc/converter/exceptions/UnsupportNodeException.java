package sk.wu.lijun.project.bc.converter.exceptions;

/**
 * Created by Lijun on 2016-01-20.
 */
public class UnsupportNodeException extends Exception {
    private Class clazz;

    public UnsupportNodeException(Class clazz) {
        this.clazz = clazz;
    }

    @Override
    public String getMessage() {
        return "Unsupported tree node: " + clazz.getSimpleName();
    }
}
