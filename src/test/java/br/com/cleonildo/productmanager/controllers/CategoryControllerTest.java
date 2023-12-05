package br.com.cleonildo.productmanager.controllers;

import br.com.cleonildo.productmanager.dto.CategoryRequest;
import br.com.cleonildo.productmanager.dto.CategoryResponse;
import br.com.cleonildo.productmanager.entities.CategoryType;
import br.com.cleonildo.productmanager.services.CategoryService;
import br.com.cleonildo.productmanager.utils.factory.CategoryFactoryUtil;
import br.com.cleonildo.productmanager.utils.factory.JsonMapperUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static java.util.Collections.singletonList;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CategoryControllerTest {

    @InjectMocks
    private CategoryController controller;
    @Mock
    private CategoryService service;
    private MockMvc mockMvc;

    // Endpoints
    private final static String BASE_URL = "/api/v1/categories";
    private final static String URL_ID =  BASE_URL + "/{id}";

    // Constants for Response and Request
    private static final Long ID = 1L;
    private static final String NAME = "Bebida";
    private static final boolean IS_ACTIVE = true;
    private static final CategoryType CATEGORY_TYPE = CategoryType.SPECIAL;
    private static final String CATEGORY_TYPE_STRING = CategoryType.SPECIAL.getTypeName();
    private CategoryResponse response;
    private CategoryRequest request;

    @BeforeEach
    void setupAttributes() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.controller).alwaysDo(print()).build();
        this.response = CategoryFactoryUtil.buildCategoryResponse(ID, NAME, IS_ACTIVE, CATEGORY_TYPE);
        this.request = CategoryFactoryUtil.buildCategoryRequest(NAME, IS_ACTIVE, CATEGORY_TYPE);
    }

    @Test
    @DisplayName("Should get category by ID and return status code OK")
    void shouldGetCategoryByIdAndReturnStatusCodeOK() throws Exception {
        // Given
        given(service.getCategoryById(ID)).willReturn(response);

        // When
        ResultActions resultActions = mockMvc.perform(get(URL_ID, ID).contentType(MediaType.APPLICATION_JSON));

        // Then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ID))
                .andExpect(jsonPath("$.name").value(NAME))
                .andExpect(jsonPath("$.active").value(IS_ACTIVE))
                .andExpect(jsonPath("$.type").value(CATEGORY_TYPE_STRING));


        // Verify
        verify(this.service).getCategoryById(ID);
        verifyNoMoreInteractions(this.service);
    }

    @Test
    @DisplayName("Should get list of categories and return status code OK")
    void shouldGetListOfCategoriesAndReturnStatusCodeOK() throws Exception {
        // Given
        given(service.getListCategory()).willReturn(singletonList(response));

        // When
        ResultActions resultActions = mockMvc.perform(get(BASE_URL).contentType(MediaType.APPLICATION_JSON));


        // Then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(ID))
                .andExpect(jsonPath("$[0].name").value(NAME))
                .andExpect(jsonPath("$[0].active").value(IS_ACTIVE))
                .andExpect(jsonPath("$[0].type").value(CATEGORY_TYPE_STRING));


        // Verify
        verify(service).getListCategory();
        verifyNoMoreInteractions(service);
    }

    @Test
    @DisplayName("Should update category by ID and return status code OK")
    void shouldUpdateCategoryByIdAndReturnStatusCodeOK() throws Exception {
        // Given
        given(service.updateCategory(ID, request)).willReturn(response);

        // When
        ResultActions resultActions = mockMvc.perform(put(URL_ID, ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonMapperUtils.asJsonString(request)));

        // Then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ID))
                .andExpect(jsonPath("$.name").value(NAME))
                .andExpect(jsonPath("$.active").value(IS_ACTIVE))
                .andExpect(jsonPath("$.type").value(CATEGORY_TYPE_STRING));

        // Verify
        verify(service).updateCategory(ID, request);
        verifyNoMoreInteractions(service);
    }

}
