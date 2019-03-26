package naakcii.by.api.admin.views.category;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import naakcii.by.api.admin.MainView;
import naakcii.by.api.admin.utils.AppConsts;
import naakcii.by.api.admin.views.CrudForm;
import naakcii.by.api.admin.views.CrudView;
import naakcii.by.api.category.CategoryDTO;
import naakcii.by.api.category.CategoryService;
import naakcii.by.api.service.CrudService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

@Route(value = AppConsts.PAGE_MAIN + "/" + AppConsts.PAGE_CATEGORY, layout = MainView.class)
@PageTitle(AppConsts.TITLE_CATEGORY)
public class CategoryView extends CrudView<CategoryDTO> {

    @Value("${no.image}")
    private String noImage;

    private final Binder<CategoryDTO> binder;

    @Autowired
    public CategoryView(CrudForm<CategoryDTO> form, CrudService<CategoryDTO> crudService, CategoryService categoryService) {
        super(form, crudService, new ComboBox<String>("Фильтр"));
        ComboBox<String> filter = (ComboBox<String>) getFilterComponent();
        filter.setItems("Активные", "Неактивные");
        filter.setValue("Активные");
        getGrid().setItems(categoryService.checkIsActive(filter.getValue()));
        filter.addValueChangeListener(e-> getGrid().setItems(categoryService.checkIsActive(e.getValue())));
        binder = new Binder<>(CategoryDTO.class);
    }

    @Override
    public Binder<CategoryDTO> getBinder() {
        return binder;
    }

    @Override
    protected void setupGrid() {
        getGrid().addColumn(new ComponentRenderer<>(categoryDTO -> {
            if ((categoryDTO.getIcon() != null) && !StringUtils.isEmpty(categoryDTO.getIcon())) {
                Image image = new Image(categoryDTO.getIcon(), categoryDTO.getName());
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
        getGrid().addColumn(CategoryDTO::getName).setHeader("Категория").setSortable(true);
        getGrid().addColumn(CategoryDTO::getPriority).setHeader("Порядок отображения").setSortable(true);
    }
}