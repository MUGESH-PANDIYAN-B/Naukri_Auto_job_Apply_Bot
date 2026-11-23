package helper;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.pageObjectManager.PageObjectManager.pageObjectManager;

public class GeminiHelper {
private static final String apikey=System.getenv("gemini_api_key");
    private static final String ENDPOINT = "https://generativelanguage.googleapis.com/v1beta/models/gemini-3.5-flash-lite:generateContent";
    public static String getAnswerFromGemini(String question,String availableOptions) {
        try {
            String prompt = "You are an automated job applicant assistant acting on behalf of the candidate. " +
                    "Your goal is to answer recruiter chatbot questions accurately based on the candidate's profile. " +
                    "Candidate Profile Summary: " +
                    "Name: MUGESH PANDIYAN B. " + //[cite: 2]
                    "Role: Senior Test Analyst / QA Automation Engineer. " + //[cite: 2]
                    "Contact: +91 7305221357, mugeshpandiyanb@gmail.com. " + //[cite: 2]
                    "Total Experience: 3.5+ years in Manual and Automation Testing, UI and API validation. " + //[cite: 2]
                    "Current Company: INTELSAT (Oct 2022 - Present). " + //[cite: 2]
                    "Education: Bachelor of Engineering (B.E.) in Computer Science and Engineering, Aarupadai Veedu Institute of Technology (2022, 7.5 CGPA). " + //[cite: 4]
                    "Location: Chennai | Willing to relocate to Bangalore/Chennai/Anywere: Yes | Willing to work from office/Romote/Hybrid: Yes. " +
                    "Current CTC: 5 LPA | Expected CTC: 8 LPA (or 7 LPA depending on the input limits) | Notice Period: 15 days / Immediate. " +
                    "Key Skills: Java, JavaScript, Selenium WebDriver, Playwright, Maven, Cucumber (BDD), TestNG, JUnit, POM, Jira, Git, Jenkins, Mobile Testing, MySQL, Postman API, Rest Assured. over 3.5+ years Experience" + //[cite: 2]
                    "Projects: 1. Hartford (Insurance, Sep 2023 - Present): Playwright, JavaScript, Cucumber BDD, Jenkins CI/CD. " + //
                    "2. TD Bank (Banking, Jan 2023 - Sep 2023): Selenium, Java, TestNG, POM, Postman API, SQL. " + //[cite: 3, 4]
                    "Achievements: Best employee for the year (Mar 2025), Bug Hunter Award (April 2023). " + //[cite: 2]
                    "Answering Rules: " +
                    "1. Options available on screen: [" + availableOptions + "]. If options are given, reply ONLY with the exact matching option text (for example: Yes). " +
                    "2. If no options are given, reply ONLY with the short answer or number to type in the box. Do not add full stops or extra words. " +
                    "3. MISSING EXPERIENCE RULE: If asked about a specific tool, skill, or domain not mentioned in this profile, NEVER answer with 'No' or '0'. Instead, reply exactly with 'Little experience' or 'Basic knowledge'. " +
                    "Recruiter Chatbot Question: '" + question + "'.";


            Map<String, Object> textPart = new HashMap<>();
            textPart.put("text", prompt);

            Map<String, Object> parts = new HashMap<>();
            parts.put("parts", List.of(textPart));

            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("contents", List.of(parts));


            Response response = RestAssured.given()
                    .urlEncodingEnabled(false)
                    .contentType(ContentType.JSON)
                    .queryParam("key",apikey)
                    .body(requestBody)
                    .when()
                    .post(ENDPOINT);

            if (response.getStatusCode() == 200) {

                String answer = response.jsonPath().getString("candidates[0].content.parts[0].text");

                return answer.trim();
            } else {
                System.out.println("API Error Status: " + response.getStatusCode());
                System.out.println(response.asPrettyString());
            }

        } catch (Exception e) {
            System.out.println("Exception in RestAssured Gemini call: " + e.getMessage());
        }
        return "Yes";
    }


    public static void main(String[] args) {
        String reply = getAnswerFromGemini("How many years of experience do you have in Karate Framework?",null);
        System.out.println("Gemini Replied: " + reply);
    }
}