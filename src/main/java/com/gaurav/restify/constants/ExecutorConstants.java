package com.gaurav.restify.constants;

public enum ExecutorConstants {
    BASH("sh"),
    PYTHON("python"),
    JAVA("java"),
    BATCH("cmd"),
    EXEC("./");

    private String shellName;

    ExecutorConstants(String shellName) {
        this.shellName = shellName;
    }

    @Override
    public String toString() {
        return this.shellName;
    }
}
