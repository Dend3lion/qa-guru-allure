package guru.qa;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.*;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static io.qameta.allure.Allure.step;
import static org.openqa.selenium.By.linkText;

public class StepsTest {
    private static final String URL = "https://github.com";
    private static final String REPOSITORY = "Dend3lion/qa-guru-allure";
    private static final String ISSUE = "Test Issue";

    @BeforeAll
    public static void beforeAll(){
        Configuration.pageLoadStrategy = "eager";
        Configuration.browserSize = "1980x1020";
    }

    @Test
    @Feature("Issue в репозитории")
    @Story("Создание Issue")
    @Owner("eroshenkoam")
    @Severity(SeverityLevel.BLOCKER)
    @Link(value = "Testing", url = "https://github.com")
    @DisplayName("Создание Issue для авторизованного пользователя с помощью чистого Selenide")
    public void basicSelenideTest(){
        SelenideLogger.addListener("allure", new AllureSelenide());

        open(URL);

        $(".search-input").click();
        $("#query-builder-test").setValue(REPOSITORY);
        $("#query-builder-test").submit();

        $(linkText(REPOSITORY)).click();
        $("#issues-tab").click();
        $(withText(ISSUE)).should(exist);
    }

    @Test
    @Feature("Issue в репозитории")
    @Story("Создание Issue")
    @Owner("eroshenkoam")
    @Severity(SeverityLevel.BLOCKER)
    @Link(value = "Testing", url = "https://github.com")
    @DisplayName("Создание Issue для авторизованного пользователя с помощью Steps")
    public void lambdaStepTest() {
        SelenideLogger.addListener("allure", new AllureSelenide());

        step("Открываем главную страницу", () -> open(URL));
        step("Ищем репозиторий " + REPOSITORY, () -> {
            $(".search-input").click();
            $("#query-builder-test").setValue(REPOSITORY);
            $("#query-builder-test").submit();
        });
        step("Кликаем по ссылке репозитория " + REPOSITORY, () -> $(linkText(REPOSITORY)).click());
        step("Открываем таб Issues", () -> $("#issues-tab").click());
        step("Проверяем наличие Issue с номером " + ISSUE, () -> {
            $(withText( ISSUE)).should(exist);
        });
    }

    @Test
    @Feature("Issue в репозитории")
    @Story("Создание Issue")
    @Owner("eroshenkoam")
    @Severity(SeverityLevel.BLOCKER)
    @Link(value = "Testing", url = "https://github.com")
    @DisplayName("Поиск Issue для авторизованного пользователя с помощью annotated Steps")
    public void annotatedStepTest() {
        SelenideLogger.addListener("allure", new AllureSelenide());
        WebSteps steps = new WebSteps();

        steps.openMainPage()
            .searchForRepository(REPOSITORY)
            .clickOnRepositoryLink(REPOSITORY)
            .openIssuesTab()
            .shouldSeeIssueWithName(ISSUE);
    }
}
