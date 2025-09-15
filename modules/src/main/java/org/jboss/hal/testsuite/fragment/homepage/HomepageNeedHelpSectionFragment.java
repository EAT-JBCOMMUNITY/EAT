package org.jboss.hal.testsuite.fragment.homepage;

import org.jboss.hal.testsuite.fragment.BaseFragment;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.Map;
import java.util.stream.Collectors;

public class HomepageNeedHelpSectionFragment extends BaseFragment {

    public Map<String, String> getAllLinks() {
        return root.findElements(By.tagName("a")).stream().collect(
                Collectors.toMap(WebElement::getText, link -> link.getAttribute("href")));
    }
}
