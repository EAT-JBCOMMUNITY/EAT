package org.jboss.hal.testsuite.fragment;

import org.jboss.arquillian.graphene.Graphene;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.Map;

/**
 * @author mkrajcov <mkrajcov@redhat.com>
 */
public class MetricsAreaFragment extends BaseFragment {

    private Map<String, String> metricGrid;

    public void setMetricGrid(Map<String, String> metricGrid) {
        this.metricGrid = metricGrid;
    }

    public MetricsFragment getMetricsFragment(String text) {
        return getMetricsFragment(text, MetricsFragment.class);
    }

    public double getPercentage(String dividendLabel, String divisorLabel) {
        double divisor = getMetricNumber(divisorLabel);
        if (divisor == 0) return 0;
        return getMetricNumber(dividendLabel) * 100 / divisor;
    }

    public <T extends MetricsFragment> T getMetricsFragment(String text, Class<T> clazz) {
        By elementSelector = By.xpath(".//*[name()='svg']//*[name()='g']/*[name()='text'][contains(text(),'" + text + "')]/../..");
        WebElement element = root.findElement(elementSelector);
        T content = Graphene.createPageFragment(clazz, element);

        double maxWidth = Double.parseDouble(element.findElement(
                By.xpath("./*[name()='g'][1]/*[name()='rect']")).getAttribute("width"));
        double currentWidth = Double.parseDouble(element.findElement(
                By.xpath("./*[name()='g'][2]/*[name()='rect']")).getAttribute("width"));
        double maxValue = Double.parseDouble(element.findElement(
                By.xpath("//*[name()='text'][./preceding-sibling::*[name()='text']][last()]")).getText().replace(",", ""));

        content.setMaxWidth(maxWidth);
        content.setCurrentWidth(currentWidth);
        content.setMaxValue(maxValue);

        return content;
    }

    public String getMetric(String key) {
        return metricGrid.get(key);
    }

    public double getMetricNumber(String key) {
        return Double.parseDouble(metricGrid.get(key));
    }
}
