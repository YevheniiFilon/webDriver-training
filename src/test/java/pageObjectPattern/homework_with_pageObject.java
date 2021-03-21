package pageObjectPattern;

import org.junit.jupiter.api.Test;

public class homework_with_pageObject extends CommonSteps_homeworkPageObject {



    @Test
    void findElements() {
        addItemsToCart(3);
        openCart();
        cleanUpCart();
    }

}
