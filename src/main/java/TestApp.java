import org.antlr.v4.runtime.tree.Tree;
import sk.wu.lijun.project.bc.SourceAnalyzer;
import sk.wu.lijun.project.bc.converter.java.JavaTreeConverter;
import sk.wu.lijun.project.bc.enums.SourceLanguage;
import sk.wu.lijun.project.bc.log.Logger;
import sk.wu.lijun.project.bc.parser.SourceAnalyzerConfig;
import sk.wu.lijun.project.bc.persistence.PersistenceUnit;
import sk.wu.lijun.project.bc.tree.oop.treenodes.AbstractTreeNode;

/**
 * Created by Lijun on 2015-12-19.
 */
public class TestApp {
    public static void main(String[] args) {
        try {
            Logger logger = Logger.getLogger();
            logger.setLogFile("E:\\Temp\\log.txt");
            SourceAnalyzerConfig config = SourceAnalyzerConfig.getConfiguration();
            config.setInputFile("E:\\Temp\\Test.java");
            config.setLanguage(SourceLanguage.JAVA);
            SourceAnalyzer analyzer = new SourceAnalyzer();
            Tree tree = analyzer.parse();
            AbstractTreeNode node = new JavaTreeConverter().convert(tree);
            new PersistenceUnit().save(node);
            System.out.println(tree.toStringTree());

            config.setInputFile("E:\\Temp\\Test.cpp");
            config.setLanguage(SourceLanguage.CPP);
            analyzer = new SourceAnalyzer();
            tree = analyzer.parse();
            new JavaTreeConverter().convert(tree);
            System.out.println(tree.toStringTree());

            config.setLanguage(SourceLanguage.PHP);
            analyzer = new SourceAnalyzer();
            tree = analyzer.parse();
            System.out.println(tree.toStringTree());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}