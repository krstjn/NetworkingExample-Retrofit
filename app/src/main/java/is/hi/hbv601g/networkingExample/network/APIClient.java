package is.hi.hbv601g.networkingExample.network;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class APIClient {

    private static Retrofit retrofit = null;
    private static final String RM_API_URL = "https://rickandmortyapi.com/";
    private static final String MOVIE_API_URL = "URL_TO_ANOTHER_API";

    public static Retrofit getRMClient() {
        return new Retrofit.Builder()
                .baseUrl(RM_API_URL)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
    }
    public static Retrofit getMovieClient() {
        return new Retrofit.Builder()
                .baseUrl(MOVIE_API_URL)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
    }
}
