package app.services;

import java.util.ArrayList;
import java.util.List;

public class RecommendationRepository {

    /**
     * Food recommendations.
     * @return
     */
    public List<String> getEatRecommendations() {
        List<String> eatRecommendations = new ArrayList<>();
        eatRecommendations.add("Meatless Monday time! Don't eat any meat on monday");
        eatRecommendations.add("Buy food that are in season, this "
                + "will cut the high transportation and fabrication costs in terms of CO2");
        eatRecommendations.add("Avoid wasting food, share it "
                + "if you think you won't be able to finish it!");
        eatRecommendations.add("Buy organic food!");
        eatRecommendations.add("Tofu Day!");
        eatRecommendations.add("Felafel Time!");
        eatRecommendations.add("Avoid Robert Mugabe’s Birthday Party\nThis year attendees will be "
                + "feasting on two elephants, two buffalo, two sables, "
                + "five impalas and a lion. What a sad day in Zimbabwe for"
                + " both food sustainability and economic inequality.");
        eatRecommendations.add("Use climate-appropriate plants in your garden");
        return eatRecommendations;
    }

    /**
     * Household recommendations.
     * @return
     */
    public List<String> getHouseholdRecommendations() {
        List<String> householdRecommendations = new ArrayList<>();
        householdRecommendations.add("Wear an extra layer of clothing"
                + " and reduce your house temperature by 1 degrees!");
        householdRecommendations.add("Recycle unused stuff!");
        householdRecommendations.add("Turn off the lights you're not using");
        householdRecommendations.add("Buy energy efficient appliances");
        householdRecommendations.add("Reduce drafts and air leaks with caulk, "
                + "insulation, and weather stripping.");
        householdRecommendations.add("Go outside and advocate for clean alternatives to "
                + "fossil fuels, such as wind, solar, geothermal energy projects");
        return householdRecommendations;
    }

    /**
     * Transport recommendations.
     * @return
     */
    public List<String> getTransportRecommendations() {
        List<String> transportRecommendations = new ArrayList<>();
        transportRecommendations.add("Do Carpooling instead of driving your own car."
                + " BlaBla Car is recommended");
        transportRecommendations.add("Don't you have enough money to buy a "
                + "Tesla? You can still give a shot on Nissan Leaf");
        transportRecommendations.add("Don't speed up unnecessarily, "
                + "this reduces mileage by up to 33%");
        transportRecommendations.add("Avoid traffic\n" + "Being stuck in "
                + "traffic wastes gas and unneccessarily creates CO2.");
        transportRecommendations.add("Buy economy class flights, "
                + "for the same reasons as carpooling and public transportation.\n"
                + "Don't be like Prince Alwaleed bin Talal al-Saud, or the "
                + "Sultan of Brunei, who buy entire economy-size "
                + "planes and convert them into flying palaces.");
        transportRecommendations.add("Don't buy a HondaJet if you have enough money");
        return transportRecommendations;
    }


}
