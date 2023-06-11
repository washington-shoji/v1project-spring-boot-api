package com.v1project.v1project.integration;

import com.v1project.v1project.entities.Product;
import com.v1project.v1project.repositories.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@Testcontainers
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@EnableConfigurationProperties
@TestPropertySource("classpath:application-test.properties")
public class ProductPostgresSQLTest extends AbstractIntegrationTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        productRepository.deleteAll();
    }

    @Test
    @Order(1)
    void should_be_able_to_save_one_product() throws Exception {
        // Given
        final var product = new Product();
        product.setProductName("Integration Product One");
        product.setProductDescription("Integration product one description");
        product.setProductPrice("100");
        product.setProductImage("IntegrationImageURL");

        // When & Then
        mockMvc.perform(post("/product/create")
                .content(new ObjectMapper().writeValueAsString(product))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.productName").value("Integration Product One"))
                .andExpect(jsonPath("$.productDescription").value("Integration product one description"))
                .andExpect(jsonPath("$.productPrice").value("100"))
                .andExpect(jsonPath("$.productImage").value("IntegrationImageURL"));

    }

    @Test
    @Order(2)
    void should_be_able_to_retrieve_all_products() throws Exception {
        // Given
        final var productOne = new Product();
        productOne.setProductName("Integration Product One");
        productOne.setProductDescription("Integration product one description");
        productOne.setProductPrice("100");
        productOne.setProductImage("IntegrationImageURL");

        // Given
        final var productTwo = new Product();
        productTwo.setProductName("Integration Product One");
        productTwo.setProductDescription("Integration product one description");
        productTwo.setProductPrice("100");
        productTwo.setProductImage("IntegrationImageURL");

        // Given
        final var productThree = new Product();
        productThree.setProductName("Integration Product One");
        productThree.setProductDescription("Integration product one description");
        productThree.setProductPrice("100");
        productThree.setProductImage("IntegrationImageURL");

        productRepository.saveAll(List.of(productOne, productTwo, productThree));

        // When
        mockMvc.perform(get("/product").accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());

        // Then
        assertThat(productRepository.findAll()).hasSize(3);

        // Just for testing pipeline v1
    }
}
