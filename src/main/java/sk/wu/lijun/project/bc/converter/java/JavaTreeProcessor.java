package sk.wu.lijun.project.bc.converter.java;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNodeImpl;
import org.antlr.v4.runtime.tree.Tree;
import sk.wu.lijun.project.bc.parser.antlr.java.JavaParser;
import sk.wu.lijun.project.bc.tree.oop.treenodes.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lijun on 2016/4/20.
 */
public class JavaTreeProcessor {
    private static final String CLASS = "class";
    private static final String VOID = "void";
    private Tree tree;
    private AbstractTreeNode root;
    private AbstractTreeNode current;
    public JavaTreeProcessor(Tree tree){
        this.tree = tree;
    }
    AbstractTreeNode getInternalTree(){
        return root;
    }

    public void process() {
        if (tree instanceof JavaParser.CompilationUnitContext) {
            root = new FileNode(null);
        }
        current = root;
        processNode(root, tree);
    }

    private void processNode(AbstractTreeNode internalNode,Tree node) {
        if (node.getChildCount() > 0){
            for (int i=0;i<node.getChildCount();i++) {
                Tree child = node.getChild(i);
                convert(internalNode, child);
            }
        }
    }

    private void convert(AbstractTreeNode internalNode, Tree node) {
        if (node instanceof JavaParser.ImportDeclarationContext){
            //TODO: is import important information for us ?
            return;
        }
        if (node instanceof JavaParser.PackageDeclarationContext){
            //TODO: is package important ?
            return;
        }
        if (node instanceof JavaParser.TypeDeclarationContext){
            //Type declaration should be only interface or class
            for (int i=0; i<node.getChildCount();i++){
                if (node.getChild(i) instanceof JavaParser.ClassDeclarationContext){
                    ClassNode clazz = convertClass((JavaParser.ClassDeclarationContext) node.getChild(i));
                    clazz.setParent(internalNode);
                }else {
                    if (node.getChild(i) instanceof JavaParser.InterfaceDeclarationContext){
                        InterfaceNode iface = convertInterface((JavaParser.InterfaceDeclarationContext) node.getChild(i));
                        iface.setParent(internalNode);
                    }
                }
            }
        }
        if (node instanceof JavaParser.ClassDeclarationContext){
            convertClass((JavaParser.ClassDeclarationContext) node);
        }
    }

    private InterfaceNode convertInterface(JavaParser.InterfaceDeclarationContext node) {

        return null;
    }

    private ClassNode convertClass(JavaParser.ClassDeclarationContext node) {
        int i=0;
        // Skip all until "class" keyword
        while (i<node.getChildCount()){
            if (node.getChild(i) instanceof TerminalNodeImpl) {
                if (((TerminalNodeImpl) node.getChild(0)).getSymbol().getText().equals(CLASS)) {
                    i++;
                    break;
                }
            }
            i++;
        }
        String classname = ((TerminalNodeImpl)node.getChild(i)).getText();
        ClassNode clazz = new ClassNode(classname);
        while (!(node.getChild(i) instanceof JavaParser.ClassBodyContext)) {
            i++;
        }
        fillClassNode(clazz, (JavaParser.ClassBodyContext) node.getChild(i));
        return clazz;
    }

    private void fillClassNode(ClassNode node, JavaParser.ClassBodyContext nodes){
        for (int i = 0; i < nodes.getChildCount(); i++) {
            if (nodes.getChild(i) instanceof TerminalNodeImpl){
                continue;
            }
            if (nodes.getChild(i) instanceof JavaParser.ClassBodyDeclarationContext){
                AbstractTreeNode body = convertClassBodyContext((JavaParser.ClassBodyDeclarationContext) nodes.getChild(i));
                if (body != null) {
                    addMenberToClassNode(node, body);
                }
            }
        }
    }

    private void addMenberToClassNode(ClassNode node, AbstractTreeNode body) {
        body.setParent(node);
        if (body instanceof FieldNode){
            node.addField((FieldNode) body);
        }
        else{
            if (body instanceof MethodNode){
                node.addMethod((MethodNode) body);
            }
        }
    }

