package sk.wu.lijun.project.bc.converter.java;

import sk.wu.lijun.project.bc.converter.exceptions.UnsupportNodeException;
import sk.wu.lijun.project.bc.parser.antlr.java.JavaParser;
import sk.wu.lijun.project.bc.tree.oop.AbstractTreeNode;

/**
 * Created by Lijun on 2016-01-20.
 */
public class JavaTreeConverterUtils {
    private JavaTreeConverterUtils(){

    }

    public static AbstractTreeNode convertPayload(Object payload) throws UnsupportNodeException {
        if (payload instanceof JavaParser.ImportDeclarationContext){
            return convertImport(payload);
        }
        if (payload instanceof JavaParser.CompilationUnitContext){
            return convertCompilationUnit(payload);
        }
        throw new UnsupportNodeException(payload.getClass());
    }



    private static AbstractTreeNode convertImport(Object payload){
        JavaParser.ImportDeclarationContext importContext = (JavaParser.ImportDeclarationContext) payload;
        String text = importContext.getText();
        return new AbstractTreeNode(text);
    }

    private static AbstractTreeNode convertClass(Object payload){
        return null;
    }

    private static AbstractTreeNode convertMethod(Object payload){
        return null;
    }

    private static AbstractTreeNode convertCompilationUnit(Object payload) {
        return new AbstractTreeNode(null);
    }
}
