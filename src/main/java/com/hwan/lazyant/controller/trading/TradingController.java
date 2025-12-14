package com.hwan.lazyant.controller.trading;

import com.hwan.lazyant.controller.trading.dto.TradingInsertRequest;
import com.hwan.lazyant.ocr.OcrExecutor;
import com.hwan.lazyant.ocr.TradingWordsGroup;
import com.hwan.lazyant.service.trading.TradingService;
import com.hwan.lazyant.util.FileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

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

    @PostMapping("/ocr/extract")
    public ResponseEntity<List<TradingWordsGroup>> extractTradingWords(@RequestPart("imageFile") MultipartFile imageFile) {
        if (!imageFile.isEmpty()) {
            try {
                File file = FileUtil.convertMultipartFileToFile(imageFile);
                return ResponseEntity.ok(ocrExecutor.extractTradings(file));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            return ResponseEntity.badRequest()
                    .build();
        }
    }
}
