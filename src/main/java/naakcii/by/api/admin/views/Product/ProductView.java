package naakcii.by.api.admin.views.Product;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import naakcii.by.api.admin.MainView;
import naakcii.by.api.admin.utils.AppConsts;
import naakcii.by.api.product.Product;
import naakcii.by.api.product.ProductDTO;
import naakcii.by.api.product.ProductService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

//@HtmlImport("src/styles.html")
@Route(value = AppConsts.PAGE_MAIN + "/" + AppConsts.PAGE_PRODUCT, layout = MainView.class)
@PageTitle(AppConsts.TITLE_PRODUCT)
public class ProductView extends VerticalLayout implements HasUrlParameter<String> {

    @Value("${no.image}")
    private String noImage;

    private ProductService productService;
    private ProductForm form;
    private ProductDTO productDTO;

    private Grid<ProductDTO> grid;

    private Dialog dialog = new Dialog();
    private Binder<ProductDTO> binder = new Binder<>(ProductDTO.class);

    private TextField search;
    private Button addProduct;

    @Value("${adminka.token}")
    private String adminkaPath;

    @Autowired
    public ProductView(ProductService productService, ProductForm form) {
        setSizeFull();
        this.productService = productService;
        this.form = form;

        grid = new Grid<>();

        grid.addColumn(new ComponentRenderer<>(productDTO -> {
            if ((productDTO.getPicture() != null) && !StringUtils.isEmpty(productDTO.getPicture())) {
                Image image = new Image(productDTO.getPicture(), productDTO.getName());
                image.setWidth("50px");
                image.setHeight("50px");
                return image;
            } else {
                Image imageEmpty = new Image(noImage, "No image");
                imageEmpty.setHeight("50px");
                imageEmpty.setWidth("50px");
                return imageEmpty;
            }}
        ))
            .setHeader("Изображение");
        grid.addColumn(ProductDTO::getName).setFlexGrow(5).setHeader("Товар").setSortable(true);
        grid.addColumn(ProductDTO::getCategoryName).setHeader("Категория").setSortable(true);
        grid.addColumn(ProductDTO::getSubcategoryName).setHeader("Подкатегория").setSortable(true);
        updateList(null);

        //drag and drop columns order
        grid.setColumnReorderingAllowed(true);

        search = new TextField("Поиск товара");
        search.setValueChangeMode(ValueChangeMode.EAGER);
        search.setPlaceholder("Введите наименование товара");
        search.setWidth("50%");
        search.addValueChangeListener(e ->
            updateList(e.getValue())
        );

        addProduct = new Button("Добавить товар");
        addProduct.addThemeVariants(ButtonVariant.MATERIAL_CONTAINED);
        addProduct.setHeight("70%");
        addProduct.addClickListener(e-> {
           grid.asSingleSelect().clear();
           getProductForm().setBinder(binder, new ProductDTO());
           dialog.open();
        });
        HorizontalLayout toolbar = new HorizontalLayout(search, addProduct);
        toolbar.setWidth("100%");
        addProduct.getStyle().set("margin-left", "auto").set("margin-top", "auto");

        add(toolbar, grid);
        dialog.add(form);

        grid.asSingleSelect().addValueChangeListener(event -> {
            getProductForm().setBinder(binder, event.getValue());
            binder.readBean(event.getValue());
            dialog.open();
        });

        setupEventListeners();
        grid.getDataProvider().refreshAll();
        getProductForm().getButtons().getSaveButton().addClickShortcut(Key.ENTER);
    }

    private void updateList(String search) {
        if(StringUtils.isEmpty(search)) {
            grid.setItems(productService.findAllDTOs());
        } else {
            grid.setItems(productService.searchName(search));
        }
    }

    private void setupEventListeners() {
        getProductForm().getButtons().addSaveListener(e -> save());
        getProductForm().getButtons().addCancelListener(e -> cancel());
        getProductForm().getButtons().addDeleteListener(e -> delete());

        getDialog().getElement().addEventListener("opened-changed", e -> {
           if(!getDialog().isOpened()) {
               cancel();
           }
        });
    }

    //soft delete
    private void delete() {
        ProductDTO productDTO = getProductForm().getProductDTO();
        Product product = productService.findProduct(productDTO.getId());
        productService.softDelete(product);
        Notification.show(productDTO.getName() + " присвоен статус 'Не активен'");
        closeUpdate();
    }

    private void cancel() {
        getDialog().close();
        grid.getDataProvider().refreshAll();
    }

    private void save() {
        ProductDTO productDTO = getProductForm().getProductDTO();
        boolean isValid = binder.writeBeanIfValid(productDTO);
        if (isValid) {
            Product product = new Product(productDTO);
            product.setSubcategory(getProductForm().getSubcategory());
            product.setUnitOfMeasure(getProductForm().getUnitOfMeasure());
            product.setCountryOfOrigin(getProductForm().getCountryOfOrigin());
            productService.save(product);
            Notification.show(productDTO.getName() + " сохранён");
            closeUpdate();
        }
    }

    private void closeUpdate() {
        grid.asSingleSelect().clear();
        getDialog().close();
        updateList(null);
        grid.getDataProvider().refreshAll();
    }

    private Dialog getDialog() {
        return dialog;
    }

    private ProductForm getProductForm() {
        return form;
    }

    @Override
    public void setParameter(BeforeEvent event, String parameter) {
        if (!parameter.equals(adminkaPath)) {
            throw new IllegalArgumentException();
        }
    }
}