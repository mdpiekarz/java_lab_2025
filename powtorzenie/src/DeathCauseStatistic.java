import java.util.StringTokenizer;

public class DeathCauseStatistic {
    private String key;
    private int ageBracket[];

    public class AgeBracketDeaths{
        final public int young, old, deathCount;

        @Override
        public String toString() {
            return "AgeBracketDeaths{" +
                    "young=" + young +
                    ", old=" + old +
                    ", deathCount=" + deathCount +
                    '}';
        }

        public AgeBracketDeaths(int young, int old, int deathCount) {
            this.young = young;
            this.old = old;
            this.deathCount = deathCount;
        }
    }

    private DeathCauseStatistic(String key, int ageBracket[]) {
        this.key = key;
        this.ageBracket = ageBracket;
    }

    public static DeathCauseStatistic fromCsvLine(String line) {
        String key;
        int ageBracket[] = new int[20];

        StringTokenizer tokenizer = new StringTokenizer(line,",");
        key = tokenizer.nextToken();
        key=key.trim();
        tokenizer.nextToken();
        for(int i=0; i<20; i++) {
            String token = tokenizer.nextToken();
            ageBracket[i] = token.equals("-") ? 0 : Integer.parseInt(token);
        }
        return new DeathCauseStatistic(key, ageBracket);
    }

    public String getKey() {
        return key;
    }
    public int[] getAgeBracket() {
        return ageBracket;
    }

    public AgeBracketDeaths getDeaths(int age) {

        return new AgeBracketDeaths(age/5*5,age/5*5+4, ageBracket[age/5]);
    }
}
