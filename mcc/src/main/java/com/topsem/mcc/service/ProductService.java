package com.topsem.mcc.service;

import com.topsem.common.service.BaseService;
import com.topsem.mcc.domain.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service class for managing products.
 */
@Service
@Transactional
@Slf4j
public class ProductService extends BaseService<Product, Long> {

}
