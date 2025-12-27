package Utilities;

import com.github.javafaker.Faker;

import java.util.*;

public class DataFaker {

    private static final Faker faker = new Faker();

    public static String fakerCity(){
        return faker.address().city();
    }
    public static String fakerDomain(){
        return faker.internet().domainName();
    }
    public static String fakeEmail(){
        return fakerFirstName()+"."+fakerLastName()+"@"+fakerDomain();
    }
    public static String fakerUsername(){
        return faker.name().username();
    }
    public static String fakerAddress() {
        return faker.address().fullAddress();
    }

    public static String fakerAncient() {
        return faker.ancient().god();
    }

    public static String fakerAnimal() {
        return faker.animal().name();
    }

    public static String fakerApp() {
        return faker.app().name();
    }

    public static String fakerAquaTeenHungerForce() {
        return faker.aquaTeenHungerForce().character();
    }

    public static String fakerArtist() {
        return faker.artist().name();
    }

    public static String fakerAvatar() {
        return faker.avatar().image();
    }

    public static String fakerBackToTheFuture() {
        return faker.backToTheFuture().quote();
    }

    public static String fakerAviation() {
        return faker.aviation().aircraft();
    }

    public static String fakerFirstName(){
        return faker.name().firstName();
    }
    public static String fakerLastName(){
        return faker.name().lastName();
    }

    public static String fakerBeer() {
        return faker.beer().name();
    }


    public static String fakerBook() {
        return faker.book().title();
    }

    public static String fakerBool() {
        return String.valueOf(faker.bool().bool());
    }

    public static String fakerBusiness() {
        return faker.business().creditCardNumber();
    }

    public static String fakerChuckNorris() {
        return faker.chuckNorris().fact();
    }

    public static String fakerCat() {
        return faker.cat().name();
    }

    public static String fakerCode() {
        return faker.code().isbn10();
    }

    public static String fakerCoin() {
        return faker.currency().code();
    }

    public static String fakerColor() {
        return faker.color().name();
    }

    public static String fakerCommerce() {
        return faker.commerce().productName();
    }

    public static String fakerCompany() {
        return faker.company().name();
    }

    public static String fakerCrypto() {
        return faker.crypto().sha256();
    }

    public static String fakerDateAndTime() {
        return faker.date().birthday().toString();
    }


    public static String fakerDisease() {
        return faker.medical().diseaseName();
    }

    public static String fakerDog() {
        return faker.dog().name();
    }

    public static String fakerDragonBall() {
        return faker.dragonBall().character();
    }

    public static String fakerDune() {
        return faker.dune().quote();
    }

    public static String fakerEducator() {
        return faker.educator().university();
    }

    public static String fakerEsports() {
        return faker.esports().game();
    }

    public static String fakerFile() {
        return faker.file().fileName();
    }

    public static String fakerFinance() {
        return faker.finance().creditCard();
    }

    public static String fakerFood() {
        return faker.food().dish();
    }

    public static String fakerFriends() {
        return faker.friends().character();
    }

    public static String fakerFunnyName() {
        return faker.funnyName().name();
    }

    public static String fakerGameOfThrones() {
        return faker.gameOfThrones().character();
    }

    public static String fakerHarryPotter() {
        return faker.harryPotter().character();
    }

    public static String fakerHipster() {
        return faker.hipster().word();
    }

    public static String fakerHitchhikersGuideToTheGalaxy() {
        return faker.hitchhikersGuideToTheGalaxy().character();
    }

    public static String fakerHobbit() {
        return faker.hobbit().character();
    }

    public static String fakerHowIMetYourMother() {
        return faker.howIMetYourMother().character();
    }

    public static String fakerIdNumber() {
        return faker.idNumber().valid();
    }

    public static String fakerInternet() {
        return faker.internet().emailAddress();
    }

    public static String fakerJob() {
        return faker.job().title();
    }

    public static String fakerLeagueOfLegends() {
        return faker.leagueOfLegends().champion();
    }

    public static String fakerLebowski() {
        return faker.lebowski().character();
    }

    public static String fakerLordOfTheRings() {
        return faker.lordOfTheRings().character();
    }

    public static String fakerLorem() {
        return faker.lorem().paragraph();
    }

    public static String fakerMatz() {
        return faker.matz().quote();
    }

    public static String fakerMusic() {
        return faker.music().genre();
    }

    public static String fakerName() {
        return faker.name().firstName();
    }

    public static String fakerNation() {
        return faker.nation().nationality();
    }

    public static String fakerNumber() {
        return String.valueOf(faker.number().randomDigit());
    }

    public static String fakerOptions() {
        return faker.options().option("Option1", "Option2", "Option3");
    }

    public static String fakerOverwatch() {
        return faker.overwatch().hero();
    }

    public static String fakerPhoneNumber() {
        return faker.phoneNumber().phoneNumber();
    }

    public static String fakerPokemon() {
        return faker.pokemon().name();
    }

    public static String fakerPrincessBride() {
        return faker.princessBride().quote();
    }

    public static String fakerRelationshipTerms() {
        return faker.relationships().direct();
    }

    public static String fakerRickAndMorty() {
        return faker.rickAndMorty().character();
    }

    public static String fakerRobin() {
        return faker.robin().quote();
    }

    public static String fakerRockBand() {
        return faker.rockBand().name();
    }

    public static String fakerShakespeare() {
        return faker.shakespeare().hamletQuote();
    }

    public static String fakerSlackEmoji() {
        return faker.slackEmoji().people();
    }

    public static String fakerSpace() {
        return faker.space().planet();
    }


    public static String fakerStarTrek() {
        return faker.starTrek().character();
    }

    public static String fakerStock() {
        return faker.stock().nyseSymbol();
    }

    public static String fakerSuperhero() {
        return faker.superhero().name();
    }

    public static String fakerTeam() {
        return faker.team().name();
    }

    public static String fakerTwinPeaks() {
        return faker.twinPeaks().character();
    }

    public static String fakerUniversity() {
        return faker.university().name();
    }

    public static String fakerWeather() {
        return faker.weather().description();
    }

    public static String fakerWitcher() {
        return faker.witcher().character();
    }

    public static String fakerYoda() {
        return faker.yoda().quote();
    }

    public static String fakerZelda() {
        return faker.zelda().character();
    }

    public static int generateRandomNumber(int lowerGenNum, int higherGenNum){
        Random random = new Random();
        return random.nextInt((higherGenNum - lowerGenNum)) + lowerGenNum;

    }
    public static String generateRandomListOfNumbers(int lowerGenNum, int higherGenNum, int numOfGen) {
        Random random = new Random();
        ArrayList<Integer> arrayList = new ArrayList<>(numOfGen);

        while (arrayList.size() < numOfGen) {
            int x = random.nextInt((higherGenNum - lowerGenNum)) + lowerGenNum;
            arrayList.add(x);
        }

        StringBuffer stringBuffer = new StringBuffer();

        for (int num : arrayList){
            stringBuffer.append(num);
        }
        return stringBuffer.toString();
    }



    
}
