package com.qualitydefectdetector.nlpprocessor;

import org.springframework.stereotype.Service;
import zemberek.morphology.TurkishMorphology;
import zemberek.normalization.TurkishSpellChecker;
import zemberek.tokenization.TurkishSentenceExtractor;
import zemberek.tokenization.TurkishTokenizer;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Service
public class ZemberekProcessor {

    private TurkishMorphology turkishMorphology;
    private TurkishSpellChecker spellChecker;
    private TurkishSentenceExtractor extractor = TurkishSentenceExtractor.DEFAULT;
    private TurkishTokenizer tokenizerDefault = TurkishTokenizer.DEFAULT;
    private TurkishTokenizer tokenizerAll = TurkishTokenizer.ALL;

    public ZemberekProcessor() throws IOException {
        turkishMorphology = TurkishMorphology.createWithDefaults();
        spellChecker = new TurkishSpellChecker(turkishMorphology);
    }

    public List<String> checkSpell(String word) {
        if (!spellChecker.check(word)) {
            return spellChecker.suggestForWord(word);
        }
        return Collections.EMPTY_LIST;
    }
}
