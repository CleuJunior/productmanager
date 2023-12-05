package br.com.cleonildo.productmanager.services;

import br.com.cleonildo.productmanager.dto.CategoryRequest;
import br.com.cleonildo.productmanager.dto.CategoryResponse;
import br.com.cleonildo.productmanager.entities.Category;
import br.com.cleonildo.productmanager.entities.CategoryType;
import br.com.cleonildo.productmanager.exceptions.NotFoundException;
import br.com.cleonildo.productmanager.repositories.ICategoryRepository;
import br.com.cleonildo.productmanager.utils.factory.CategoryFactoryUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static br.com.cleonildo.productmanager.exceptions.ExceptionsConstants.CATEGORY_ID_NOT_FOUND;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;


@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {
    @Mock
    private ICategoryRepository repository;
    @InjectMocks
    private CategoryService service;
    private static final Long ID = 1L;
    private static final String NAME = "Bebida";
    private static final boolean IS_ACTIVE = true;
    private static final CategoryType CATEGORY_TYPE = CategoryType.SPECIAL;
    private Category category;
    private CategoryResponse response;

    @BeforeEach
    void setup() {
        this.category = CategoryFactoryUtil.buildCategory(ID, NAME, IS_ACTIVE, CATEGORY_TYPE);
        this.response = CategoryFactoryUtil.buildCategoryResponse(ID, NAME, IS_ACTIVE, CATEGORY_TYPE);
    }

    @Test
    @DisplayName("Return the list of all categories when calling getListCategory")
    void shouldReturnListCategory_WhenCallingGetListCategory() {
        given(repository.findAll()).willReturn(singletonList(category));

        var actual = this.service.getListCategory();

        assertThat(actual.isEmpty(), is(false));
        assertThat(actual.size(), is(1));
        assertThat(actual.get(0), is(response));

        verify(this.repository).findAll();
        verifyNoMoreInteractions(this.repository);
    }

    @Test
    @DisplayName("Should return a category when calling getCategoryById")
    void shouldReturnCategory_WhenCallingGetCategoryById() {
        given(repository.findById(ID)).willReturn(Optional.ofNullable(category));

        var actual = this.service.getCategoryById(ID);

        assertThat(response, is(actual));

        verify(this.repository).findById(ID);
        verifyNoMoreInteractions(this.repository);
    }

    @Test
    @DisplayName("Should throw NotFoundException when ID does not exist")
    void shouldThrowNotFoundException_WhenIdDoesNotExist() {
        // Arrange
        given(repository.findById(anyLong())).willReturn(Optional.empty());

        // Act and Assert
        assertThatThrownBy(() -> service.getCategoryById(ID))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining(CATEGORY_ID_NOT_FOUND);
    }

    @Test
    @DisplayName("Given an existing category, when updating the category, then the category should be updated successfully")
    void givenExistingCategory_whenUpdatingCategory_thenCategoryShouldBeUpdatedSuccessfully() {
        // Given
        CategoryRequest updateRequest = new CategoryRequest("Updated Category", false, CategoryType.NORMAL);
        given(repository.findById(ID)).willReturn(Optional.ofNullable(category));

        // When
        CategoryResponse updatedCategoryResponse = service.updateCategory(ID, updateRequest);

        // Then
        assertNotNull(updatedCategoryResponse);
        assertThat(updatedCategoryResponse.name(), is(updateRequest.name()));
        assertThat(updatedCategoryResponse.active(), is(updateRequest.active()));
        assertThat(updatedCategoryResponse.type(), is(updateRequest.type().getTypeName()));

        // And
        verify(repository).findById(ID);
        verify(repository).save(any(Category.class));
    }

    @Test
    @DisplayName("Given a non-existing category, when updating the category, then a NotFoundException should be thrown")
    void givenNonExistingCategory_whenUpdatingCategory_thenNotFoundExceptionShouldBeThrown() {
        // Given
        CategoryRequest updateRequest = new CategoryRequest("Updated Category", false, CategoryType.NORMAL);
        given(repository.findById(anyLong())).willReturn(Optional.empty());

        // When and Then
        assertThatThrownBy(() -> service.updateCategory(ID, updateRequest))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining(CATEGORY_ID_NOT_FOUND);

        // And
        verify(repository).findById(anyLong());
        verifyNoMoreInteractions(repository);
    }

}