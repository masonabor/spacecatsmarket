package com.edu.web.spacecatsmarket.web;

import com.edu.web.spacecatsmarket.dto.category.CreateCategoryRequestDto;
import com.edu.web.spacecatsmarket.dto.category.ResponseCategoryDto;
import com.edu.web.spacecatsmarket.dto.category.UpdateCategoryRequestDto;
import com.edu.web.spacecatsmarket.featuretoggle.FeatureToggleExtension;
import com.edu.web.spacecatsmarket.featuretoggle.FeatureToggles;
import com.edu.web.spacecatsmarket.featuretoggle.annotation.DisabledFeatureToggle;
import com.edu.web.spacecatsmarket.featuretoggle.annotation.EnabledFeatureToggle;
import com.edu.web.spacecatsmarket.featuretoggle.exception.AdminRequiredException;
import com.edu.web.spacecatsmarket.featuretoggle.impl.FeatureToggleServiceImpl;
import com.edu.web.spacecatsmarket.service.CategoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jdk.jfr.Enabled;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Category Controller Integration Tests")
@ExtendWith(FeatureToggleExtension.class)
class CategoryControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CategoryService categoryService;

    @Autowired
    private ObjectMapper objectMapper;

    private ResponseCategoryDto categoryResponse;

    @BeforeEach
    void setUp() {
        reset(categoryService);
        categoryResponse = new ResponseCategoryDto(UUID.randomUUID().toString(), "Space goods");
    }

    @Test
    void testGetAllCategories_success() throws Exception {
        when(categoryService.getAll()).thenReturn(List.of(categoryResponse));

        mockMvc.perform(get("/api/v1/categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Space goods"));

        verify(categoryService, times(1)).getAll();
    }

    @Test
    void testCreateCategory_success() throws Exception {
        CreateCategoryRequestDto request = new CreateCategoryRequestDto("Space goods");
        when(categoryService.createCategory(any())).thenReturn(categoryResponse);

        mockMvc.perform(post("/api/v1/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Space goods"));

        verify(categoryService, times(1)).createCategory(any());
    }

    @Test
    void testCreateCategory_invalidName_returnsBadRequest() throws Exception {
        CreateCategoryRequestDto invalidRequest = new CreateCategoryRequestDto("");

        mockMvc.perform(post("/api/v1/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());

        verify(categoryService, never()).createCategory(any());
    }

    @Test
    void testUpdateCategory_success() throws Exception {
        UpdateCategoryRequestDto request = new UpdateCategoryRequestDto(categoryResponse.id(), "Updated goods");
        when(categoryService.updateCategory(any())).thenReturn(categoryResponse);

        mockMvc.perform(put("/api/v1/categories/{id}", categoryResponse.id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Space goods"));

        verify(categoryService, times(1)).updateCategory(any());
    }

    @Test
    void testUpdateCategory_idMismatch_returnsBadRequest() throws Exception {
        UpdateCategoryRequestDto request = new UpdateCategoryRequestDto("wrong-id", "Updated goods");

        mockMvc.perform(put("/api/v1/categories/{id}", "another-id")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());

        verify(categoryService, never()).updateCategory(any());
    }

    @Test
    @DisabledFeatureToggle(FeatureToggles.ADMIN_OPERATION)
    void testDeleteCategory_success() throws Exception {
        doNothing().when(categoryService).delete(any());

        mockMvc.perform(delete("/api/v1/categories/{id}", UUID.randomUUID()))
                .andExpect(status().isNoContent());

        verify(categoryService, times(1)).delete(any());
    }

    @Test
    @EnabledFeatureToggle(FeatureToggles.ADMIN_OPERATION)
    void testDeleteCategory_throwsAdminRequiredException() throws Exception {
        doNothing().when(categoryService).delete(any());

        mockMvc.perform(delete("/api/v1/categories/{id}", UUID.randomUUID())
                        .header("role", "USER"))
                        .andExpect(status().isForbidden())
                        .andExpect(result -> assertInstanceOf(AdminRequiredException.class, result.getResolvedException()));
    }

    @Test
    @EnabledFeatureToggle(FeatureToggles.ADMIN_OPERATION)
    void testDeleteCategoryWithAdminHeader_success() throws Exception {
        doNothing().when(categoryService).delete(any());

        mockMvc.perform(delete("/api/v1/categories/{id}", UUID.randomUUID())
                        .header("role", "ADMIN"))
                        .andExpect(status().isNoContent());
    }
}
