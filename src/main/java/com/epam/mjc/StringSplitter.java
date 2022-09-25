package com.epam.mjc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class StringSplitter {

    /**
     * Splits given string applying all delimiters to it. Keeps order of result substrings as in source string.
     *
     * @param source source string
     * @param delimiters collection of delimiter strings
     * @return List of substrings
     */
    public List<String> splitByDelimiters(String source, Collection<String> delimiters) {
        ArrayList<String> listElOfSource = new ArrayList<>();
        listElOfSource.add(source);
        for (String delimiter : delimiters) {
            ArrayList<String> newListElOfSource = new ArrayList<>();
            for (String element : listElOfSource){
                String[] split = element.split(delimiter);
                Collections.addAll(newListElOfSource, split);
            }
            listElOfSource = newListElOfSource;
        }
        //delete empty elements
        while(listElOfSource.remove(""));
        return listElOfSource;
    }
}
