package sk.wu.lijun.project.bc.tree.oop.treenodes;

import java.io.Serializable;

/**
 * Created by Lijun on 2016/4/20.
 */
public interface Persistence extends Serializable {
    void save();
    Object load(Long id);
}
