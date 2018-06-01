package lt.vu.mif.generator.realizations;

import java.util.ArrayList;
import java.util.List;
import lt.vu.mif.generator.interfaces.IDataGenerator;
import lt.vu.mif.model.product.Category;
import lt.vu.mif.model.product.CategoryAttribute;
import lt.vu.mif.model.product.CategoryAttributeValue;
import lt.vu.mif.model.user.Role;
import lt.vu.mif.model.user.User;
import lt.vu.mif.repository.repository.interfaces.ICategoryRepository;
import lt.vu.mif.repository.repository.interfaces.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PresentationDataGenerator implements IDataGenerator {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private ICategoryRepository categoryRepository;
    @Autowired
    private CategoryInserter categoryInserter;

    @Override
    public void generateData() {
        insertUsers();
        insertCategories();
    }

    private void insertCategories() {
        categoryInserter.insertRootCategory();
        Category root = categoryRepository.getRootCategory();

        Category skateboad = new Category();
        skateboad.setName("Riedlentės");
        skateboad.setParentCategory(root);

        //ILGIS
        CategoryAttribute lenght = createCategoryAttribute(skateboad, "Ilgis");
        List<CategoryAttributeValue> lenghtValues = new ArrayList<>();
        lenghtValues.add(createAttributeValue(lenght, "30"));
        lenghtValues.add(createAttributeValue(lenght, "31"));
        lenghtValues.add(createAttributeValue(lenght, "32"));
        lenghtValues.add(createAttributeValue(lenght, "39"));
        lenghtValues.add(createAttributeValue(lenght, "40"));
        lenghtValues.add(createAttributeValue(lenght, "40,5"));
        lenghtValues.add(createAttributeValue(lenght, "46"));
        lenghtValues.add(createAttributeValue(lenght, "38"));
        lenght.getValues().addAll(lenghtValues);


        //PLOTIS
        CategoryAttribute width = createCategoryAttribute(skateboad, "Plotis");
        List<CategoryAttributeValue> widthValues = new ArrayList<>();
        widthValues.add(createAttributeValue(width, "7.5"));
        widthValues.add(createAttributeValue(width, "8"));
        widthValues.add(createAttributeValue(width, "10"));
        widthValues.add(createAttributeValue(width, "29.75"));
        widthValues.add(createAttributeValue(width, "9.75"));
        widthValues.add(createAttributeValue(width, "9"));
        widthValues.add(createAttributeValue(width, "46"));
        width.getValues().addAll(widthValues);

        //RATAI
        CategoryAttribute wheels = createCategoryAttribute(skateboad, "Ratai");
        List<CategoryAttributeValue> wheelsValues = new ArrayList<>();
        wheelsValues.add(createAttributeValue(wheels, "52x30mm,100A"));
        wheelsValues.add(createAttributeValue(wheels, "52x33mm,100A"));
        wheelsValues.add(createAttributeValue(wheels, "32x30mm,100A"));
        wheelsValues.add(createAttributeValue(wheels, "75x52mm,78A"));
        wheelsValues.add(createAttributeValue(wheels, "71x51mm,82A"));
        wheelsValues.add(createAttributeValue(wheels, "70x51mm,78A"));
        wheelsValues.add(createAttributeValue(wheels, "80 mm 84A"));
        wheelsValues.add(createAttributeValue(wheels, "110 mm 88A"));
        wheelsValues.add(createAttributeValue(wheels, "110 mm 85A"));
        wheels.getValues().addAll(wheelsValues);


        //GUOLIAI
        CategoryAttribute bearings = createCategoryAttribute(skateboad, "Guoliai");
        List<CategoryAttributeValue> bearingsValues = new ArrayList<>();
        bearingsValues.add(createAttributeValue(bearings, "Wicked ABEC 5"));
        bearingsValues.add(createAttributeValue(bearings, "Wicked ABEC 7"));
        bearingsValues.add(createAttributeValue(bearings, "High Precision Mindless"));
        bearingsValues.add(createAttributeValue(bearings, "ABEC 9"));
        bearingsValues.add(createAttributeValue(bearings, "Twincam"));
        bearingsValues.add(createAttributeValue(bearings, "Wicked freespin ABEC 9"));
        bearings.getValues().addAll(bearingsValues);

        Category longboards = new Category();
        longboards.setName("Longboards");
        longboards.setParentCategory(skateboad);

        CategoryAttribute maxHeight = createCategoryAttribute(longboards, "Maksimalus svoris");
        List<CategoryAttributeValue> maxHeightValues = new ArrayList<>();
        maxHeightValues.add(createAttributeValue(maxHeight, "130kg"));
        maxHeightValues.add(createAttributeValue(maxHeight, "120kg"));
        maxHeightValues.add(createAttributeValue(maxHeight, "100kg"));
        maxHeight.getValues().addAll(maxHeightValues);

        Category skateboards = new Category();
        skateboards.setName("Skateboards");
        skateboards.setParentCategory(skateboad);

        CategoryAttribute color = createCategoryAttribute(skateboards, "Spalva");
        List<CategoryAttributeValue> colorValues = new ArrayList<>();
        colorValues.add(createAttributeValue(color, "Mėlyna"));
        colorValues.add(createAttributeValue(color, "Oranžinė"));
        colorValues.add(createAttributeValue(color, "Sidabrinė"));
        color.getValues().addAll(colorValues);

        CategoryAttribute axis = createCategoryAttribute(skateboards, "Ašys");
        List<CategoryAttributeValue> axisValues = new ArrayList<>();
        axisValues.add(createAttributeValue(axis, "Ašis 1"));
        axisValues.add(createAttributeValue(axis, "Ašis 2"));
        axis.getValues().addAll(axisValues);

        Category skates = new Category();
        skates.setName("Riedučiai");
        skates.setParentCategory(root);

        //RIEDUČIŲ GUOLIAI
        CategoryAttribute skatesBearings = createCategoryAttribute(skates, "Guoliai");
        List<CategoryAttributeValue> skatesBearingsValues = new ArrayList<>();
        skatesBearingsValues.add(createAttributeValue(skatesBearings, "Wicked ABEC 5"));
        skatesBearingsValues.add(createAttributeValue(skatesBearings, "Wicked ABEC 7"));
        skatesBearingsValues.add(createAttributeValue(skatesBearings, "High Precision Mindless"));
        skatesBearingsValues.add(createAttributeValue(skatesBearings, "ABEC 9"));
        skatesBearingsValues.add(createAttributeValue(skatesBearings, "Twincam"));
        skatesBearingsValues.add(createAttributeValue(skatesBearings, "Wicked freespin ABEC 9"));
        skatesBearings.getValues().addAll(skatesBearingsValues);

        CategoryAttribute skatesaxis = createCategoryAttribute(skates, "Ašys");
        List<CategoryAttributeValue> skatesaxisValues = new ArrayList<>();
        skatesaxisValues.add(createAttributeValue(skatesaxis, "Ašis 1"));
        skatesaxisValues.add(createAttributeValue(skatesaxis, "Ašis 2"));
        skatesaxis.getValues().addAll(skatesaxisValues);


        CategoryAttribute skatesWheel = createCategoryAttribute(skates, "Ratai");
        List<CategoryAttributeValue> skatesWheelValues = new ArrayList<>();
        skatesWheelValues.add(createAttributeValue(skatesWheel, "52x30mm,100A"));
        skatesWheelValues.add(createAttributeValue(skatesWheel, "52x33mm,100A"));
        skatesWheelValues.add(createAttributeValue(skatesWheel, "32x30mm,100A"));
        skatesWheelValues.add(createAttributeValue(skatesWheel, "75x52mm,78A"));
        skatesWheelValues.add(createAttributeValue(skatesWheel, "71x51mm,82A"));
        skatesWheelValues.add(createAttributeValue(skatesWheel, "70x51mm,78A"));
        skatesWheelValues.add(createAttributeValue(skatesWheel, "80 mm 84A"));
        skatesWheelValues.add(createAttributeValue(skatesWheel, "110 mm 88A"));
        skatesWheelValues.add(createAttributeValue(skatesWheel, "110 mm 85A"));
        skatesWheel.getValues().addAll(skatesWheelValues);

        CategoryAttribute purpose = createCategoryAttribute(skates, "Paskirtis");
        List<CategoryAttributeValue> purposeValues = new ArrayList<>();
        purposeValues.add(createAttributeValue(purpose, "Paskirtis"));
        purpose.getValues().addAll(purposeValues);

        Category protection = new Category();
        protection.setName("Apsaugos");
        protection.setParentCategory(root);

        CategoryAttribute manufacturer = createCategoryAttribute(protection, "Gamintojas");
        List<CategoryAttributeValue> manufacturerValues = new ArrayList<>();
        manufacturerValues.add(createAttributeValue(manufacturer, "SEBA"));
        manufacturerValues.add(createAttributeValue(manufacturer, "Powerslide"));
        manufacturerValues.add(createAttributeValue(manufacturer, "Ennui"));

        manufacturer.getValues().addAll(manufacturerValues);

        CategoryAttribute size = createCategoryAttribute(protection, "Dydis");
        List<CategoryAttributeValue> sizeValues = new ArrayList<>();
        sizeValues.add(createAttributeValue(size, "S"));
        sizeValues.add(createAttributeValue(size, "M"));
        sizeValues.add(createAttributeValue(size, "L"));
        size.getValues().addAll(sizeValues);

        Category spareParts = new Category();
        spareParts.setName("Atsarginės dalys");
        spareParts.setParentCategory(root);

        Category partwheels = new Category();
        partwheels.setName("Ratai");
        partwheels.setParentCategory(spareParts);

        CategoryAttribute wheelSize = createCategoryAttribute(partwheels, "Dydis");
        List<CategoryAttributeValue> wheelSizeValues = new ArrayList<>();
        wheelSizeValues.add(createAttributeValue(wheelSize, "S"));
        wheelSizeValues.add(createAttributeValue(wheelSize, "M"));
        wheelSizeValues.add(createAttributeValue(wheelSize, "L"));
        wheelSize.getValues().addAll(wheelSizeValues);

        CategoryAttribute thickness = createCategoryAttribute(partwheels, "Storis");
        List<CategoryAttributeValue> thicknessValues = new ArrayList<>();
        thicknessValues.add(createAttributeValue(thickness, "30mm"));
        thickness.getValues().addAll(thicknessValues);

        CategoryAttribute hardness = createCategoryAttribute(partwheels, "Kietumas");
        List<CategoryAttributeValue> hardnessValues = new ArrayList<>();
        hardnessValues.add(createAttributeValue(hardness, "100A"));
        hardnessValues.add(createAttributeValue(hardness, "85A"));
        hardness.getValues().addAll(hardnessValues);

        CategoryAttribute to = createCategoryAttribute(partwheels, "Skirta");
        List<CategoryAttributeValue> toValues = new ArrayList<>();
        toValues.add(createAttributeValue(to, "Riedlentėms"));
        toValues.add(createAttributeValue(to, "Riedučiams"));
        to.getValues().addAll(toValues);

        Category partbearings = new Category();
        partbearings.setName("Guoliai");
        partbearings.setParentCategory(spareParts);

        categoryRepository.save(skateboad);
        categoryRepository.save(skates);
        categoryRepository.save(protection);
        categoryRepository.save(spareParts);
        categoryRepository.save(longboards);
        categoryRepository.save(skateboards);
        categoryRepository.save(partwheels);
        categoryRepository.save(partbearings);
    }

    private void insertUsers() {
        List<User> users = new ArrayList<>();

        users.add(createUser("vartotojas1@test.lt", Role.USER, false));
        users.add(createUser("vartotojas2@test.lt", Role.USER, false));
        users.add(createUser("vartotojas3@test.lt", Role.USER, true));
        users.add(createUser("administratorius@test.lt", Role.ADMIN, false));

        userRepository.saveAll(users);
    }

    private CategoryAttributeValue createAttributeValue(CategoryAttribute categoryAttribute, String value) {
        CategoryAttributeValue attributeValue = new CategoryAttributeValue();
        attributeValue.setValue(value);
        attributeValue.setCategoryAttribute(categoryAttribute);
        return attributeValue;
    }

    private CategoryAttribute createCategoryAttribute(Category category, String name) {
        CategoryAttribute categoryAttribute = new CategoryAttribute();
        categoryAttribute.setCategory(category);
        categoryAttribute.setName(name);
        category.getAttributes().add(categoryAttribute);
        return categoryAttribute;
    }

    private User createUser(String email, Role userRole, boolean blocked) {
        User user = new User();
        user.setEmail(email);
        user.setRole(userRole);
        user.setPassword(passwordEncoder.encode("password"));
        user.setBlocked(blocked);
        return user;
    }
}
