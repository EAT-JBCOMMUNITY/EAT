package org.jboss.hal.testsuite.treefinder;

import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.graphene.findby.ByJQuery;
import org.jboss.arquillian.graphene.findby.FindByJQuery;
import org.jboss.arquillian.graphene.fragment.Root;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;

/**
 * Class representing tree based navigation located e.g. in generated subsystems and deployments
 */
public class TreeNavigation {

    private static final Logger logger = LoggerFactory.getLogger(TreeNavigation.class);

    private static final String TREE_CLASS_NAME = "gwt-Tree";

    private TreeNavigation() {
    }

    @Drone
    private WebDriver browser;

    @FindByJQuery("." + TREE_CLASS_NAME)
    @Root
    private WebElement root;

    private List<String> steps;

    /**
     * Add step of navigation - i.e. level in tree structure
     * @param label name of label of item through which the navigation will be performed.
     * @return this
     */
    public TreeNavigation step(String label) {
        if (steps == null) {
            steps = new LinkedList<>();
        }
        steps.add(label);
        return this;
    }

    /**
     * Navigates to target item step by step
     * @return target item
     */
    public TreeNavigationGroup navigateToTreeItem() {
        TreeNavigationGroup current = new TreeNavigationGroup(root.findElement(ByJQuery.selector("> div:nth-child(2)")));
        logger.trace("Top level {}: '{}'", TreeNavigationGroup.class.getSimpleName(), current.getRoot().getAttribute("innerHTML"));
        for (String step : steps) {
            logger.debug("Navigating to next subtree with root label '{}'.", step);
            current = current.openSubTreeIfNotOpen().getDirectChildByLabel(step);
        }
        return current;
    }

}
