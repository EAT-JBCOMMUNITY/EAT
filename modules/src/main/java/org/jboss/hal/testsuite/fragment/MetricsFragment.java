package org.jboss.hal.testsuite.fragment;

/**
 * @author mkrajcov <mkrajcov@redhat.com>
 */
public class MetricsFragment extends BaseFragment {

    private double maxValue;

    private double maxWidth;
    private double currentWidth;

    public double getPercentage() {
        return (currentWidth * 100 / maxWidth);
    }

    public double getCurrentValue() {
        return (currentWidth / maxWidth) * maxValue;
    }

    public double getMaxWidth() {
        return maxWidth;
    }

    public void setMaxWidth(double maxWidth) {
        this.maxWidth = maxWidth;
    }

    public double getCurrentWidth() {
        return currentWidth;
    }

    public void setCurrentWidth(double currentWidth) {
        this.currentWidth = currentWidth;
    }

    public double getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(double maxValue) {
        this.maxValue = maxValue;
    }
}
