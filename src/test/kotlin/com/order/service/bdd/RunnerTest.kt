package com.order.service.bdd

import org.junit.platform.suite.api.ConfigurationParameter
import org.junit.platform.suite.api.SelectClasspathResource
import org.junit.platform.suite.api.Suite
import io.cucumber.junit.platform.engine.Constants
import org.junit.platform.suite.api.IncludeEngines

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("com/order/service/bdd/features")
@ConfigurationParameter(key = Constants.GLUE_PROPERTY_NAME, value = "com.order.service.bdd.steps,com.order.service.bdd.config")
@ConfigurationParameter(key = Constants.PLUGIN_PROPERTY_NAME, value = "pretty,json:target/cucumber-report.json")
class RunCucumberTest
