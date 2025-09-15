package org.jboss.hal.testsuite.treefinder;

import org.jboss.arquillian.graphene.findby.ByJQuery;
import org.openqa.selenium.By;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents a group of navigation items. Be aware, that containment of this group varies from several subtrees as its
 * children to just one child with no subtree.
 */
public class TreeNavigationGroup {

    private static final Logger logger = LoggerFactory.getLogger(TreeNavigationGroup.class);

    private static final String TREE_ITEM_CLASS_NAME = "gwt-TreeItem";

    private static final By DIRECT_CHILDREN_SELECTOR = ByJQuery.selector("> div > div");

    private final WebElement root;

    TreeNavigationGroup(WebElement root) {
        this.root = root;
    }

    /**
     * Retrieve root element of this group
     */
    public WebElement getRoot() {
        return root;
    }

    /**
     * Get all direct children of current root
     * @return list of direct children
     */
    private List<TreeNavigationGroup> getDirectChildren() {
        List<WebElement> elements = root.findElements(DIRECT_CHILDREN_SELECTOR);
        if (elements.size() > 0) {
            logger.debug("Found '{}' direct children!", elements.size());
            return elements.stream().map(TreeNavigationGroup::new).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    /**
     * Returns label of root item of this group
     * @return label
     */
    public String getLabel() {
        return root.findElement(ByJQuery.selector("div." + TREE_ITEM_CLASS_NAME)).getText().trim();
    }

    /**
     * Return direct child of current root tree item by its label
     * @param label by which the item will be looked for
     * @return child
     * @throws NotFoundException if child with given label is not found
     */
    TreeNavigationGroup getDirectChildByLabel(String label) {
        for (TreeNavigationGroup child : getDirectChildren()) {
            if (child.getLabel().equals(label)) {
                return child;
            }
        }
        throw new NotFoundException("Not found child with label '" + label + "'!");
    }

    /**
     * Clicks on button which toggles opening of subtree
     * @return this
     */
    private TreeNavigationGroup clickOpenTreeToggleButton() {
        //Click on an arrow icon on left of label
        root.findElement(ByJQuery.selector("> table tr > td > img")).click();
        return this;
    }

    /**
     * Clicks root label of this subtree
     * @return this
     */
    public TreeNavigationGroup clickLabel() {
        root.findElement(ByJQuery.selector("> table tr > td:has(div)")).click();
        return this;
    }

    /**
     * Reveals subtree if it is currently closed
     * @return this
     */
    TreeNavigationGroup openSubTreeIfNotOpen() {
        if (!root.findElement(DIRECT_CHILDREN_SELECTOR).isDisplayed()) {
            clickOpenTreeToggleButton();
        }
        return this;
    }

    /**
     * Find if this item has a subtree to open
     * @return true if this item has subtree, false otherwise
     */
    public boolean hasSubtree() {
        //Just check if this root contains table with arrow and label
        return root.findElements(ByJQuery.selector("> table")).size() > 0;
    }

    /**
     * Find if this item has children
     * @return true if this item has children, false otherwise
     */
    public boolean hasChildren() {
        return hasSubtree() && openSubTreeIfNotOpen().getDirectChildren().size() > 0;
    }

    /**
     * Find if this item has child with given label
     * @param label Label of child
     * @return true if this item has child with given label
     */
    public boolean hasChild(String label) {
        return hasChildren() && getDirectChildByLabel(label) != null;
    }

}
