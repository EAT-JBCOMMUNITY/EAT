package org.jboss.hal.testsuite.fragment;

import org.jboss.arquillian.graphene.Graphene;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * Created by jcechace on 15/02/14.
 */
public class NavigationFragment extends BaseFragment {

    public <T extends NavigationSectionFragment> T getSectionBy(By selector, Class<T> clazz) {
        WebElement sectionRoot = root.findElement(selector);
        T section = Graphene.createPageFragment(clazz, sectionRoot);
        return section;
    }

    public <T extends NavigationSectionFragment> T  getSection(String id, Class<T> clazz) {
        By selector = By.id(id);
        return getSectionBy(selector, clazz);
    }

    public NavigationSectionFragment getSection(String id) {
        return getSection(id, NavigationSectionFragment.class);
    }
}
