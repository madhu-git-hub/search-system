package com.csgi.search.system.indexer;

import java.util.List;

import com.csgi.search.system.model.Result;

/**
 * Interface for processing words based on specific rules.
 */
public interface RuleProcessor {
    Result process(List<String> words);
    public int countWordsStartingWith(List<String> words);
    public List<String> getWordsLongerThan(List<String> words);
    //Rule3 will be added in the future
    public int countWordsEndingWith(List<String> words);
}