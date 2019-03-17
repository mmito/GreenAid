package app.services;

import app.models.Category;
import app.repository.CategoryRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryServiceImplTest {


    @Autowired
    private CategoryServiceImpl categoryService;

    @MockBean
    private CategoryRepository categoryRepositoryMock;

    @Test
    public void findExistingCategory() throws Exception {

        Category category = new Category();
        category.setId(55);
        category.setName("TestCategory");

        Mockito.when(categoryRepositoryMock.findById(55)).thenReturn(category);

        Category result = this.categoryService.findById(55);

        Assert.assertEquals(category, result);
    }

    @Test
    public void findNonexistingCategory() throws Exception {

        Mockito.when(categoryRepositoryMock.findById(55)).thenReturn(null);

        Category result = this.categoryService.findById(55);

        Assert.assertNull(result);
    }

}
