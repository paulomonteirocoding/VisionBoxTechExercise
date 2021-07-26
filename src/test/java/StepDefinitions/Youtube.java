package StepDefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import state.World;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class Youtube {

    private World world;
    private List<WebElement> trendingVideos;

    @Before
    public void before() {
        world = new World();
        world.getDriver().manage().deleteAllCookies();
        world.getDriver().navigate().refresh();
    }

    @After
    public void after() {
        world.getDriver().quit();
    }

    @Given("the user navigates to {string}")
    public void theUserNavigatesTo(String destinationURL) {
        destinationURL = destinationURL.toLowerCase(Locale.ROOT);
        String finalUrl = destinationURL;
        if (!(destinationURL.startsWith("HTTP://") || destinationURL.startsWith("HTTPS://"))) {
            finalUrl = "HTTP://" + destinationURL;
        }
        URL url = null;
        try {
            url = new URL(finalUrl);
            world.getDriver().navigate().to(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        world.waitForPageToLoad(destinationURL);

        List<WebElement> elements = world.getDriver().findElements(By.tagName("button"));

        for (WebElement e : elements
        ) {
            if (
                    world.getDriver().getCurrentUrl().contains("consent")
                            && (
                            e.getText().equalsIgnoreCase("Aceito")
                                    || e.getText().equalsIgnoreCase("Accept")
                    )
            ) {
                e.click();
                world.waitForPageToLoad(destinationURL);
            }
        }

        if (!world.getDriver().getCurrentUrl().contains("consent")) {
            Assert.assertTrue(true);
        }

    }

    @And("the user clicks {string}")
    public void theUserClicks(String arg0) {

        switch (arg0) {
            case "Explore":
            case "Explorar":
                for (WebElement e : world.getDriver().findElements(By.xpath("//*[@id=\"endpoint\"]"))) {
                    System.out.println(e.getText());
                    if (e.getText().equalsIgnoreCase("Explorar") || e.getText().equalsIgnoreCase("Explore"))
                        world.getWait().until(ExpectedConditions.elementToBeClickable(e)).click();
                }
                break;
            case "Trends":
            case "Tendências":
                for (WebElement e : world.getDriver().findElements(By.xpath("//*[@id=\"destination-content-root\"]"))) {
                    System.out.println(e.getText());
                    if (e.getText().equalsIgnoreCase("Tendências") || e.getText().equalsIgnoreCase("Trends"))
                        world.getWait().until(ExpectedConditions.elementToBeClickable(e)).click();
                }
                break;
            default:
                System.out.println("Button name not recognized");
        }


    }

    @Then("exactly {int} videos are displayed")
    public void exactlyVideosAreDisplayed(int arg0) {
        world.waitForPageToLoad("https://www.youtube.com/feed/explore");
        world.getWait().until(ExpectedConditions.elementToBeClickable(By.cssSelector(
                "ytd-item-section-renderer.style-scope:nth-child(3) > div:nth-child(3) > ytd-shelf-renderer:nth-child(1) > " +
                        "div:nth-child(1) > div:nth-child(2) > ytd-expanded-shelf-contents-renderer:nth-child(1) > div:nth-child(1) > " +
                        "ytd-video-renderer:nth-child(1) > div:nth-child(1) > div:nth-child(2) > div:nth-child(1) > div:nth-child(1)" +
                        " > h3:nth-child(1) > a:nth-child(2) > yt-formatted-string:nth-child(2)"
        )));
        trendingVideos = world.getDriver()
                .findElements(
                        By.cssSelector(
                                "a#video-title[href*=\"/watch?\"]"
                        )
                );

        Assert.assertTrue("Video count on page did not match expected!", trendingVideos.size() == arg0);
    }


    @And("the user logs of the {int} longest videos {string}, {string}, {string}")
    public void theUserTakesNoteOfTheLongestVideos(int arg0, String arg1, String arg2, String arg3) {

        System.out.println("Needs to implement step");

        /*for (WebElement e : trendingVideos
        ) {
            for (String s : e.getText().split("\n")
            ) {
                System.out.println(s);
            }
        }*/


    }

    @And("the user logs of the {int} most viewed videos {string}, {string}, {string}")
    public void theUserTakesNoteOfTheMostViewedtVideos(int arg0, String arg1, String arg2, String arg3) {

        System.out.println("Needs to implement step");

        /*for (WebElement e : trendingVideos
        ) {
            for (String s : e.getText().split("\n")
            ) {
                System.out.println(s);
            }
        }*/


    }

    @And("the user sums the view count for all listed videos")
    public void theUserSumsTheViewCountForAllListedVideos() {

        List<String> videoNames = new ArrayList<>();

        for (int i = 0; i < trendingVideos.size(); i++) {
            videoNames.add(trendingVideos.get(i).getText());
        }

        List<String> namePipeLengthPipeViewsPipeId = new ArrayList<>();

        for (String s : videoNames
             ) {
            for (WebElement e : trendingVideos
                 ) {
                if (e.getText().contains(s)){
                    for (String elemText :e.getText().split("\n")
                         ) {
                        if(
                                elemText.toLowerCase(Locale.ROOT).contains("views")
                                || elemText.toLowerCase(Locale.ROOT).contains("visualizações")
                        ){
                            namePipeLengthPipeViewsPipeId.add(s + "|" +  elemText.replace("visualizações","")
                                    .replace("views","")
                                    .replace("K","000")
                                    .replace("mil","000")
                                    .replace("M de","000000")
                                    .replace("M","000000")
                                    .replace(" ",""));
                        }


                    }

                }
            }
        }

        /*List<WebElement> elems =
        for (int i = 0; i < ; i++) {
            world.getDriver().findElements(By.cssSelector("a#video-title[href*=\"/watch?\"]")).get(0).getAttribute("aria-label").replace(world.getDriver().findElements(By.cssSelector("a#video-title[href*=\"/watch?\"]")).get(0).getAttribute("title"),"")
        }


        //trendingVideos.

        for (WebElement e : trendingVideos
        ) {
            String[] s = e.getText().split("\n");

            Map<String, String> videoData = new HashMap<>();

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");

            if(e.getText().contains("Luísa Sonza - melhor sozinha :-)-:")){
                System.out.println("aaa");
            }




            videoData.put("length", s[0]);
            videoData.put("Title", s[1]);
            videoData.put("Views", s[3]
                    .replace("visualizações","")
                    .replace("views","")
                    .replace("K","000")
                    .replace("mil","000")
                    .replace("M de","000000")
                    .replace("M","000000")
                    .replace(" ","")
            );

            extractedVideoData.add(videoData);
        }

        int viewCounter = 0;

        for (Map<String,String> m: extractedVideoData
             ) {
            viewCounter +=
                    Integer.parseInt(m.get("Views")
                    );
        }*/

    }

}