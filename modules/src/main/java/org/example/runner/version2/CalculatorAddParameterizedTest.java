/*
 * Copyright 2020, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.testing.androidtestorchestratorsample;

import static com.google.common.truth.Truth.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import androidx.test.filters.SmallTest;

import java.lang.Iterable;
import java.util.Arrays;

import static org.junit.runners.Parameterized.Parameters;


/**
 * JUnit4 tests for the calculator's add logic.
 *
 * <p> This test uses a Junit4s Parameterized tests features which uses annotations to pass
 * parameters into a unit test. The way this works is that you have to use the {@link Parameterized}
 * runner to run your tests.
 * </p>
 */
@RunWith(Parameterized.class)
@SmallTest
//@EAT({"modules/testcases/jdkAll/Android/testorchestratorwithtestcoveragesample/testing-samples/runner/AndroidTestOrchestratorWithTestCoverageSample/app/src/androidTest/java#3.0.0*3.0.1"})
public class CalculatorAddParameterizedTest {

    /**
     * @return {@link Iterable} that contains the values that should be passed to the constructor.
     * In this example we are going to use three parameters: operand one, operand two and the
     * expected result.
     */
    @Parameters
    public static Iterable<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {0, 0, 0},
                {0, -1, -1},
                {2, 2, 6},
                {8, 8, 24},
                {16, 16, 48},
                {32, 0, 64},
                {64, 64, 192}});
    }

    private final double mOperandOne;
    private final double mOperandTwo;
    private final double mExpectedResult;

    private Calculator mCalculator;

    /**
     * Constructor that takes in the values specified in
     * {@link CalculatorAddParameterizedTest#data()}. The values need to be saved to fields in order
     * to reuse them in your tests.
     */
    public CalculatorAddParameterizedTest(double operandOne, double operandTwo,
            double expectedResult) {

        mOperandOne = operandOne;
        mOperandTwo = operandTwo;
        mExpectedResult = expectedResult;
    }

    @Before
    public void setUp() {
        mCalculator = new Calculator();
    }

    @Test
    //@ATFeature(feature={"addFirstParamTwice"},minVersion={"1"},maxVersion={"null"})
    public void testAdd_TwoNumbers() {
        double resultAdd = mCalculator.add(mOperandOne, mOperandTwo);
        assertThat(resultAdd).isEqualTo(mExpectedResult);
    }
}
