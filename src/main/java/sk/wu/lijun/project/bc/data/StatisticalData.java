package sk.wu.lijun.project.bc.data;

import sk.wu.lijun.project.bc.tree.oop.treenodes.AbstractTreeNode;
import sk.wu.lijun.project.bc.tree.oop.treenodes.ClassNode;
import sk.wu.lijun.project.bc.tree.oop.treenodes.MethodNode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lijun on 2016/4/20.
 */
public class StatisticalData {
    private List<ClassNode> classes;
    private List<MethodNode> methods;
    private static StatisticalData instance;
    private StatisticalData(){
        classes = new ArrayList<>();
        methods = new ArrayList<>();
    }
    public static StatisticalData getStatisticData(){
        if (instance == null){
            instance = new StatisticalData();
        }
        return instance;
    }


    public List<ClassNode> getClasses() {
        return classes;
    }

    public void setClasses(List<ClassNode> classes) {
        this.classes = classes;
    }

    public void addClass(ClassNode clazz){
        classes.add(clazz);
    }

    public List<MethodNode> getMethods() {
        return methods;
    }

    public void setMethods(List<MethodNode> methods) {
        this.methods = methods;
    }

    public void addMethod(MethodNode method){
        methods.add(method);
    }
}
