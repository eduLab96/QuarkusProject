package edu.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import edu.api.ApiRequests;
import edu.entities.Product;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;
import jakarta.inject.Inject;

@PageTitle("Products")
@Route(value = "products", layout = MainLayout.class)
public class ProductsView extends VerticalLayout {
	
	@Inject
    ApiRequests api;
    Grid<Product> grid;
    TextField addNameField;
    TextField addDescriptionField;
    TextField updtNameField;
    TextField updtDescField;
    IntegerField idField;
    Button addButton;
    Button updateButton;
    Button deleteButton;
    Button cancelButton;

    public ProductsView() {
        
        //GRID
        createGrid();

        //FIELDS
        addNameField = new TextField();
        addNameField.setLabel("Name");
        addNameField.setId("addNameField");
        addDescriptionField = new TextField();
        addDescriptionField.setLabel("Description");
        addDescriptionField.setId("addDescriptionField");
        idField = new IntegerField();
        idField.setLabel("Id");
        idField.setReadOnly(true);
        idField.setId("idField");
        updtNameField = new TextField();
        updtNameField.setLabel("Name");
        updtNameField.setId("updtNameField");
        updtDescField = new TextField();
        updtDescField.setLabel("Description");
        updtDescField.setId("updtDescField");

        //BUTTONS
        addButton = new Button();
        addButton.setText("Add");
        addButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        addButton.addClickListener(click -> addProduct());

        deleteButton = new Button();
        deleteButton.setText("Delete");
        deleteButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
        deleteButton.addClickListener(click -> showDeleteDialog());

        cancelButton = new Button();
        cancelButton.setText("Cancel");
        cancelButton.addClickListener(click -> clearForm());

        updateButton = new Button();
        updateButton.setText("Update");
        updateButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        updateButton.addClickListener(click -> updateProduct());

        //LAYOUTS
        HorizontalLayout layoutRow = new HorizontalLayout();
        VerticalLayout layoutColumn2 = new VerticalLayout();
        VerticalLayout layoutColumn3 = new VerticalLayout();
        VerticalLayout layoutColumn4 = new VerticalLayout();
        setHeightFull();
        setWidthFull();
        setFlexGrow(1.0, layoutRow);
        layoutRow.setWidthFull();
        layoutRow.addClassName(Gap.MEDIUM);
        layoutColumn2.setHeightFull();
        layoutColumn2.setWidth(null);
        layoutColumn3.setHeightFull();
        layoutRow.setFlexGrow(1.0, layoutColumn3);
        layoutColumn3.setWidth(null);
        layoutColumn4.setWidth(null);
        layoutColumn4.setAlignSelf(FlexComponent.Alignment.START, deleteButton);
        add(layoutRow);
        layoutRow.add(layoutColumn2);
        layoutColumn2.add(addNameField);
        layoutColumn2.add(addDescriptionField);
        layoutColumn2.add(addButton);
        layoutRow.add(layoutColumn3);
        layoutRow.add(layoutColumn4);
        layoutColumn4.add(idField);
        layoutColumn4.add(updtNameField);
        layoutColumn4.add(updtDescField);
        layoutColumn4.add(updateButton);
        layoutColumn4.add(deleteButton);
        layoutColumn4.add(cancelButton);
        layoutColumn3.add(grid);
        
    }
    
    private void showDeleteDialog() {
    	
    	ConfirmDialog dialog = new ConfirmDialog();
    	dialog.setHeader("Delete product?");
    	dialog.setText(
    	        "Are you sure you want to permanently delete this item?");

    	dialog.setCancelable(true);

    	dialog.setConfirmText("Delete");
    	dialog.setConfirmButtonTheme("error primary");
    	dialog.addConfirmListener(event -> deleteProduct());
    	
	    dialog.open();
    	
    }
    
    private void loadGrid() {
    	grid.setItems(query -> 
    		api.requestProductListQuery(
                    query.getOffset(),
                    query.getLimit() 
                )
                .stream());
    }
    
    private void addProduct() {
        Product p = new Product(addNameField.getValue(),addDescriptionField.getValue());
        if (!api.requestPostProduct(p)) {
            Notification.show("Couldn't add the product")
                    .addThemeVariants(NotificationVariant.LUMO_ERROR);
        }
        addNameField.setValue("");
        addDescriptionField.setValue("");
        loadGrid();
        Notification.show("Product added");
    }

    private void updateProduct() {
        if (idField.isEmpty()) {
            Notification.show("To upload a product you have to select it from the list")
                    .addThemeVariants(NotificationVariant.LUMO_ERROR);
        }

        Product p = new Product( idField.getValue(),
                updtNameField.getValue(),
                updtDescField.getValue());

        if (!api.requestPutProduct(p)) {
            Notification.show("Couldn't upload the product")
                    .addThemeVariants(NotificationVariant.LUMO_ERROR);
        }
        loadGrid();
    }

    private void deleteProduct() {
    	
		if (idField.getValue() == null) {
            Notification.show("Id is empty")
                    .addThemeVariants(NotificationVariant.LUMO_ERROR);
        }

        Product p = new Product( idField.getValue(),
                updtNameField.getValue(),
                updtDescField.getValue());


        if (!api.requestDeleteProduct(p)) {
            Notification.show("Couldn't delete the product")
                    .addThemeVariants(NotificationVariant.LUMO_ERROR);
        }
        loadGrid();
   
    }


    private void fillForm(Product p) {
        updtNameField.setValue(p.getName());
        updtDescField.setValue(p.getDescription());
        idField.setValue(p.getId());
    }

    private void clearForm(){
        updtNameField.setValue("");
        updtDescField.setValue("");
        idField.setValue(null);
    }

    private void createGrid(){
        grid = new Grid<>(Product.class, false);
        grid.setSelectionMode(Grid.SelectionMode.SINGLE);
        grid.addThemeVariants(
        		GridVariant.LUMO_COMPACT, 
        		GridVariant.LUMO_NO_BORDER,
                GridVariant.LUMO_NO_ROW_BORDERS
                );
        grid.addColumn(Product::getId).setHeader("Id").setSortable(true);
        grid.addColumn(Product::getName).setHeader("Name").setSortable(true);
        grid.addColumn(Product::getDescription).setHeader("Description").setSortable(true);
        
        grid.addComponentColumn(Product -> {
        	  Checkbox checkbox = new Checkbox();
        	  checkbox.setValue(Product.isAvaliable());
        	  checkbox.setReadOnly(true);
        	  return checkbox;
        	})
        .setHeader("Is avaliable?")
        .setSortable(true);
        
        
        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                Product p = event.getValue();
                fillForm(p);
            } else {
                clearForm();
            }
        });
        grid.setColumnReorderingAllowed(true);
        loadGrid();
    }

    public void setIdField(int id) {
        idField.setValue(id);
    }

}
