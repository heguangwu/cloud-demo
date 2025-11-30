package cloud.heguangwu.product.controller;

import cloud.heguangwu.product.Product;
import cloud.heguangwu.product.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
//@RequestMapping("/api")
@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/product/{id}")
    public Product product(@PathVariable("id") Long productId, HttpServletRequest request) {
        String token = request.getHeader("X-Token");
        log.info("-------X-Token: {}", token);
        return productService.getProductById(productId);
    }
}
