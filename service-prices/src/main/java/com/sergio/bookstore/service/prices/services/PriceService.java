package com.sergio.bookstore.service.prices.services;

import com.sergio.bookstore.service.prices.dto.PriceDto;
import com.sergio.bookstore.service.prices.exceptions.AppException;
import com.sergio.bookstore.service.prices.mappers.PriceMapper;
import com.sergio.bookstore.service.prices.respositories.PriceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@RequiredArgsConstructor
@Service
public class PriceService {

    private final PriceRepository priceRepository;
    private final PriceMapper priceMapper;

    public PriceDto getPrice(long bookId) {
        return priceRepository.findByBookId(bookId)
                .map(priceMapper::toPriceDto)
                .orElseThrow(() -> new AppException("No price for book " + bookId, HttpStatus.NOT_FOUND));
    }

    public void updatePrice(long bookId, BigDecimal newPrice) {
        var price = priceRepository.findByBookId(bookId)
                .orElseThrow(() -> new AppException("No price for book " + bookId, HttpStatus.NOT_FOUND));
        
        log.info("Updating price for book {} from {} to {}", bookId, price.getPrice(), newPrice);
        price.setPrice(newPrice);
        // BUG: Missing priceRepository.save(price) - changes lost!
    }
}
