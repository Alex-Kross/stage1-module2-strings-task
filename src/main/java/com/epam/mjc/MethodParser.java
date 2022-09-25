package com.epam.mjc;

import java.util.ArrayList;
import java.util.List;

public class MethodParser {
    private static final String splitterArgAndBeforeArg = "[\\(\\)]";
    private static final String splitterBetweenWords = " ";
    private static final String splitterBetweenArgs = ", ";
    /**
     * Parses string that represents a method signature and stores all it's members into a {@link MethodSignature} object.
     * signatureString is a java-like method signature with following parts:
     *      1. access modifier - optional, followed by space: ' '
     *      2. return type - followed by space: ' '
     *      3. method name
     *      4. arguments - surrounded with braces: '()' and separated by commas: ','
     * Each argument consists of argument type and argument name, separated by space: ' '.
     * Examples:
     *      accessModifier returnType methodName(argumentType1 argumentName1, argumentType2 argumentName2)
     *      private void log(String value)
     *      Vector3 distort(int x, int y, int z, float magnitude)
     *      public DateTime getCurrentDateTime()
     *
     * @param signatureString source string to parse
     * @return {@link MethodSignature} object filled with parsed values from source string
     */
    public MethodSignature parseFunction(String signatureString) {
        StringSplitter splitter = new StringSplitter();
        ArrayList<String> mainDelimiter = new ArrayList<>();
        mainDelimiter.add(splitterArgAndBeforeArg);
        //split arguments and before arguments
        ArrayList<String> listArgAndBeforeArg = (ArrayList<String>) splitter.splitByDelimiters(signatureString, mainDelimiter);
        //if method have args
        List<MethodSignature.Argument> arguments= new ArrayList<>();
        if (listArgAndBeforeArg.size() == 2) {
            String argumentsMethod = listArgAndBeforeArg.get(1);
            ArrayList<String> argsDelimiter = new ArrayList<>();
            argsDelimiter.add(splitterBetweenArgs);
            argsDelimiter.add(splitterBetweenWords);
            //split args on words
            ArrayList<String> listArgs = (ArrayList<String>) splitter.splitByDelimiters(argumentsMethod, argsDelimiter);
            for (int i = 0; i < listArgs.size(); i += 2) {
                arguments.add(new MethodSignature.Argument(listArgs.get(i), listArgs.get(i + 1)));
            }
        }
        //split access modifier, return type and name
        String preArgumentMethod = listArgAndBeforeArg.get(0);
        ArrayList<String> beforeArgsDelimiter = new ArrayList<>();
        beforeArgsDelimiter.add(splitterBetweenWords);
        ArrayList<String> listBeforeArgs = (ArrayList<String>) splitter.splitByDelimiters(preArgumentMethod, beforeArgsDelimiter);

        String methodRetType;
        String methodName;
        MethodSignature methodSignature;
        //if method have access modifier
        if (listBeforeArgs.size() == 3) {
            String methodModAcc = listBeforeArgs.get(0);
            methodRetType = listBeforeArgs.get(1);
            methodName = listBeforeArgs.get(2);
            methodSignature = new MethodSignature(methodName, arguments);
            methodSignature.setAccessModifier(methodModAcc);

        } else {
            methodRetType = listBeforeArgs.get(0);
            methodName = listBeforeArgs.get(1);
            methodSignature = new MethodSignature(methodName, arguments);
        }
        methodSignature.setReturnType(methodRetType);
        return methodSignature;
    }
}
