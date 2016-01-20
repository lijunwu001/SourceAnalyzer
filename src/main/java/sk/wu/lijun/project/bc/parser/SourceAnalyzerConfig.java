package sk.wu.lijun.project.bc.parser;

import sk.wu.lijun.project.bc.enums.SourceLanguage;

/**
 * Created by Lijun on 2016-01-20.
 */
public class SourceAnalyzerConfig {

    private static SourceAnalyzerConfig config;
    private String inputFile;
    private SourceLanguage language;
    private String outputFile;

    private SourceAnalyzerConfig(){

    }

    public static SourceAnalyzerConfig getConfiguration() {
        if (config == null){
            config = new SourceAnalyzerConfig();
        }
        return config;
    }

    public String getInputFile() {
        return inputFile;
    }

    public void setInputFile(String inputFile) {
        this.inputFile = inputFile;
    }

    public SourceLanguage getLanguage() {
        return language;
    }

    public void setLanguage(SourceLanguage language) {
        this.language = language;
    }

    public String getOutputFile() {
        return outputFile;
    }

    public void setOutputFile(String outputFile) {
        this.outputFile = outputFile;
    }
}
