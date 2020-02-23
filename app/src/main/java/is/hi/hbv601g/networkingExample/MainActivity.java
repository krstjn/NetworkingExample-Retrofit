package is.hi.hbv601g.networkingExample;

import androidx.appcompat.app.AppCompatActivity;
import is.hi.hbv601g.networkingExample.network.APIClient;
import is.hi.hbv601g.networkingExample.network.RickAndMortyAPI;
import is.hi.hbv601g.networkingExample.model.Character;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private RickAndMortyAPI rickAndMortyAPI;

    private TextView mName;
    private TextView mStatus;
    private TextView mOrigin;
    private TextView mLocation;
    private ImageView mImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mName = findViewById(R.id.tvName);
        mStatus = findViewById(R.id.tvStatus);
        mOrigin = findViewById(R.id.tvOrigin);
        mLocation = findViewById(R.id.tvLocation);
        mImage = findViewById(R.id.ivImage);

        rickAndMortyAPI = APIClient.getRMClient().create(RickAndMortyAPI.class);

        getNewRandomCharacter();

        findViewById(R.id.btnRandomCharacter).setOnClickListener(l -> getNewRandomCharacter());
    }

    public void getNewRandomCharacter(){
        long randomCharacterId = (long)(Math.random() * 200 + 1);
        Call<Character> call = rickAndMortyAPI.getCharacter(randomCharacterId);
        call.enqueue(new Callback<Character>() {
            @Override
            public void onResponse(Call<Character> call, Response<Character> response) {
                Log.d("HttpStatus", String.valueOf(response.code()));

                if (response.isSuccessful() && response.body() != null) {
                        updateUI(response.body());
                }
            }

            @Override
            public void onFailure(Call<Character> call, Throwable t) {
                Log.e("NetworkError", t.getMessage());
                call.cancel();
            }
        });
    }

    public void updateUI(Character character) {
        mName.setText(character.getName());
        mStatus.setText(character.getStatus());
        mOrigin.setText(character.getOrigin().getName());
        mLocation.setText(character.getLocation().getName());
        new GetImageFromUrl().execute(character.getImage());
    }

    class GetImageFromUrl extends AsyncTask<String, Void, Bitmap>{

        GetImageFromUrl(){}

        @Override
        protected Bitmap doInBackground(String... url) {
            String stringUrl = url[0];
            Bitmap bitmap = null;
            try {
                URL imageUrl = new URL(stringUrl);
                bitmap = BitmapFactory.decodeStream(imageUrl.openConnection().getInputStream());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap){
            super.onPostExecute(bitmap);
            mImage.setImageBitmap(bitmap);
        }
    }
}
