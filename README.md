**Test Description:**
Write a test automation suite which does following.
1. Reads the given input file: car_input.txt
2. Extracts vehicle registration numbers based on pattern(s).
3. Each number extracted from input file is fed to any car valuation website for e.g. https://car-checking.com
(Perform vehicle details search from car valuation page)
4. Compare the output returned by car valuation website with given car_output.txt
5. Highlight/fail the test if car reg details not found from the comparison site or mismatches from point #4. Showcase your skills so it’s easier to add more input files in future. Utilise any JVM based language with browser automation tools. Use
design patterns where appropriate.

**Project overview :**
Java based project developed with Intellij IDE having standard plugins like maven, testng. If using other IDE then make sure to add these if not present already. 
Other plugins in use for this projects are as below, please install if not present on IDE in use. 
	- cucumber for java
 	- gherkin 
  POM.xml file includes all dependencies, adjust java version if needed as per setup on your machine. 

  **Execute Test**
  - Navigate to CarCompare.feature file (located under src/test/java/com/testcases/)
  - right click and run as Feature or Scenario which will execute test on Chrome browser.
  - As part of reporting, this is currently covered within console outcome with detail information. 
  
