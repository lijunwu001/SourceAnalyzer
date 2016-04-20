package sk.wu.lijun.project.bc.persistence;

import sk.wu.lijun.project.bc.tree.oop.treenodes.IPersistenceObject;

/**
 * Created by Lijun on 2016/4/20.
 */
public class PersistenceUnit {
    public PersistenceUnit(){

    }
    public void save(IPersistenceObject object){
        HibernateUtils.save(object);
    }
}
