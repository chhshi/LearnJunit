package com.in28minutes.junit.suites;

import com.in28minutes.junit.helper.ArraysCompareTest;
import com.in28minutes.junit.helper.StringHelper;
import com.in28minutes.junit.helper.TestParameterized;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(JUnitPlatform.class)
@SelectClasses({ArraysCompareTest.class, StringHelper.class, TestParameterized.class})
public class HelperSuite {

}
