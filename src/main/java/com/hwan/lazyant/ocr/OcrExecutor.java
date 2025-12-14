package com.hwan.lazyant.ocr;

import java.io.File;
import java.util.List;

public interface OcrExecutor {
    List<TradingWordsGroup> extractTradings(File imageFile);
}
