package is.hi.hbv601g.networkingExample.network;

import is.hi.hbv601g.networkingExample.model.Character;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RickAndMortyAPI {

    @GET("api/character/{id}")
    Call<Character> getCharacter(@Path("id") long id);
}
