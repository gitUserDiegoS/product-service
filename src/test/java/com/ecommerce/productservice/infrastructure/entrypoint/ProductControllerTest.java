package com.ecommerce.productservice.infrastructure.entrypoint;


import com.ecommerce.productservice.domain.model.product.Product;
import com.ecommerce.productservice.domain.usecase.product.IproductUseCase;
import com.ecommerce.productservice.infrastructure.adapter.securityauth.JwtAuthFilter;
import com.ecommerce.productservice.infrastructure.entrypoint.dto.CreateProductDto;
import com.ecommerce.productservice.infrastructure.entrypoint.dto.ProductResponseDto;
import com.ecommerce.productservice.infrastructure.entrypoint.dto.UpdateProductDto;
import com.ecommerce.productservice.infrastructure.entrypoint.mapper.ProductMapperDto;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ProductController.class,
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = JwtAuthFilter.class)
        })
@AutoConfigureMockMvc(addFilters = false)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private IproductUseCase productUseCase;

    @MockitoBean
    private ProductMapperDto productMapperDto;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldCreateProductSuccessfully() throws Exception {

        CreateProductDto createDto = new CreateProductDto();
        createDto.setName("Laptop");
        createDto.setDescription("Gaming laptop");
        createDto.setUnitPrice(BigDecimal.TWO);


        Product product = new Product();
        product.setId(1L);
        product.setName("Laptop");
        product.setUnitPrice(BigDecimal.TWO);

        ProductResponseDto response = ProductResponseDto.builder().id(1L).name("Laptop").unitPrice(BigDecimal.TWO).build();

        Mockito.when(productMapperDto.toModel(any(CreateProductDto.class))).thenReturn(product);
        Mockito.when(productUseCase.saveProduct(any())).thenReturn(product);
        Mockito.when(productMapperDto.toResponse(any())).thenReturn(response);

        mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Laptop"));
    }


    @Test
    void shouldGetProductByIdSuccessfully() throws Exception {

        Product product = new Product();
        product.setId(1L);
        product.setName("Phone");
        product.setUnitPrice(BigDecimal.TWO);


        ProductResponseDto response = ProductResponseDto.builder().id(1L).name("Phone").unitPrice(BigDecimal.TWO).build();

        Mockito.when(productUseCase.findProductById(1L)).thenReturn(product);
        Mockito.when(productMapperDto.toResponse(any())).thenReturn(response);

        mockMvc.perform(get("/api/v1/products/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Phone"))
                .andExpect(jsonPath("$.unitPrice").value(BigDecimal.TWO));
    }


    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldUpdateProductSuccessfully() throws Exception {
        UpdateProductDto updateDto = UpdateProductDto.builder()
                .id(1L)
                .name("Updated Laptop")
                .unitPrice(BigDecimal.TEN)
                .build();

        Product updatedProduct = new Product();
        updatedProduct.setId(1L);
        updatedProduct.setName("Updated Laptop");
        updatedProduct.setUnitPrice(BigDecimal.TEN);

        ProductResponseDto response = ProductResponseDto.builder().id(1L).name("Updated Laptop").unitPrice(BigDecimal.TEN).build();

        Mockito.when(productMapperDto.toModel(any(UpdateProductDto.class))).thenReturn(updatedProduct);
        Mockito.when(productUseCase.updateProduct(any())).thenReturn(updatedProduct);
        Mockito.when(productMapperDto.toResponse(any())).thenReturn(response);

        mockMvc.perform(put("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Laptop"))
                .andExpect(jsonPath("$.unitPrice").value(BigDecimal.TEN));
    }


    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldDeleteProductSuccessfully() throws Exception {
        mockMvc.perform(delete("/api/v1/products/1"))
                .andExpect(status().isNoContent());
        Mockito.verify(productUseCase, Mockito.times(1)).deleteProduct(1L);
    }


    @Test
    void shouldSearchProductsSuccessfully() throws Exception {

        Product product = new Product();
        product.setId(1L);
        product.setName("Mouse");
        product.setUnitPrice(BigDecimal.ONE);


        ProductResponseDto response = ProductResponseDto.builder().id(1L).name("Mouse").unitPrice(BigDecimal.ONE).build();

        Page<Product> productPage = new PageImpl<>(List.of(product), PageRequest.of(0, 10), 1);
        Mockito.when(productUseCase.searchProduct(any(), any(), any(), any())).thenReturn(productPage);
        Mockito.when(productMapperDto.toResponse(any())).thenReturn(response);

        mockMvc.perform(get("/api/v1/products")
                        .param("name", "Mouse")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name").value("Mouse"));
    }
}
