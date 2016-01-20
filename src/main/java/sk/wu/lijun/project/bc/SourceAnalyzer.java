package sk.wu.lijun.project.bc;

import org.antlr.v4.runtime.tree.Tree;
import sk.wu.lijun.project.bc.enums.SourceLanguage;
import sk.wu.lijun.project.bc.log.Logger;
import sk.wu.lijun.project.bc.parser.*;

import java.io.FileInputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Lijun on 2015-10-04.
 */
public class SourceAnalyzer {
    private final SourceAnalyzerConfig config;
    private Parser parser;

    public SourceAnalyzer(SourceAnalyzerConfig config){
        this.config = config;
    }

    public Tree parse(){
        try {
            parser = ParserHolder.getParser(config.getLanguage());
            return parser.parse(new FileInputStream(config.getInputFile()));
        }
        catch (Exception ex){
            Logger.getLogger().error(ex.getMessage());
        }
        return null;
    }
}

class ParserHolder{
    private static Map<SourceLanguage, Class> parsers;

    static {
        // Initialize all parsers for all supported languages
        parsers = new HashMap<>();
        parsers.put(SourceLanguage.JAVA, JavaTreeParser.class);
        parsers.put(SourceLanguage.CPP, CppTreeParser.class);
        parsers.put(SourceLanguage.PHP, PHPTreeParser.class);
    }

    public static Map<SourceLanguage, Class> getParsers() {
        return parsers;
    }

    public static Parser getParser(SourceLanguage lang) {
        Class clazz = parsers.get(lang);
        Parser parser = null;
        try {
            Method method = clazz.getMethod("getInstance", null);
            parser = (Parser) method.invoke(null, new Object[0]);
        } catch (NoSuchMethodException e) {
            Logger.getLogger().error(e.getMessage());
        } catch (IllegalAccessException e) {
            Logger.getLogger().error(e.getMessage());
        } catch (InvocationTargetException e) {
            Logger.getLogger().error(e.getMessage());
        }
        return parser;
    }
}