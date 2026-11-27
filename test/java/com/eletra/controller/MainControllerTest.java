package com.eletra.controller;

import com.eletra.dto.CategoryDTO;
import com.eletra.dto.LineDTO;
import com.eletra.dto.ModelDTO;
import com.eletra.service.CategoryService;
import com.eletra.service.LineService;
import com.eletra.service.ModelService;
import javafx.embed.swing.JFXPanel;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TitledPane;
import javafx.scene.control.TreeView;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.Arrays;
import java.util.Collections;

public class MainControllerTest {

    private MainController controller;

    @Before
    public void setUp() {
        new JFXPanel();

        controller = Mockito.spy(new MainController());
        controller.tpModels = new TitledPane();
        controller.cbLines = new ComboBox<>();
        controller.tvModels = new TreeView<>();

        controller.lineService = mock(LineService.class);
        controller.categoryService = mock(CategoryService.class);
        controller.modelService = mock(ModelService.class);
    }

    @Test
    public void initializeControllerTest() {
        doNothing().when(controller).setAPIServices();
        doNothing().when(controller).setTpModels();
        doNothing().when(controller).setCbLines();

        controller.initialize(null, null);

        verify(controller).setAPIServices();
        verify(controller).setTpModels();
        verify(controller).setCbLines();
    }

    @Test
    public void setAPIServicesTest() {
        controller.lineService = null;
        controller.categoryService = null;
        controller.modelService = null;

        controller.setAPIServices();

        assertNotNull(controller.lineService);
        assertNotNull(controller.categoryService);
        assertNotNull(controller.modelService);
    }

    @Test
    public void setTpModelsTest() {
        controller.tpModels.setDisable(false);
        LineDTO cronos = new LineDTO(1, "Cronos");
        LineDTO ares = new LineDTO(2, "Ares");
        CategoryDTO cronosOld = new CategoryDTO(1, "Cronos Old", 1);
        ModelDTO cronos6001 = new ModelDTO(1, "Cronos 6001-A", 1);

        when(controller.lineService.getAllLines()).thenReturn(Arrays.asList(cronos, ares));
        when(controller.categoryService.getCategoriesByLineId(1)).thenReturn(Collections.singletonList(cronosOld));
        when(controller.modelService.getModelsByCategoryId(1)).thenReturn(Collections.singletonList(cronos6001));
        when(controller.categoryService.getCategoriesByLineId(2)).thenReturn(Collections.emptyList());

        controller.setTpModels();

        assertTrue(controller.tpModels.isDisabled());

        assertFalse(controller.tvModels.isShowRoot());

        assertEquals(controller.rootCategories, controller.tvModels.getRoot());
        assertTrue(controller.rootCategories.isExpanded());

        assertEquals(2, controller.cbLines.getItems().size());

        assertEquals("Cronos", controller.cbLines.getItems().get(0));
        assertEquals("Ares", controller.cbLines.getItems().get(1));

        assertTrue(controller.modelsCache.containsKey("Cronos"));
        assertTrue(controller.modelsCache.containsKey("Ares"));
    }

    @Test
    public void setCbLinesTestCase1() {
        LineDTO cronos = new LineDTO(1, "Cronos");
        LineDTO ares = new LineDTO(2, "Ares");
        CategoryDTO cronosOld = new CategoryDTO(1, "Cronos Old", 1);
        ModelDTO cronos6001A = new ModelDTO(1, "Cronos 6001-A", 1);
        ModelDTO cronos6003 = new ModelDTO(2, "Cronos 6003", 1);

        when(controller.lineService.getAllLines()).thenReturn(Arrays.asList(cronos, ares));
        when(controller.categoryService.getCategoriesByLineId(1)).thenReturn(Collections.singletonList(cronosOld));
        when(controller.modelService.getModelsByCategoryId(1)).thenReturn(Arrays.asList(cronos6001A, cronos6003));
        when(controller.categoryService.getCategoriesByLineId(2)).thenReturn(Collections.emptyList());

        controller.setTpModels();
        controller.setCbLines();

        controller.cbLines.setValue("Cronos");

        assertFalse(controller.tpModels.isDisabled());

        assertEquals("Cronos Old", controller.tvModels.getRoot().getChildren().get(0).getValue());

        assertEquals(2, controller.tvModels.getRoot().getChildren().get(0).getChildren().size());

        assertEquals("Cronos 6001-A", controller.tvModels.getRoot().getChildren().get(0).getChildren().get(0).getValue());
        assertEquals("Cronos 6003", controller.tvModels.getRoot().getChildren().get(0).getChildren().get(1).getValue());
    }

    @Test
    public void setCbLinesTestCase2() {
        LineDTO cronos = new LineDTO(1, "Cronos");
        LineDTO ares = new LineDTO(2, "Ares");
        CategoryDTO cronosOld = new CategoryDTO(1, "Cronos Old", 1);
        CategoryDTO aresTb = new CategoryDTO(4, "Ares TB", 2);
        ModelDTO ares7021 = new ModelDTO(12, "Ares 7021", 4);

        when(controller.lineService.getAllLines()).thenReturn(Arrays.asList(cronos, ares));
        when(controller.categoryService.getCategoriesByLineId(1)).thenReturn(Collections.singletonList(cronosOld));
        when(controller.modelService.getModelsByCategoryId(1)).thenReturn(Collections.emptyList());
        when(controller.categoryService.getCategoriesByLineId(2)).thenReturn(Collections.singletonList(aresTb));
        when(controller.modelService.getModelsByCategoryId(4)).thenReturn(Collections.singletonList(ares7021));

        controller.setTpModels();
        controller.setCbLines();

        controller.cbLines.setValue("Cronos");
        controller.tvModels.getRoot().getChildren().get(0).setExpanded(true);

        controller.cbLines.setValue("Ares");

        assertFalse(controller.tpModels.isDisabled());
        assertEquals("Ares TB", controller.tvModels.getRoot().getChildren().get(0).getValue());
        assertEquals("Ares 7021", controller.tvModels.getRoot().getChildren().get(0).getChildren().get(0).getValue());

        controller.cbLines.setValue("Cronos");
        assertFalse(controller.tvModels.getRoot().getChildren().get(0).isExpanded());
    }
}