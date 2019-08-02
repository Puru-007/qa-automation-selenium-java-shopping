# Introduction
The provided repository contains a sample complete automated test using selenium webdriver for email sending functionality via Gmail.

The test is doing following things:
- Login to Gmail
- Compose an email from subject and body as mentioned in src/test/resources/test.properties
- Label email as "Social"
- Send the email to the same account which was used to login (from and to addresses would be the same)
- Wait for the email to arrive in the Inbox
- Mark email as starred
- Open the received email
- Verify email came under proper Label i.e. "Social"
- Verify the subject and body of the received email
- Generate test execution report at the end

# Notes
- Update the src/test/resources/test.properties file to replace dummy credentials and chromedriver path before you run the tests. You may remove your login details before submitting this assessment.
- Don't include downloaded packages or auto-generated folders in your submission.
- Please enable "Social" label for your Gmail account using the following steps:
    -Go to Settings of your Gmail account
    -Click on the "Labels" tab and click on "Show" for "Social" label under Categories
    -Click on "Inbox" tab and mark the checkbox for "Social" and "Primary" under Categories so that "Social" and "Primary" tabs appear on the main page of your Gmail account


# Prerequisites:
- ChromeDriver 2.44 , JDK 8+
- Any IDE

# Development Environment:
- Modify src/test/resources/test.properties to point to ChromeDriver's path on your system
- On any terminal, move to the project's root folder and execute the following command:
    - ./gradlew clean test
