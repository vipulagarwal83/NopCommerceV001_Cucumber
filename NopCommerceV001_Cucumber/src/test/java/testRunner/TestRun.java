package testRunner;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;

import io.cucumber.junit.CucumberOptions;



@RunWith(Cucumber.class)
@CucumberOptions
        (
        		//features=".//Features/",  // for run All feature file		
		  //features=".//Features/Customers.feature",  // for run single feature file
          features= {".//Features/Login.feature",".//Features/Customers.feature"},  // for run multiple feature file	
		  glue="stepDefinations",
		  dryRun=false,
		  monochrome= true,
		  plugin= {"pretty","html:test-output/cucumber-reports"}
		  //tags= ("@sanity and @regression")
		  		
		)
public class TestRun {

}
