package com.csgi.search.system.model;

import java.util.List;

public class Result {
    private final int mWordCount;
    private final List<String> longWords;
    private final int suffixWordCount;

    public Result(int mWordCount, List<String> longWords, int suffixWordCount) {
        this.mWordCount = mWordCount;
        this.longWords = longWords;
        this.suffixWordCount = suffixWordCount;
    }

    public int getMWordCount() {
        return mWordCount;
    }

    public List<String> getLongWords() {
        return longWords;
    }
    
    public int getSuffixWordCount() {
        return suffixWordCount;
    }
}
