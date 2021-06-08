import java.util.HashMap;
import java.util.Map;
import java.util.Random;

class Generator {
    Random rand = new Random();
    long[] POWERS_OF_62 = new long[8];
    static char[] chars = {'0', '1', '2', '3', '4' ,'5', '6', '7', '8', '9',
        'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z',
        'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'
    };

    public Generator() {
        for (int i = 0; i < 8; i++) {
            POWERS_OF_62[i] = (long)Math.pow(62, i);
        }
    }

    public long getRandomLong() {
        return Math.abs(rand.nextLong());
    }

    public String getUri() {
        return getUri(getRandomLong());
    }

    public String getUri(long number) {
        String uri = "";
        long quotient = number;
        for (int i = 7; i >= 0; i--) {
            if (quotient >= POWERS_OF_62[i]) {
                uri = uri + String.valueOf(chars[(int)(quotient % 62)]);
                quotient = quotient / 62;
            }
        }
        return new StringBuilder(uri).reverse().toString();
    }
}

class UriStore {
    Map<String, String> uriMap = new HashMap<>();
    Generator generator = new Generator();

    public String getShortUriAndStore(String url) {
        String shortUri = generator.getUri();
        uriMap.put(shortUri, url);
        return shortUri;
    }
}

public final class UrlShortener {
    public static void main(String[] args) {
        UriStore uriStore = new UriStore();
        String url = "https://s3.amazonaws.com/srweb-pvt-prod-us-east-1-294962523550/static/parts-manuals/2002__2002-210-SUNDECK.pdf";
        String shortUrl = uriStore.getShortUriAndStore(url);
        System.out.println("Short Url=" + shortUrl + " for url=" + uriStore.uriMap.get(shortUrl));
    }
}
