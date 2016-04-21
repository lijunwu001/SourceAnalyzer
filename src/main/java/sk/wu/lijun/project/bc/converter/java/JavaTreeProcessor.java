package sk.wu.lijun.project.bc.converter.java;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNodeImpl;
import org.antlr.v4.runtime.tree.Tree;
import sk.wu.lijun.project.bc.parser.antlr.java.JavaParser;
import sk.wu.lijun.project.bc.tree.oop.FormalParameter;
import sk.wu.lijun.project.bc.tree.oop.treenodes.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lijun on 2016/4/20.
 */
public class JavaTreeProcessor {
    private static final String CLASS = "class";
    private static final String VOID = "void";
    private static final String INTERFACE = "interface";
    private static final String EXTENDS = "extends";
    private static final String IMPLEMENTS = "implements";

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

        for (int i = 0; i < node.getChildCount(); i++) {
            Tree child = node.getChild(i);
            convert(internalNode, child);
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
            List<String> modifiers = new ArrayList<>();
            ClassNode clazz = null;
            for (int i=0; i<node.getChildCount();i++){
                if (node.getChild(i) instanceof JavaParser.ClassDeclarationContext){
                    clazz = convertClass((JavaParser.ClassDeclarationContext) node.getChild(i));
                    clazz.setParent(internalNode);
                    if (internalNode instanceof FileNode){
                        internalNode.addChild(clazz);
                    }
                }else {
                    if (node.getChild(i) instanceof JavaParser.InterfaceDeclarationContext){
                        InterfaceNode iface = convertInterface((JavaParser.InterfaceDeclarationContext) node.getChild(i));
                        iface.setParent(internalNode);
                    }else{
                        if (node.getChild(i) instanceof JavaParser.ClassOrInterfaceModifierContext){
                            modifiers.add(getClassOrInterfaceModifier((JavaParser.ClassOrInterfaceModifierContext) node.getChild(i)));
                        }
                    }
                }
            }
            if (clazz != null){
                clazz.setModifiers(modifiers);
            }
        }
    }

    private InterfaceNode convertInterface(JavaParser.InterfaceDeclarationContext node) {
        String name = null;
        InterfaceNode result = null;
        for (int i=0;i<node.getChildCount();i++){
            if (node.getChild(i) instanceof TerminalNodeImpl){
                if (((TerminalNodeImpl)node.getChild(i)).getText().equals(INTERFACE)){
                    i++;
                    ParseTree nameNode = node.getChild(i);
                    if (!(nameNode instanceof TerminalNodeImpl)){
                        throw new IllegalStateException("Invalid format of input java code: "+this.getClass().getName());
                    }
                    name = nameNode.getText();
                    result = new InterfaceNode(name);
                }
            }else{
                if (node.getChild(i) instanceof JavaParser.InterfaceBodyContext){
                    if (result == null) throw new IllegalStateException("Invalid format of input java code: "+this.getClass().getName());
                    fillInterface(result, node.getChild(i));
                }
            }
        }
        return null;
    }

    private void fillInterface(InterfaceNode result, ParseTree child) {

    }

    private ClassNode convertClass(JavaParser.ClassDeclarationContext node) {
        String className = null;
        ClassNode clazz = null;
        List<String> modifiers = new ArrayList<>();
        String extds = null;
        List<String> ifs = new ArrayList<>();

        for (int i = 0; i < node.getChildCount(); i++) {
            if (node.getChild(i) instanceof TerminalNodeImpl) {
                String keyword = ((TerminalNodeImpl) node.getChild(i)).getSymbol().getText();
                if (keyword.equals(CLASS)) {
                    i++;
                    TerminalNodeImpl name = (TerminalNodeImpl) node.getChild(i);
                    className = name.getText();
                    clazz = new ClassNode(className);
                }else{
                    if (keyword.equals(EXTENDS)){
                        i++;
                        JavaParser.TypeContext type = (JavaParser.TypeContext) node.getChild(i);
                        extds = type.getText();
                    }else{
                        if (keyword.equals(IMPLEMENTS)){
                            i++;
                            JavaParser.TypeListContext interfaces = (JavaParser.TypeListContext) node.getChild(i);
                            ifs = getInterfaces(interfaces);
                        }
                    }
                }
            } else {
                if (node.getChild(i) instanceof JavaParser.ClassBodyContext) {
                    fillClassNode(clazz, (JavaParser.ClassBodyContext) node.getChild(i));
                } else {
                }
            }
        }
        clazz.setIfaces(ifs);
        clazz.setExtendsClass(extds);
        return clazz;
    }

    private List<String> getInterfaces(JavaParser.TypeListContext interfaces) {
        List<String> result = new ArrayList<>();
        for (int i=0;i<interfaces.getChildCount();i++){
            if (interfaces.getChild(i) instanceof JavaParser.TypeContext){
                result.add(interfaces.getChild(i).getText());
            }
        }
        return result;
    }

    private void fillClassNode(ClassNode node, JavaParser.ClassBodyContext nodes){
        for (int i = 0; i < nodes.getChildCount(); i++) {
            if (nodes.getChild(i) instanceof TerminalNodeImpl){
                continue;
            }
            if (nodes.getChild(i) instanceof JavaParser.ClassBodyDeclarationContext){
                AbstractTreeNode body = convertClassBodyContext(node, (JavaParser.ClassBodyDeclarationContext) nodes.getChild(i));
            }
        }
    }

    private void addMenberToClassNode(ClassNode node, AbstractTreeNode body) {
//        body.setParent(node);
//        if (body instanceof FieldNode){
//            node.addField((FieldNode) body);
//        }
//        else{
            if (body instanceof MethodNode){
                node.addMethod((MethodNode) body);
            }
//        }
    }

    private AbstractTreeNode convertClassBodyContext(ClassNode classNode, JavaParser.ClassBodyDeclarationContext node) {
        AbstractModifiableNode result = null;
        List<String> modifiers = new ArrayList<>();

        for (int index = 0; index < node.getChildCount(); index++) {
            if (node.getChild(index) instanceof JavaParser.ModifierContext) {
                modifiers.add(getModifier((JavaParser.ModifierContext) node.getChild(index)));
            } else {
                if (node.getChild(index) instanceof JavaParser.BlockContext) {
                    return convertStaticBlock((JavaParser.BlockContext)node.getChild(index));
                } else {
                    if (node.getChild(index) instanceof JavaParser.MemberDeclarationContext) {
                        result = getMemberContext(classNode, (JavaParser.MemberDeclarationContext) node.getChild(index));
                        result.setModifiers(modifiers);
                        addMenberToClassNode(classNode, result);
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
    private AbstractModifiableNode getMemberContext(AbstractTreeNode parent, JavaParser.MemberDeclarationContext node) {
        for (int i = 0; i < node.getChildCount(); i++) {
            ParseTree member = node.getChild(i);
            if (member instanceof JavaParser.FieldDeclarationContext) {
                return convertField((ClassNode) parent, (JavaParser.FieldDeclarationContext) member);
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
                    if (child instanceof JavaParser.TypeContext){
                        String type = ((JavaParser.TypeContext) child).getText();
                        i++;
                        TerminalNodeImpl methodName = (TerminalNodeImpl) node.getChild(i);
                        String name = methodName.getText();
                        result = new MethodNode(name);
                        result.setModifiers(modifiers);
                        result.setReturnType(type);
                    }else{
                        if (child instanceof JavaParser.FormalParametersContext){
                            List<FormalParameter> parameters = getFormarParameter((JavaParser.FormalParametersContext) child);
                            result.setFormalParameters(parameters);
                        }
                    }
                }
            }
        }
        return result;
    }

    private List<FormalParameter> getFormarParameter(JavaParser.FormalParametersContext node) {
        List<FormalParameter> result = new ArrayList<>();
        for (int i=0;i<node.getChildCount();i++){
            Tree child = node.getChild(i);
            if (child instanceof JavaParser.FormalParameterListContext){
                for (int j = 0;i<child.getChildCount();i++){
                    if (child.getChild(j) instanceof JavaParser.FormalParameterContext) {
                        FormalParameter formalParameter = getSingleFormalParameter((JavaParser.FormalParameterContext)child.getChild(j));
                        result.add(formalParameter);
                    }
                }
            }
        }
        return result;
    }

    private FormalParameter getSingleFormalParameter(JavaParser.FormalParameterContext node) {
        String type = null;
        String name = null;
        for (int i=0;i<node.getChildCount();i++) {
            if (node.getChild(i) instanceof JavaParser.TypeContext){
                type = getType((JavaParser.TypeContext)node.getChild(i));
            }else{
                if (node.getChild(i) instanceof JavaParser.VariableDeclaratorIdContext){
                   name =  node.getChild(i).getText();
                }
            }
        }
        FormalParameter param = new FormalParameter();
        param.setName(name);
        param.setType(type);
        return param;
    }

    private String getType(JavaParser.TypeContext node) {
        return node.getText();
    }

    private AbstractModifiableNode convertField(ClassNode parent, JavaParser.FieldDeclarationContext node) {
        List<String> modifiers = new ArrayList<>();
        String type = null;
        String name = null;
        for (int i=0;i<node.getChildCount();i++){
            Tree member = node.getChild(i);
            if (member instanceof JavaParser.ModifierContext){
                modifiers.add(getModifier((JavaParser.ModifierContext) member));
            }else{
                if (member instanceof JavaParser.TypeContext){
                    JavaParser.TypeContext tp = (JavaParser.TypeContext) member;
                    if (tp.classOrInterfaceType()!=null) {
                        type = tp.classOrInterfaceType().getText();
                    }else{
                        type = tp.primitiveType().getText();
                    }
                }
                else {
                    if (member instanceof JavaParser.VariableDeclaratorsContext){
                        for (int j =0;j<member.getChildCount();j++) {
                            Tree child = member.getChild(j);
                            if (child instanceof JavaParser.VariableDeclaratorContext) {
                                name = ((JavaParser.VariableDeclaratorsContext) member).getText();
                                FieldNode fieldNode = new FieldNode(name);
                                fieldNode.setModifiers(modifiers);
                                fieldNode.setDataType(type);
                                fieldNode.setParent(parent);
                                parent.addField(fieldNode);
                            }
                        }
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

    private String getClassOrInterfaceModifier(JavaParser.ClassOrInterfaceModifierContext node){
        return node.getChild(0).getText();
    }
}
