package lt.vu.mif.ui.view;

import lombok.Getter;
import lombok.Setter;
import lt.vu.mif.ui.helpers.interfaces.ICategoryHelper;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Named
@ViewScoped
@Getter
@Setter
public class CategoryTreeView implements Serializable {

    @Autowired
    private ICategoryHelper categoryHelper;

    private TreeNode root;

    @PostConstruct
    public void init() {
        List<CategoryView> categories = categoryHelper.findAll();
        Collections.sort(categories);
        Map<CategoryView, TreeNode> categoryMap = new HashMap<CategoryView, TreeNode>();
        for (CategoryView c : categories) {
            categoryMap.put(c, null);
        }

        //Build tree bottom to top (assign node to every category and set parent node)
        for (CategoryView c : categories) {
            //If category doesn't have a node, let's assign it!
            if (categoryMap.get(c) == null) {
                setNode(categoryMap, c);
            }
        }
    }

    private void setNode(Map<CategoryView, TreeNode> categoryMap, CategoryView current) {
        //Reached the top node
        if (current.getParentCategory() == null) {
            root = new DefaultTreeNode(current, null);
            categoryMap.replace(current, root);
            return;
        }

        //If parent doesn't have a node, we have to assign it one
        if (categoryMap.get(current.getParentCategory()) == null) {
            setNode(categoryMap, current.getParentCategory());
        }

        //Assign the node (and set node's parent node)
        categoryMap.replace(current,
                new DefaultTreeNode(current, categoryMap.get(current.getParentCategory())));
    }
}


