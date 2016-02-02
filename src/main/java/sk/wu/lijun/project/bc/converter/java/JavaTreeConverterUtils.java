package sk.wu.lijun.project.bc.converter.java;

import sk.wu.lijun.project.bc.converter.exceptions.UnsupportNodeException;
import sk.wu.lijun.project.bc.parser.antlr.java.JavaParser;
import sk.wu.lijun.project.bc.tree.oop.treenodes.AbstractTreeNode;
import sk.wu.lijun.project.bc.tree.oop.treenodes.ClassNode;

/**
 * Created by Lijun on 2016-01-20.
 */
public class JavaTreeConverterUtils {
    private JavaTreeConverterUtils(){

    }

    public static AbstractTreeNode convertAntlrTreeNode(Object node) throws UnsupportNodeException {
        if (node instanceof JavaParser.ImportDeclarationContext){
            return convertImport((JavaParser.ImportDeclarationContext) node);
        }
        if (node instanceof JavaParser.ClassDeclarationContext){
            return convertClass((JavaParser.ClassDeclarationContext) node);
        }
        if (node instanceof JavaParser.MethodDeclarationContext){
            return convertMethod((JavaParser.MethodDeclarationContext) node);
        }

        if (node instanceof JavaParser.CompilationUnitContext){
            return convertCompilationUnit(node);
        }

        throw new UnsupportNodeException(node.getClass());
    }


    /**
     *
     * @param payload
     * @return
     */
    private static AbstractTreeNode convertImport(JavaParser.ImportDeclarationContext payload){
        String text = payload.getText();
        return new AbstractTreeNode(text);
    }

    private static AbstractTreeNode convertClass(JavaParser.ClassDeclarationContext payload){
        return new ClassNode(payload.getText());
    }

    private static AbstractTreeNode convertMethod(JavaParser.MethodDeclarationContext payload){
        return null;
    }

    private static AbstractTreeNode convertCompilationUnit(Object payload) {
        return new AbstractTreeNode(null);
    }
}
