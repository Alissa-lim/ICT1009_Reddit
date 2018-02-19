package testPackage;

import uk.ac.wlv.sentistrength.*;
public class mews{

    private String sentence;
    private SentiStrength sen;
    private double positve=0;
    private double negatvie =0;
    private double neutral = 0;
    private double positivePer;
    private double negativePer;
    private double neutralPer;
    private double total;

    public mews() {

    }

    public mews(String sen) {
        this.sentence = sen;
    }

    public String getSentence() {
        return sentence;
    }

    public void setSentence(String sentence) {
        this.sentence = sentence;
    }

    public double getPositve() {
        return this.positve;
    }

    public double getNegatvie() {
        return this.negatvie;
    }
    public double getNeutral() {
        return this.neutral;
    }
    public double getTotal() {
        return this.total;
    }

    public double getPositivePer() {
        return positivePer;
    }

    public double getNegativePer() {
        return negativePer;
    }

    public double getNeutralPer() {
        return neutralPer;
    }

    public boolean Initialise() {
        this.sen = new SentiStrength();
        String ssthIn[] = {"sentidata","C:\\Users\\School(Work)-PC\\Documents\\ICT1009\\Java Project\\JRAW\\src\\main\\java\\testPackage\\SentiStrength_Data\\", "trinary"};
        this.sen.initialise(ssthIn);
        return true;

    }

    public String generateEach() {
        Initialise();
        String re = this.sen.computeSentimentScores(this.sentence);
        String[] parts = re.split(" "); // to split the string
        int score = Integer.parseInt(parts[2]); // taking the last value of the sentiment

        String sentim;
        switch (score) {
            case 1:  sentim = "Positive";
            this.positve +=1;
                break;
            case -1:  sentim = "Negative";
            this.negatvie += 1;
                break;
            case 0:  sentim = "Neutral";
            this.neutral += 1;
                break;
            default: sentim = "Invalid";
                break;
        }
        return sentim;
    }

    public void calculate() {
        this.total = this.positve + this.neutral + this.negatvie;
        this.positivePer = this.positve / this.total * 100.0;
        this.negativePer = this.negatvie / this.total * 100.0;
        this.neutralPer = this.neutral / this.total * 100.0;
    }


}