    private AbstractTreeNode convertClassBodyContext(JavaParser.ClassBodyDeclarationContext node) {
        AbstractTreeNode result = null;
        List<String> modifiers = new ArrayList<>();

        for (int index = 0; index < node.getChildCount(); index++) {
            if (node.getChild(index) instanceof JavaParser.ModifierContext) {
                modifiers.add(getModifier((JavaParser.ModifierContext) node.getChild(index)));
                index++;
            } else {
                if (node.getChild(index) instanceof JavaParser.BlockContext) {
                    return convertStaticBlock((JavaParser.BlockContext)node.getChild(index));
                } else {
                    AbstractModifiableNode member = null;
                    if (node.getChild(index) instanceof JavaParser.MemberDeclarationContext) {
                        member = getMemberContext((JavaParser.MemberDeclarationContext) node.getChild(index));
                        member.setModifiers(modifiers);
                    }
                }
            }
        }
        return result;
    }

    private AbstractTreeNode convertStaticBlock(JavaParser.BlockContext block) {
        return new AbstractTreeNode("{TODO: blok}");
    }

    // "Member"'s children should be class, interface, field and method
    private AbstractModifiableNode getMemberContext(JavaParser.MemberDeclarationContext node) {
        for (int i = 0; i < node.getChildCount(); i++) {
            ParseTree member = node.getChild(i);
            if (member instanceof JavaParser.FieldDeclarationContext) {
                return convertField((JavaParser.FieldDeclarationContext) member);
            } else {
                if (member instanceof JavaParser.MethodDeclarationContext) {
                    return convertMethod((JavaParser.MethodDeclarationContext) member);
                } else {
                    if (member instanceof JavaParser.ClassDeclarationContext) {
                        return convertClass((JavaParser.ClassDeclarationContext) member);
                    } else {
                        if (member instanceof JavaParser.InterfaceDeclarationContext) {
                            return convertInterface((JavaParser.InterfaceDeclarationContext) member);
                        } else {
                            throw new IllegalStateException("invalid class member: " + this.getClass().getSimpleName());
                        }
                    }
                }
            }
        }
        return null;
    }

    private AbstractModifiableNode convertMethod(JavaParser.MethodDeclarationContext node) {
        MethodNode result = null;
        // modifiers void name(parameters){body}
        List<String> modifiers = new ArrayList<>();
        for (int i=0;i<node.getChildCount();i++) {
            Tree child = node.getChild(i);
            if (child instanceof JavaParser.ModifierContext) {
                modifiers.add(getModifier((JavaParser.ModifierContext) child));
            }
            else{
                if (child instanceof TerminalNodeImpl){
                    if (((TerminalNodeImpl) child).getSymbol().getText().equals(VOID)){
                        i++;
                        TerminalNodeImpl methodName = (TerminalNodeImpl) node.getChild(i);
                        String name = methodName.getText();
                        result = new MethodNode(name);
                        result.setModifiers(modifiers);
                    }
                }
                else {

                }
            }
        }
        return result;
    }

    private AbstractModifiableNode convertField(JavaParser.FieldDeclarationContext node) {
        List<String> modifiers = new ArrayList<>();
        String type = null;
        String name = null;
        for (int i=0;i<node.getChildCount();i++){
            Tree member = node.getChild(i);
            if (member instanceof JavaParser.ModifierContext){
                modifiers.add(getModifier((JavaParser.ModifierContext) member));
            }else{
                if (member instanceof JavaParser.TypeContext){
                    type = ((JavaParser.TypeContext) member).classOrInterfaceType().getText();
                }
                else {
                    if (member instanceof JavaParser.VariableDeclaratorContext){
                        name = ((JavaParser.VariableDeclaratorContext) member).getText();
                    }
                }
            }
        }
        FieldNode field = new FieldNode(name);
        field.setModifiers(modifiers);
        return field;
    }

    private String getModifier(JavaParser.ModifierContext node) {
        return node.getText();
    }
}
