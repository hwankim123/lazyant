package com.hwan.lazyant.controller.trading;

import com.hwan.lazyant.controller.trading.dto.TradingInsertRequest;
import com.hwan.lazyant.ocr.OcrExecutor;
import com.hwan.lazyant.service.trading.TradingService;
import com.hwan.lazyant.util.FileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/tradings")
public class TradingController {

    private final TradingService tradingService;
    private final OcrExecutor ocrExecutor;

    @PostMapping
    public void insert(@RequestBody TradingInsertRequest request) {
        tradingService.insert(request);
    }

    @PostMapping("/bulk")
    public void bulkInsert(@RequestPart("imageFile") MultipartFile imageFile) {
        if (!imageFile.isEmpty()) {
            try {
                File file = FileUtil.convertMultipartFileToFile(imageFile);
                ocrExecutor.extractTradings(file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
